package model;

import java.util.List;
import javax.swing.SwingWorker;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.optim.PointValuePair;

import matlabfunction.Matlab;
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
	private UTFDatatype[] utf = new UTFDatatype[8];
	private double[][][] stepResponse;
	private double[][][] pole;

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
	}

	/**
	 * Berechnet alle 8 Übertragungsfunktionen sowie die dazugehörigen
	 * Polstellen und Schrittantworten.
	 */
	private void berechne() {

		// Die anzunähernde Sprungantwort wird normiert um die Berechnung zu
		// vereinfachen.
		double[][] stepResponseMeasurement = scalingStepResponse(measurementData.getFinalData());
		double[] timeFullNormed = stepResponseMeasurement[0];
		double[] stepFullNormed = stepResponseMeasurement[1];
		double[] timeLenghtNormed = stepResponseMeasurement[2];
	
		// Die Übertragungsfunktion wird berechnet;
		Target target = new Target(timeFullNormed, stepFullNormed);
		PointValuePair optimum = StableFMinSearch.fminsearch(target, 10, this);
		utf[0] = new UTFDatatype();
		utf[0].ordnung = optimum.getPoint().length - 1;
		utf[0].zaehler = optimum.getPoint()[0];
		if (optimum.getPoint().length % 2 == 1) {
			utf[0].koeffWQ = new double[optimum.getPoint().length - 1];
			
		} else {
			utf[0].koeffWQ = new double[optimum.getPoint().length - 2];
			utf[0].sigma = optimum.getPoint()[optimum.getPoint().length - 1];
		}
		for (int i = 0; i < utf[0].koeffWQ.length ; i++) {
			utf[0].koeffWQ[i] = optimum.getPoint()[i + 1];
		}

		// Dazugehörige Sprungantwort berechnen:
		stepResponse = new double[][][] {
				{ timeLenghtNormed, Target.omega2polstep(optimum.getPoint(), timeFullNormed) } };
		
		// Dazugehörige Polstellen berechnen:
		pole = new double[10][2][utf[0].ordnung];
		double[] nenner1 = new double[utf[0].ordnung + 1];
		double[] nenner2 = new double[utf[0].ordnung + 1];
		if (utf[0].ordnung % 2 == 0) {
			nenner1 = new double[] { 1, utf[0].koeffWQ[0] / utf[0].koeffWQ[1], Math.pow(utf[0].koeffWQ[0], 2.0) };
			for (int i = 2; i < utf[0].ordnung - 1; i += 2) {
				nenner2 = new double[] { 1, utf[0].koeffWQ[i] / utf[0].koeffWQ[i + 1], Math.pow(utf[0].koeffWQ[i], 2.0) };
				nenner1 = Matlab.conv(nenner1, nenner2);
			}
		} else {
			nenner1 = new double[] { 1, utf[0].koeffWQ[0] / utf[0].koeffWQ[1], Math.pow(utf[0].koeffWQ[0], 2.0) };
			for (int i = 2; i < utf[0].ordnung - 1; i += 2) {
				nenner2 = new double[] { 1, utf[0].koeffWQ[i] / utf[0].koeffWQ[i + 1], Math.pow(utf[0].koeffWQ[i], 2.0) };
				nenner1 = Matlab.conv(nenner1, nenner2);
			}
			nenner1 = Matlab.conv(new double[] { 1, -utf[0].koeffWQ[utf[0].ordnung - 1] }, nenner1);
		}
		Complex[] roots = new Complex[utf[0].ordnung];
		roots = Matlab.roots(nenner1);
		for (int i = 0; i < utf[0].ordnung; i++) {
			pole[0][0][i] = roots[i].getReal();
			pole[0][1][i] = roots[i].getImaginary();
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
				if (info.isFehler) {
					StatusBar.showStatus(info.status, StatusBar.FEHLER);
				} else {
					StatusBar.showStatus(info.status, StatusBar.INFO);
				}

			}
			// if (info.isUtfActuallised) {
			// zwischenSignal = (new double[][] { timeShort,
			// Target.omega2polstep(info.utfKoeff, timeNormed) });
			// model.notifyObservers(Model.NOTIFY_REASON_APPROXIMATION_ZWISCHENWERT);
			//
			// }
		}

	}

	@Override
	protected void done() {
		super.done();
		StatusBar.showStatus("Approximation beendet.", StatusBar.INFO);
		model.notifyObservers(Model.NOTIFY_REASON_APPROXIMATION_DONE);
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
		return stepResponse;
	}

	/**
	 * Gibt die Polstellenposition der Polstellen-Ordnung 2-10 zurück.
	 * 
	 * @return
	 */
	public double[][][] getPole() {
		return pole;
	}
}

//
// package model;
//
// import java.util.List;
// import javax.swing.SwingWorker;
//
// import org.apache.commons.math3.complex.Complex;
// import org.apache.commons.math3.optim.PointValuePair;
//
// import matlabfunction.Matlab;
// import matlabfunction.SVTools;
// import userInterface.StatusBar;
//
// public class Approximation extends SwingWorker<Object,
// SwingWorkerInfoDatatype> implements SwingWorkerClient {
//
//
// /**
// * Generale Daten:
// */
// private MeasurementData measurementData;
// private Model model;
//
//
// /**
// * Schrittantwort und Polstellen:
// */
// private double[][] solutionSignal;
// private double[][] zwischenSignal;
// private double[] timeShort;
// private double[] timeNormed;
// private double[] stepNormed;
// private UTFDatatype utf = new UTFDatatype();
// private double[][] pole;
//
// /**
// *
// */
//
// /**
// * Erstellt ein Objekt welches in der Lage ist eine Übertragungsfunktion zu
// * berechnen. Die Berechnung wird in einem eigenen Task durchgeführt. Die
// * Übertragungsfunktion wird so berechnet, dass sie möglichst genau mit den
// * Messdaten übereitstimmt. Ein Objekt dieser Klasse kann nur eine
// * Übertragungsfunktion von genau einer Ordnung (Polstellen-Ordnung)
// * berechnen.
// *
// * @param measurementData
// * @param model
// */
// public Approximation(MeasurementData measurementData, Model model) {
// this.measurementData = measurementData;
// this.model = model;
// }
//
// /**
// * Eigentliche Berechnungsmethode.
// */
// private void berechne() {
//
// // Die Zeitachse der Messwerte wird normiert um die Berechnung zu
// // vereinfachen.
// timeNormed = scalingTime(measurementData.getFinalData())[0];
// stepNormed = scalingTime(measurementData.getFinalData())[1];
// timeShort = scalingTime(measurementData.getFinalData())[2];
//
// // Die Übertragungsfunktion und die Zusammenhänge mit den Messwerten
// // werden definiert.
// Target target = new Target(timeNormed, stepNormed);
//
// PointValuePair optimum = StableFMinSearch.fminsearch(target, 10, this);
// //
// System.out.println(""+optimum.getPoint()[0]+optimum.getPoint()[1]+optimum.getPoint()[2]+optimum.getPoint()[3]+optimum.getPoint()[4]);
//
// solutionSignal = new double[][] { timeShort,
// Target.omega2polstep(optimum.getPoint(), timeNormed) };
// utf[0].ordnung = optimum.getPoint().length - 1;
// if (optimum.getPoint().length % 2 == 1) {
// utf[0].koeffWQ = new double[optimum.getPoint().length - 1];
// utf[0].zaehler = optimum.getPoint()[0];
// for (int i = 0; i < optimum.getPoint().length - 1; i++) {
// utf[0].koeffWQ[i] = optimum.getPoint()[i + 1];
// }
// } else {
// utf[0].koeffWQ = new double[optimum.getPoint().length - 2];
// utf[0].zaehler = optimum.getPoint()[0];
// for (int i = 0; i < optimum.getPoint().length - 2; i++) {
// utf[0].koeffWQ[i] = optimum.getPoint()[i + 1];
// }
// utf[0].sigma = optimum.getPoint()[optimum.getPoint().length - 1];
// }
//
// pole = new double[2][utf[0].ordnung];
// for (int i = 0; i < utf[0].ordnung; i += 2) {
// double omega = utf[0].koeffWQ[i];
// double q = utf[0].koeffWQ[i + 1];
// double im = Math.sqrt((Math.pow(omega, 2) * (1 - 4 * Math.pow(q, 2))) / (-4.0
// * Math.pow(q, 2)));
// double re = -Math.sqrt(Math.pow(omega, 2) - Math.pow(im, 2));
// pole[0][i] = re;
// pole[1][i] = im;
// pole[0][i + 1] = re;
// pole[1][i + 1] = -im;
// }
// if (utf[0].ordnung % 2 == 1) {
// pole[0][utf[0].ordnung - 1] = utf[0].sigma;
// pole[1][utf[0].ordnung - 1] = 0;
// }
//
// double[] nenner1 = new double[utf[0].ordnung + 1];
// double[] nenner2 = new double[utf[0].ordnung + 1];
// if (utf[0].ordnung % 2 == 0) {
// nenner1 = new double[] { 1, utf[0].koeffWQ[0] / utf[0].koeffWQ[1],
// Math.pow(utf[0].koeffWQ[0], 2.0) };
// for (int i = 2; i < utf[0].ordnung - 1; i += 2) {
// nenner2 = new double[] { 1, utf[0].koeffWQ[i] / utf[0].koeffWQ[i+1],
// Math.pow(utf[0].koeffWQ[i], 2.0) };
// nenner1 = Matlab.conv(nenner1, nenner2);
// }
// } else {
// nenner1 = new double[] { 1, utf[0].koeffWQ[0] / utf[0].koeffWQ[1],
// Math.pow(utf[0].koeffWQ[0], 2.0) };
// for (int i = 2; i < utf[0].ordnung - 1; i += 2) {
// nenner2 = new double[] { 1, utf[0].koeffWQ[i] / utf[0].koeffWQ[i+1],
// Math.pow(utf[0].koeffWQ[i], 2.0) };
// nenner1 = Matlab.conv(nenner1, nenner2);
// }
// nenner1 = Matlab.conv(new double[] { 1, -utf[0].koeffWQ[utf[0].ordnung-1] },
// nenner1);
// }
// Complex[] roots = new Complex[utf[0].ordnung];
// roots = Matlab.roots(nenner1);
// for(int i=0;i<utf[0].ordnung;i++) {
// pole[0][i] = roots[i].getReal();
// pole[1][i] = roots[i].getImaginary();
// }
//
// }
//
// @Override
// protected Object doInBackground() {
// berechne();
// return 0;
// }
//
// @Override
// protected void process(List<SwingWorkerInfoDatatype> arg) {
// super.process(arg);
// for (int i = 0; i < arg.size(); i++) {
// SwingWorkerInfoDatatype info = arg.get(i);
// if (info.isStatus) {
// if (info.isFehler) {
// StatusBar.showStatus(info.status, StatusBar.FEHLER);
// } else {
// StatusBar.showStatus(info.status, StatusBar.INFO);
// }
//
// }
// if (info.isUtfActuallised) {
// zwischenSignal = (new double[][] { timeShort,
// Target.omega2polstep(info.utfKoeff, timeNormed) });
// model.notifyObservers(Model.NOTIFY_REASON_APPROXIMATION_ZWISCHENWERT);
//
// }
// }
//
// }
//
// @Override
// protected void done() {
// super.done();
// StatusBar.showStatus("Approximation beendet.", StatusBar.INFO);
// model.notifyObservers(Model.NOTIFY_REASON_APPROXIMATION_DONE);
// }
//
// /**
// * Normiert die Zeitachse des gegebenen Arrays.
// *
// * @param step_response_m
// * @return
// */
// private static double[][] scalingTime(double[][] step_response_m) {
//
// int isLenght = step_response_m[0].length;
// int factor = isLenght/500;
// factor = (int)Math.max(1.0, factor);
// int actualLenght = isLenght/factor;
//
//
// double scalefactorTime =
// Math.log10(step_response_m[0][step_response_m[0].length - 1]) - 1;
// double[][] step_response_scaled_m = new double[3][actualLenght];
//
// for (int i = 0; i < actualLenght; i++) {
// step_response_scaled_m[0][i] = step_response_m[0][i*factor] * Math.pow(10.0,
// -scalefactorTime);
// step_response_scaled_m[1][i] = step_response_m[1][i*factor];
// step_response_scaled_m[2][i] = step_response_m[0][i*factor];
// }
//
// return step_response_scaled_m;
// }
//
// public double[][] getSolutionSignal() {
// return solutionSignal;
// }
//
// public void setSolutionSignal(double[][] solutionSignal) {
// this.solutionSignal = solutionSignal;
// }
//
// @Override
// public void swingAction(SwingWorkerInfoDatatype info) {
// publish(info);
// }
//
// public double[][] getZwischenSignal() {
// return zwischenSignal;
// }
//
// public UTFDatatype getUTF() {
// return utf;
// }
//
// public double[][] getPolePoint() {
// return pole;
// }
//
// }
//
//
