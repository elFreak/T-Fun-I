package model;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.SwingWorker;
import org.apache.commons.math3.optim.PointValuePair;
import com.sun.corba.se.impl.orbutil.threadpool.TimeoutException;

import model.fMinSearch.StableFMinSearch;
import userInterface.StatusBar;

/**
 * Klasse Network: Verwalted alle berechneten Übertragungsfunktionen
 * (Annäherungsrechngen).
 * 
 * @author Team 1
 *
 */
public class Network extends SwingWorker<Object, Message> implements SwingWorkerClient {

	/**
	 * Verknüpfungen zu anderen Objekten:
	 */
	private MeasurementData measurementData;
	private Model model;

	/**
	 * Eigenschaften:
	 */
	private double threshold;
	private Target target;

	/**
	 * Approximationen:
	 */
	private PointValuePair[] startValues;
	private Approximation[] approximations = new Approximation[10];
	private double[][] korrelationComparison;
	private double[][][] korrelationComparisonPoins;

	/**
	 * Thread verwalten:
	 */
	public ExecutorService threadExecutor = Executors.newFixedThreadPool(1);

	// -----------------------------------------------------------------------------------------------------------------
	// Konstrucktor:
	// -----------------------------------------------------------------------------------------------------------------
	/**
	 * Erstellt ein neues Netzwerk, welches die verschiedenen
	 * Annäherungsrechnungen verwalted. Beim erstellen dieses Netzwerkes werden
	 * zu beginn in einem seperaten Thread Startwerte für alle
	 * Annäherungsrechnungen berechnet.
	 * 
	 * @param measurementData
	 * @param model
	 */
	public Network(MeasurementData measurementData, Model model) {
		this.measurementData = measurementData;
		this.model = model;
		this.threshold = model.getNextThreshold();

		target = new Target(measurementData.getTimeFullNormed(), measurementData.getStepFullNormed());

		threadExecutor.execute(this);
	}

	// -----------------------------------------------------------------------------------------------------------------
	// Berechnungs Methoden:
	// -----------------------------------------------------------------------------------------------------------------
	/**
	 * In dieser Methode werden die Vorbereitungen für die Berechnungen der
	 * einzelnen Übertragungsfunktionen gemacht. Diese Vorbereitung besteht aus
	 * dem Berechnen von Startwerten für die später folgenden
	 * Annäherungsberechnungen. Dabei wird so vorgegangen, dass für tiefe
	 * Ordnungen zuerst Startwerte berechnet werden. Danach werden die
	 * Startwerte für höhere Ordnungen berechnet, wobei die Startwerte aus den
	 * tieferen Ordnungen als ungefähre Richtwerte benutzt werden um diese
	 * Berechnung stabil zu gestalten.
	 * 
	 */
	private void prepareCalculation() {

		// Strucktur der Startwerte wird definiert.
		PointValuePair utf[] = new PointValuePair[10]; // Ordnung 1-10 ...

		try {
			for (int i = 1; i <= 10; i++) { // Ordnung 1-10 ...

				// Kopiere Werte von kleineren Ordnungen. Nur falls die Ordnung
				// höher als 3 ist.
				double[] newUtf = new double[i + 1];
				if (i >= 4) { // Ordnung >= 4
					for (int j = 0; j < utf[i - 3].getPoint().length; j++) {
						newUtf[j] = utf[i - 3].getPoint()[j];
					}
					if (i % 2 == 0) {
						newUtf[1] = Math.sqrt(Math.sqrt((newUtf[1])));
						newUtf[2] = (newUtf[2]) / 4;
						newUtf[newUtf.length - 2] = newUtf[1];
						newUtf[newUtf.length - 1] = newUtf[2];
					} else {
						newUtf[newUtf.length - 1] = newUtf[newUtf.length - 3];
						newUtf[1] = Math.sqrt(Math.sqrt((newUtf[1])));
						newUtf[2] = (newUtf[2]) / 4;
						newUtf[newUtf.length - 3] = newUtf[1];
						newUtf[newUtf.length - 2] = newUtf[2];
					}
				} else {
					if (i == 1) { // Ordnung = 1
						newUtf[0] = 1.0;
						newUtf[1] = -1.0;
					}
					if (i == 2) { // Ordnung = 2
						newUtf[0] = 1.0;
						newUtf[1] = 1.0;
						newUtf[2] = 1.0;
					}
					if (i == 3) { // Ordnung = 3
						newUtf[0] = 1.0;
						newUtf[1] = 1.0;
						newUtf[2] = 1.0;
						newUtf[3] = -1.0;
					}
				}
				utf[i - 1] = new PointValuePair(newUtf, 0);

				// Berechne die momentane Ordnung:
				utf[i - 1] = StableFMinSearch.getUtfN(target, i, utf[i - 1], this, 0, threshold);
				swingAction(new Message("Startwerte berechnet (Ordnung " + i + ").", false));

				// Uebertragungsfuktion umschreiben:
				UTFDatatype uebergabe = new UTFDatatype();
				uebergabe.ordnung = i;
				uebergabe.zaehler = utf[i - 1].getPoint()[0];
				if (utf[i - 1].getPoint().length % 2 == 1) {
					uebergabe.koeffWQ = new double[utf[i - 1].getPoint().length - 1];

				} else {
					uebergabe.koeffWQ = new double[utf[i - 1].getPoint().length - 2];
					uebergabe.sigma = utf[i - 1].getPoint()[utf[i - 1].getPoint().length - 1];
				}
				for (int j = 0; j < uebergabe.koeffWQ.length; j++) {
					uebergabe.koeffWQ[j] = utf[i - 1].getPoint()[j + 1];
				}
			}

			// Falls alle Startwerte berechnet wurden, so wird dies ausgegeben.
			swingAction(new Message("Alle Startwerte wurden berechnet.", false));

		} catch (TimeoutException e) { // Bei einem Timeout wird die Berechnung
										// der Startwerte nicht durchgeführt.
			swingAction(new Message(
					"Probleme bei der Berechnung (Startwerte).\nVersuchen Sie folgendes:\n1) Versichern Sie sich, dass die Messwerte korekt bearbeited wurden.\n2) Passen Sie den Threshold an und starten Sie dann die Berechnung neu.",
					true));
		}

		startValues = utf;
	}

	/**
	 * Diese Methode gibt dem Thread-Pool die Aufgabe die Übertragungsfunktion
	 * der ausgewählten Ordnung in einem seperaten Thread zu berechnen.
	 * 
	 * @param order
	 */
	public void calculateApproximation(int order) {
		if (approximations[order - 1] == null) { // Falls die Ordnung noch nicht
													// berechnet wurde.
			approximations[order - 1] = new Approximation(order, this);
			threadExecutor.execute(approximations[order - 1]);
		}
	}

	/**
	 * Definiert, was im eigenen Thread gemacht werden soll.
	 * 
	 */
	@Override
	protected Object doInBackground() throws Exception {
		prepareCalculation();
		return 0;
	}

	/**
	 * Dient dazu den Benutzer mittels der StatusBar zu informieren.
	 */
	@Override
	protected void process(List<Message> arg) {
		if (isCancelled() == false) {
			super.process(arg);
			for (int i = 0; i < arg.size(); i++) {
				Message message = arg.get(i);

				if (message.isError) {
					StatusBar.showStatus(message.message, StatusBar.FEHLER);
				} else {
					StatusBar.showStatus(message.message, StatusBar.INFO);
				}

			}
		}

	}

	/**
	 * Sobald der Thread beendet wird werden die Observer des Models informiert.
	 */
	@Override
	protected void done() {
		super.done();

		// Nur falls die Startwerte auch berechnet wurden, wird dies den
		// Observern mitgeteilt:
		if (startValues != null && isCancelled()==false) {
			model.notifyObservers(Model.NOTIFY_REASON_NETWORK_START_VALUES);
		}
	}

	/**
	 * Diese Methode kann von den einzelnen Approximationen benutzt werden, um
	 * dem Netzwerk mitzuteilen, dass eine neue Approxmation berechnet wurde.
	 * Das Netzwerk passt darauf den Korrelationsvergleich der bereits
	 * berechneten Ordnungen an und informiert die Observer.
	 */
	public void approximationDone() {
		int anzahl = 0;
		for (int i = 0; i < approximations.length; i++) {
			if (approximations[i] != null) {
				if (approximations[i].getKorrKoef() != 0) {
					anzahl++;
				}
			}
		}
		korrelationComparison = new double[2][anzahl];
		korrelationComparisonPoins = new double[10][][];
		int zaeler = 0;
		for (int i = 0; i < approximations.length; i++) {
			if (approximations[i] != null) {
				if (approximations[i].getKorrKoef() != 0) {
					korrelationComparison[0][zaeler] = (double) (i + 1);
					korrelationComparison[1][zaeler] = approximations[i].getKorrKoef();
					zaeler++;
					
					korrelationComparisonPoins[i] = new double[][] { { (i + 1) },{ approximations[i].getKorrKoef() } };
				}
			}
		}
		
		
		if (isCancelled() == false) {
			model.notifyObservers(Model.NOTIFY_REASON_APPROXIMATION_UPDATE);
		}
	}

	@Override
	public void swingAction(Message message) {
		publish(message);
	}

	// -----------------------------------------------------------------------------------------------------------------
	// Get-Methoden:
	// -----------------------------------------------------------------------------------------------------------------
	public Approximation getApprox(int order) {
		return approximations[order - 1];
	}

	public double[][] getKorrelationComparison() {
		return korrelationComparison;
	}

	public Target getTarget() {
		return target;
	}

	public double getThreshold() {
		return threshold;
	}

	public PointValuePair[] getStartWerte() {
		return startValues;
	}

	public MeasurementData getMeasurementData() {
		return measurementData;
	}

	public double[][][] getKorrelationComparisonPoins() {
		return korrelationComparisonPoins;
	}
}