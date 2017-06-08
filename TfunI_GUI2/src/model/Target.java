package model;

import org.apache.commons.math3.analysis.MultivariateFunction;

import matlabfunction.Matlab;
import matlabfunction.SVTools;

/**
 * Representiert eine Funktion, welche die Abweichung von einer
 * Soll-Sprungantwort und einer Ist-Sprungantwort berechnet. Dieser Funktion
 * wird beim erstellen ({@link #Target(double[], double[])}) eine
 * Soll-Sprungantwort fest zugeordnet. Danach kann mittels der Methode
 * {@link #value(double[])} die Abweichung berechnet werden. Zusätzlich
 * beinhalted die Klasse eine die statische Methode
 * {@link #omega2polstep(double[], double[])} mit welcher eine Sprungantwort
 * anhand einer Übertragungsfunktion berechnet werden kann.
 *
 * @author Team 1
 *
 */
public class Target implements MultivariateFunction {

	private double[] shouldTime;
	private double[] step_soll;

	/**
	 * Konstruiert ein Objekt. Dabei wird die Soll-Sprungantwort mitgegeben.
	 * 
	 * @param shouldTime
	 * @param step_soll
	 */
	public Target(double[] shouldTime, double[] step_soll) {
		this.shouldTime = shouldTime;
		this.step_soll = step_soll;
	}

	/**
	 * Berechnet die zur Übertragungsfunktion (variables) gehörende
	 * Sprungantwort und vergleicht diese mit der Soll-Sprungantwort des
	 * Objekts. Dabei wird die Abweichung der beiden Sprungantworten zu einander
	 * berechnet. Diese Berechnung basiert auf der Summe der quadrierten
	 * Differenz der beiden Kurven.
	 * 
	 * @param variables
	 * @return distance
	 * @exception RuntimeException
	 */
	public double value(double[] variables) throws RuntimeException {
		double distance = 0.0;
		distance = failuresum(step_soll, omega2polstep(variables, shouldTime));
		return distance;

	}

	/**
	 * Diese statische Methode berechnet anhand einer Übertragungsfunktion eine
	 * Sprungantwort und gibt diese zurück.
	 * 
	 * @param utf
	 * @param time
	 * @return stepResponse
	 */
	public static double[] omega2polstep(double[] utf, double[] time) {
		double[] zaehler1 = new double[1];
		int ordnung = utf.length - 1;
		double[] nenner1 = new double[ordnung + 1];
		double[] nenner2 = new double[ordnung + 1];
		double[] stepResponse = new double[time.length];
		if (ordnung != 1) {
			if (ordnung % 2 == 0) {
				zaehler1[0] = utf[0] * Math.pow(utf[1], 2.0);
				nenner1 = new double[] { 1, utf[1] / utf[2], Math.pow(utf[1], 2.0) };
				for (int i = 2; i < ordnung - 1; i += 2) {
					nenner2 = new double[] { 1, utf[i + 1] / utf[i + 2], Math.pow(utf[i + 1], 2.0) };
					nenner1 = Matlab.conv(nenner1, nenner2);
					zaehler1[0] = zaehler1[0] * Math.pow(utf[i + 1], 2.0);
				}
			} else {
				zaehler1[0] = utf[0] * Math.pow(utf[1], 2.0);
				nenner1 = new double[] { 1, utf[1] / utf[2], Math.pow(utf[1], 2.0) };
				for (int i = 2; i < ordnung - 1; i += 2) {
					nenner2 = new double[] { 1, utf[i + 1] / utf[i + 2], Math.pow(utf[i + 1], 2.0) };
					nenner1 = Matlab.conv(nenner1, nenner2);
					zaehler1[0] = zaehler1[0] * Math.pow(utf[i + 1], 2.0);
				}
				nenner1 = Matlab.conv(new double[] { 1, -utf[ordnung] }, nenner1);
				zaehler1[0] = zaehler1[0] * Math.abs(utf[ordnung]);
			}
			stepResponse = (double[]) SVTools.step(zaehler1, nenner1, time)[0];
		} else {
			zaehler1[0] = utf[0] * Math.abs(utf[ordnung]);
			nenner1 = new double[] { 1, -utf[1] };
			stepResponse = (double[]) SVTools.step(zaehler1, nenner1, time)[0];
		}

		return stepResponse;
	}

	/**
	 * Berechnet mit dem bei {@link #value(double[])} beschriebenen Verfahren die Abweichung zwischen zwei Vektoren.
	 * 
	 * @param should
	 * @param is
	 * @return failuresum
	 */
	private double failuresum(double[] should, double[] is) {

		double failuresum = 0;

		double max = 0;
		for (int i = 0; i < is.length; i++) {
			failuresum = failuresum + (Math.pow(should[i] - is[i], 2));
			if (should[i] > max) {
				max = should[i];
			}
		}
		failuresum *= 1000.0;
		failuresum /= is.length;
		failuresum /= max;

		return failuresum;
	}

	/**
	 * Gibt eine Kopie des Target zurück.
	 * 
	 * @return copyOfTarget
	 */
	public Target copy() {
		return new Target(shouldTime, step_soll);
	}
}