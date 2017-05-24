package model;

import java.util.List;
import javax.swing.SwingWorker;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.optim.PointValuePair;

import matlabfunction.Matlab;
import model.fMinSearch.StableFMinSearch;
import model.fMinSearch.SwingWorkerClient;
import model.fMinSearch.SwingWorkerInfoDatatype;
import userInterface.StatusBar;

public class Approximation extends SwingWorker<Object, SwingWorkerInfoDatatype> implements SwingWorkerClient {

	/**
	 * Generale Daten:
	 */
	private MeasurementData measurementData;
	private Model model;

	/**
	 * Schrittantwort, Polstellen und Übertragungsfunktion:
	 */
	private UTFDatatype[] utf = new UTFDatatype[9];
	private double[][][] stepResponse = new double[10][][];
	private PointValuePair[][] pole = new PointValuePair[9][2];

	/**
	 * Normierte Eingangssignale (Sprungantwort aus der Klasse Messwerte):
	 */
	private static final int NORM_NUMBER_OF_DATA = 400;

	/**
	 * Erstellt ein Objekt welches in der Lage ist, zu einer gegebenen
	 * Schrittantwort Übertragungsfunktionen zu berechnen. Das Format der
	 * Übertragungsfunktion wird in der Klasse UTFDatentyp beschrieben. Es
	 * werden immer 8 Funktionen mit berechnet. Damit werden die
	 * Polstellen-Ordnungen 2-10 abgedeckt. Die Berechnung wird in einem eigenen
	 * Task durchgeführt. Die Übertragungsfunktionen werden so berechnet, dass
	 * sie möglichst genau mit den Messdaten übereitstimmt.
	 * 
	 * @param measurementData
	 * @param model
	 */
	public Approximation(MeasurementData measurementData, Model model) {
		// Setze die Grundlegenden Verknüpfungen der Klasse:
		this.measurementData = measurementData;
		this.model = model;

		// Started die Berechnung:
		this.execute();
	}

	/**
	 * Berechnet alle 8 Übertragungsfunktionen sowie die dazugehörigen
	 * Polstellen und Schrittantworten.
	 */
	private void berechne() {

		// Die anzunähernde Sprungantwort wird normiert um die Berechnung zu
		// vereinfachen:
		double[][] stepResponseMeasurement = scalingStepResponse(measurementData.getFinalData());
		double[] timeFullNormed = stepResponseMeasurement[0];
		double[] stepFullNormed = stepResponseMeasurement[1];
		double[] timeLenghtNormed = stepResponseMeasurement[2];

		// Startwerte werden berechnet:
		Target target = new Target(timeFullNormed, stepFullNormed);
		PointValuePair[] startValues = StableFMinSearch.getStartValues(target, this);
		SwingWorkerInfoDatatype info = new SwingWorkerInfoDatatype();
		info.statusFehler = false;
		info.isStatus = true;
		info.statusText = "Startwerte berechnet.";
		swingAction(info);

		// Die Übertragungsfunktion wird berechnet:
		for (int k = 2; k <= 10; k++) {

			// Berechnet aus den vorher berechneten Startwerten eine möglichst
			// genaue Übertragungsfunktion.
			PointValuePair optimum = StableFMinSearch.getUtfN(target, k, startValues[k - 2], this, 3);
			utf[k - 2] = new UTFDatatype();
			utf[k - 2].ordnung = optimum.getPoint().length - 1;
			utf[k - 2].zaehler = optimum.getPoint()[0];
			if (optimum.getPoint().length % 2 == 1) {
				utf[k - 2].koeffWQ = new double[optimum.getPoint().length - 1];

			} else {
				utf[k - 2].koeffWQ = new double[optimum.getPoint().length - 2];
				utf[k - 2].sigma = optimum.getPoint()[optimum.getPoint().length - 1];
			}
			for (int i = 0; i < utf[k - 2].koeffWQ.length; i++) {
				utf[k - 2].koeffWQ[i] = optimum.getPoint()[i + 1];
			}

			// Dazugehörige Sprungantwort berechnen:
			stepResponse[k - 2] = new double[][] { timeLenghtNormed,
					Target.omega2polstep(optimum.getPoint(), timeFullNormed) };

			// Dazugehörige Polstellen berechnen:
			double[] nenner1 = new double[utf[k - 2].ordnung + 1];
			double[] nenner2 = new double[utf[k - 2].ordnung + 1];
			if (utf[k - 2].ordnung % 2 == 0) {
				nenner1 = new double[] { 1, utf[k - 2].koeffWQ[0] / utf[k - 2].koeffWQ[1],
						Math.pow(utf[k - 2].koeffWQ[0], 2.0) };
				for (int i = 2; i < utf[k - 2].ordnung - 1; i += 2) {
					nenner2 = new double[] { 1, utf[k - 2].koeffWQ[i] / utf[k - 2].koeffWQ[i + 1],
							Math.pow(utf[k - 2].koeffWQ[i], 2.0) };
					nenner1 = Matlab.conv(nenner1, nenner2);
				}
			} else {
				double[] koeffAll = new double[utf[k - 2].ordnung];
				for (int l = 0; l < utf[k - 2].koeffWQ.length; l++) {
					koeffAll[l] = utf[k - 2].koeffWQ[l];
				}
				koeffAll[koeffAll.length - 1] = utf[k - 2].sigma;

				nenner1 = new double[] { 1, utf[k - 2].koeffWQ[0] / utf[k - 2].koeffWQ[1],
						Math.pow(utf[k - 2].koeffWQ[0], 2.0) };
				for (int i = 2; i < utf[k - 2].ordnung - 1; i += 2) {
					nenner2 = new double[] { 1, utf[k - 2].koeffWQ[i] / utf[k - 2].koeffWQ[i + 1],
							Math.pow(utf[k - 2].koeffWQ[i], 2.0) };
					nenner1 = Matlab.conv(nenner1, nenner2);
				}
				nenner1 = Matlab.conv(new double[] { 1, -utf[k - 2].sigma }, nenner1);
			}
			Complex[] roots = new Complex[utf[k - 2].ordnung];
			roots = Matlab.roots(nenner1);

			// Den Wert der Polstellen in die entsprechende Variable schreiben:
			double[] real = new double[k];
			double[] imag = new double[k];
			for (int i = 0; i < utf[k - 2].ordnung; i++) {
				real[i] = roots[i].getReal();
				imag[i] = roots[i].getImaginary();
			}
			pole[k - 2][0] = new PointValuePair(real, 0);
			pole[k - 2][1] = new PointValuePair(imag, 0);

			SwingWorkerInfoDatatype info2 = new SwingWorkerInfoDatatype();
			info2.statusFehler = false;
			info2.isStatus = true;
			info2.statusText = "Ordnung " + k + " berechnet.";
			swingAction(info2);

			SwingWorkerInfoDatatype info3 = new SwingWorkerInfoDatatype();
			info3.isActuallised = true;
			swingAction(info3);
		}
	}

	@Override
	protected Object doInBackground() {
		berechne();
		return 0;
	}

	@Override
	protected void process(List<SwingWorkerInfoDatatype> arg) {
		super.process(arg);
		for (int i = 0; i < arg.size(); i++) {
			SwingWorkerInfoDatatype info = arg.get(i);
			if (info.isStatus) {
				if (info.statusFehler) {
					StatusBar.showStatus(info.statusText, StatusBar.FEHLER);
				} else {
					StatusBar.showStatus(info.statusText, StatusBar.INFO);
				}

			}
			if (info.isActuallised) {
				if (isCancelled() == false) {
					model.notifyObservers(Model.NOTIFY_REASON_APPROXIMATION_DONE);
				}
			}
		}

	}

	@Override
	protected void done() {
		super.done();
		StatusBar.showStatus("Approximation beendet.", StatusBar.INFO);
	}

	/**
	 * Normiert die Sprungantwort: 1) Normiert die Zeitachse 2) Normiert die
	 * Anzahl der Messpunkte
	 * 
	 * @param stepResponseOriginal
	 * @return
	 */
	private static double[][] scalingStepResponse(double[][] stepResponseOriginal) {

		// Berechnet den Faktor um die Anzahl der Messpunkte zu normieren:
		int originalLenght = stepResponseOriginal[0].length;
		int factor = originalLenght / NORM_NUMBER_OF_DATA;
		factor = (int) Math.max(1.0, factor);

		// Berechnet den Faktor um die Zeitachse zu normieren:
		double scalefactorTime = Math.log10(stepResponseOriginal[0][stepResponseOriginal[0].length - 1]) - 1;

		// Erzeugt die Strucktur der normierten Daten:
		int newLenght = originalLenght / factor;
		double[][] stepResponseNew = new double[3][newLenght];

		// Erstellt die normierten Daten:
		for (int i = 0; i < newLenght; i++) {
			stepResponseNew[0][i] = stepResponseOriginal[0][i * factor] * Math.pow(10.0, -scalefactorTime); // Zeit
																											// normiert
																											// und
																											// Anzahl
																											// Messwerte
																											// normiert.
			stepResponseNew[1][i] = stepResponseOriginal[1][i * factor]; // Anzahl
																			// Messwerte
																			// normiert.
			stepResponseNew[2][i] = stepResponseOriginal[0][i * factor]; // Anzahl
																			// Messwerte
																			// normiert.
		}

		return stepResponseNew;
	}

	@Override
	public void swingAction(SwingWorkerInfoDatatype info) {
		publish(info);
	}

	/**
	 * Gibt die Übertragungsfunktion der Polstellen-Ordnung 2-10 zurück.
	 * 
	 * @return
	 */
	public UTFDatatype[] getUtf() {
		return utf;
	}

	/**
	 * Gibt die Schrittantwort der Polstellen-Ordnung 2-10 zurück.
	 * 
	 * @return
	 */
	public double[][][] getStepResponse() {
		if (stepResponse != null) {
			return stepResponse;
		} else {
			return null;
		}
	}

	/**
	 * Gibt die Polstellenposition der Polstellen-Ordnung 2-10 zurück.
	 * 
	 * @return
	 */
	public PointValuePair[][] getPole() {
		if (pole != null) {
			return pole;
		} else {
			return null;
		}
	}
}