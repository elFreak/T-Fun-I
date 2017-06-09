package model;

import java.util.List;
import javax.swing.SwingWorker;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.optim.PointValuePair;
import com.sun.corba.se.impl.orbutil.threadpool.TimeoutException;
import matlabfunction.Matlab;
import model.fMinSearch.StableFMinSearch;
import userInterface.StatusBar;

/**
 * 
 * Representiert eine Übertragungsfunktion, welche durch ein
 * Annäherungsverfahren möglichst genau an ein {@link Target} angepasst wurde.
 * Diese Annäherung läuft in einem eigenen SwingWorker ab und es wird die Klasse
 * {@link StableFMinSearch} verwendet.
 * 
 * @author Team 1
 *
 */
public class Approximation extends SwingWorker<Object, Message> implements SwingWorkerClient {

	// Verknüpfungen zu anderen Objekten:
	private Network network;

	// Eigenschaften:
	private int order;

	// Resultat:
	private UTFDatatype utf = new UTFDatatype();
	private double[][] stepResponse;
	private PointValuePair[] pole = new PointValuePair[2];
	private double korrKoef = 0;
	private double[][] absolutError;

	// -----------------------------------------------------------------------------------------------------------------
	// Konstrucktor:
	// -----------------------------------------------------------------------------------------------------------------
	/**
	 * Erzeugt die {@link Approximation}. Hierbei wird die Polstellenordnung der
	 * Übertragungsfunktion, sowie das anzunähernde {@link Target} definiert.
	 * Dafür muss beim Erzeugen das {@link Network}, welches die
	 * {@link Approximation} erzeugt, sich selber als Argument übergeben.
	 * 
	 * @param order
	 * @param network
	 */
	public Approximation(int order, Network network) {
		this.order = order;
		this.network = network;
	}

	// -----------------------------------------------------------------------------------------------------------------
	// Berechnungs Methoden:
	// -----------------------------------------------------------------------------------------------------------------
	private void calculate() throws TimeoutException {

		// Eigentliche Berechnung:
		PointValuePair optimum = StableFMinSearch.getUtfN(network.getTarget(), order,
				network.getStartWerte()[order - 1], this, 2, network.getThreshold());

		// Schreibe die berechnete Übertragungsfunktion in die entsprechende
		// Variable:
		utf = new UTFDatatype();
		utf.ordnung = order;
		utf.zaehler = optimum.getPoint()[0]/network.getMeasurementData().getstepHeight();
		if (optimum.getPoint().length % 2 == 1) {
			utf.koeffWQ = new double[optimum.getPoint().length - 1];

		} else {
			utf.koeffWQ = new double[optimum.getPoint().length - 2];
			utf.sigma = optimum.getPoint()[optimum.getPoint().length - 1];

			utf.sigma *= Math.pow(10.0, -network.getMeasurementData().getTimeScaleFactor());

		}
		for (int i = 0; i < utf.koeffWQ.length; i++) {
			utf.koeffWQ[i] = optimum.getPoint()[i + 1];
			// Zeitachse zurückskalieren:
			if (i % 2 == 0) {
				utf.koeffWQ[i] *= Math.pow(10.0, -network.getMeasurementData().getTimeScaleFactor());
			}
		}

		// Dazugehörige Sprungantwort berechnen:
		calculateStep();

		// Dazugehörige Polstellen berechnen:
		calculatePole();

		// Den Korelationskoeffizienten berechnen:
		calculateKorrKoeff();

		// Den Fehler berechnen:
		calculateError();

		// Den Benutzer informieren:
		if (network.isCancelled() == false) {
			swingAction(new Message("Berechnung abgeschlossen (Ordnung " + order + ").", false));
		}

	}

	private void calculateStep() {
		double[] points = new double[utf.ordnung + 1];
		points[0] = utf.zaehler*network.getMeasurementData().getstepHeight();
		for (int i = 0; i < utf.koeffWQ.length; i++) {
			points[i + 1] = utf.koeffWQ[i];
		}
		if (utf.ordnung % 2 == 1) {
			points[points.length - 1] = utf.sigma;
		}
		stepResponse = new double[][] { network.getMeasurementData().getFinalData()[0],
				Target.omega2polstep(points, network.getMeasurementData().getFinalData()[0]) };
	}

	private void calculatePole() {
		double[] nenner1 = new double[utf.ordnung + 1];
		double[] nenner2 = new double[utf.ordnung + 1];
		if (utf.ordnung != 1) {
			if (utf.ordnung % 2 == 0) {
				nenner1 = new double[] { 1, utf.koeffWQ[0] / utf.koeffWQ[1], Math.pow(utf.koeffWQ[0], 2.0) };
				for (int i = 2; i < utf.ordnung - 1; i += 2) {
					nenner2 = new double[] { 1, utf.koeffWQ[i] / utf.koeffWQ[i + 1], Math.pow(utf.koeffWQ[i], 2.0) };
					nenner1 = Matlab.conv(nenner1, nenner2);
				}
			} else {
				double[] koeffAll = new double[utf.ordnung];
				for (int l = 0; l < utf.koeffWQ.length; l++) {
					koeffAll[l] = utf.koeffWQ[l];
				}
				koeffAll[koeffAll.length - 1] = utf.sigma;

				nenner1 = new double[] { 1, utf.koeffWQ[0] / utf.koeffWQ[1], Math.pow(utf.koeffWQ[0], 2.0) };
				for (int i = 2; i < utf.ordnung - 1; i += 2) {
					nenner2 = new double[] { 1, utf.koeffWQ[i] / utf.koeffWQ[i + 1], Math.pow(utf.koeffWQ[i], 2.0) };
					nenner1 = Matlab.conv(nenner1, nenner2);
				}
				nenner1 = Matlab.conv(new double[] { 1, -utf.sigma }, nenner1);
			}
		} else {
			nenner1 = new double[] { 1, -utf.sigma };
		}
		Complex[] roots = new Complex[utf.ordnung];
		try {
			roots = Matlab.roots(nenner1);
		} catch (Exception e) {

		}

		// Den Wert der Polstellen in die entsprechende Variable schreiben:
		double[] real = new double[order];
		double[] imag = new double[order];
		for (int i = 0; i < utf.ordnung; i++) {
			real[i] = roots[i].getReal();
			imag[i] = roots[i].getImaginary();
		}
		pole[0] = new PointValuePair(real, 0);
		pole[1] = new PointValuePair(imag, 0);
	}

	private void calculateKorrKoeff() {
		korrKoef = Korrelation.korrKoeff(network.getMeasurementData().getFinalData()[1], stepResponse[1]);
	}

	private void calculateError() {
		absolutError = new double[2][stepResponse[0].length];
		for (int i = 0; i < stepResponse[0].length; i++) {
			absolutError[0][i] = stepResponse[0][i];
			absolutError[1][i] = stepResponse[1][i] - network.getMeasurementData().getFinalData()[1][i];
		}
	}

	@Override
	protected Object doInBackground() throws Exception {
		swingAction(new Message("Berechnung gestarted (Ordnung " + order + ").", false));
		try {
			calculate();
		} catch (TimeoutException e) {
			if (network.isCancelled() == false) {
				swingAction(new Message(
						"Probleme bei der Berechnung (Ordnung " + order
								+ ").\nVersuchen Sie folgendes:\n1) Versichern Sie sich, dass die Messwerte korekt bearbeited wurden.\n2) Passen Sie den Threshold an und starten Sie dann die Berechnung neu.",
						true));
			}
		}
		return 0;
	}

	@Override
	protected void process(List<Message> arg) {
		if (network.isCancelled() == false) {
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

	@Override
	protected void done() {
		super.done();
		network.approximationDone();
	}

	@Override
	public void swingAction(Message info) {
		publish(info);
	}

	// -----------------------------------------------------------------------------------------------------------------
	// Geter- und Seter-Methoden:
	// -----------------------------------------------------------------------------------------------------------------
	public double[][] getStepResponse() {
		return stepResponse;
	}

	public UTFDatatype getUtf() {
		return utf;
	}

	public PointValuePair[] getPole() {
		return pole;
	}

	public double getKorrKoef() {
		return korrKoef;
	}

	public void setUtf(UTFDatatype utf) {
		this.utf = utf;
		calculateStep();
		calculatePole();
		calculateKorrKoeff();
		calculateError();
		network.approximationDone();
	}

	public double[][] getError() {
		return absolutError;
	}
}