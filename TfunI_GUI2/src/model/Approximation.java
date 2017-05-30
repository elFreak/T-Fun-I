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
 * Klasse Approximation: Verwalted eine Übertragungsfunktion
 * (Annäherungsrechnung). Diese Funktion hat eine feste Polstellenordnung
 * zwischen 1 und 10.
 * 
 * @author Team 1
 *
 */
public class Approximation extends SwingWorker<Object, Message> implements SwingWorkerClient {

	/**
	 * Verknüpfungen zu anderen Objekten:
	 */
	Network network;

	/**
	 * Eigenschaften:
	 */
	private int order;

	/**
	 * Resultat:
	 */
	private UTFDatatype utf = new UTFDatatype();
	private double[][] stepResponse;
	private PointValuePair[] pole = new PointValuePair[2];
	private double korrKoef = 0;

	// -----------------------------------------------------------------------------------------------------------------
	// Konstrucktor:
	// -----------------------------------------------------------------------------------------------------------------
	/**
	 * Erzeugt ein Objekt, welches eine Übertragungsfunktion von einer
	 * Polstellenordnung representiert. Dieses Objekt kann diese
	 * Übertragungsfunktion sowie die dazugehörigen Eigenschaften in einem
	 * eigenen Thread berechnen.
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
	/**
	 * Berechnet anhand des Netzwerkes eine Übertragungsfunktion der gegebenen
	 * Polstellenordnung.
	 * 
	 * @throws TimeoutException
	 */
	private void calculate() throws TimeoutException {

		// Eigentliche Berechnung:
		PointValuePair optimum = StableFMinSearch.getUtfN(network.getTarget(), order,
				network.getStartWerte()[order - 1], this, 3, network.getThreshold());

		// Schreibe die berechnete Übertragungsfunktion in die entsprechende
		// Variable:
		utf = new UTFDatatype();
		utf.ordnung = order;
		utf.zaehler = optimum.getPoint()[0];
		if (optimum.getPoint().length % 2 == 1) {
			utf.koeffWQ = new double[optimum.getPoint().length - 1];

		} else {
			utf.koeffWQ = new double[optimum.getPoint().length - 2];
			utf.sigma = optimum.getPoint()[optimum.getPoint().length - 1];
		}
		for (int i = 0; i < utf.koeffWQ.length; i++) {
			utf.koeffWQ[i] = optimum.getPoint()[i + 1];
		}

		// Dazugehörige Sprungantwort berechnen:
		calculateStep();

		// Dazugehörige Polstellen berechnen:
		calculatePole();

		// Den Korelationskoeffizienten berechnen:
		calculateKorrKoeff();

		// Den Benutzer informieren:
		if (network.isCancelled() == false) {
			swingAction(new Message("Berechnung abgeschlossen (Ordnung " + order + ").", false));
		}

	}

	private void calculateStep() {
		double[] points = new double[utf.ordnung + 1];
		points[0] = utf.zaehler;
		for (int i = 0; i < utf.koeffWQ.length; i++) {
			points[i + 1] = utf.koeffWQ[i];
		}
		if (utf.ordnung % 2 == 1) {
			points[points.length - 1] = utf.sigma;
		}

		stepResponse = new double[][] { network.getMeasurementData().getTimeLenghtNormed(),
				Target.omega2polstep(points, network.getMeasurementData().getTimeFullNormed()) };
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
		korrKoef = Korrelation.korrKoeff(network.getMeasurementData().getStepFullNormed(), stepResponse[1]);
	}

	/**
	 * Definiert, was im eigenen Thread gemacht werden soll.
	 * 
	 * @throws Exception
	 */
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

	/**
	 * Dient dazu den Benutzer mittels der StatusBar zu informieren.
	 */
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

	/**
	 * Sobald der Thread beendet wird werden die Observer des Models informiert.
	 */
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
	// Get-Methoden:
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
		network.approximationDone();
	}

}