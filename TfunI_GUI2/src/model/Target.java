package model;

import org.apache.commons.math3.analysis.MultivariateFunction;

import matlabfunction.Matlab;
import matlabfunction.SVTools;

public class Target implements MultivariateFunction {

	public boolean isCanceled = false;
	private double[] t;
	private double[] step_soll;
	public double[] error = {};

	public Target(double[] t, double[] step_soll) {
		this.t = t;
		this.step_soll = step_soll;
	}

	public double value(double[] variables) throws RuntimeException {

		
		
		double error = 0.0;

		error = failuresum(step_soll, omega2polstep(variables, t));
		this.error = Matlab.concat(this.error, error / t.length);
		
//		if(isCanceled) {
			try {
				Thread.sleep(0, 10);
			} catch (InterruptedException e) {
				// TODO: handle exception
			}
//		}
		
		return error;

	}

	// x0=[K wp1 qp1 wp2 qp2 wp3 qp3] n=even
	// x0=[K wp1 qp1 wp2 qp2 wp3 qp3 sig] n=odd

	/**
	 * Schrittanwort aus K, wp und qp berechnen.
	 * 
	 * @param ordnung
	 * @param data
	 * @param time
	 * @return
	 */

	public static double[] omega2polstep(double[] data, double[] time) {
		double[] zaehler1 = new double[1];
		int ordnung = data.length - 1;
		double[] nenner1 = new double[ordnung + 1];
		double[] nenner2 = new double[ordnung + 1];
		double[] data2 = new double[time.length];
		if (ordnung % 2 == 0) {
			zaehler1[0] = data[0] * Math.pow(data[1], 2.0);
			nenner1 = new double[] { 1, data[1] / data[2], Math.pow(data[1], 2.0) };
			for (int i = 2; i < ordnung - 1; i += 2) {
				nenner2 = new double[] { 1, data[i + 1] / data[i + 2], Math.pow(data[i + 1], 2.0) };
				nenner1 = Matlab.conv(nenner1, nenner2);
				zaehler1[0] = zaehler1[0] * Math.pow(data[i + 1], 2.0);
			}
		} else {
			zaehler1[0] = data[0] * Math.pow(data[1], 2.0);
			nenner1 = new double[] { 1, data[1] / data[2], Math.pow(data[1], 2.0) };
			for (int i = 2; i < ordnung - 1; i += 2) {
				nenner2 = new double[] { 1, data[i + 1] / data[i + 2], Math.pow(data[i + 1], 2.0) };
				nenner1 = Matlab.conv(nenner1, nenner2);
				zaehler1[0] = zaehler1[0] * Math.pow(data[i + 1], 2.0);
			}
			nenner1 = Matlab.conv(new double[] { 1, -data[ordnung] }, nenner1);
			zaehler1[0] = zaehler1[0] * Math.abs(data[ordnung]);
		}
		data2 = (double[]) SVTools.step(zaehler1, nenner1, time)[0];

		return data2;
	}

	/**
	 * Fehlersumme berechnen
	 * 
	 * @param should
	 * @param is
	 * @return
	 */
	private static double failuresum(double[] should, double[] is) {

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

		// try {
		// Thread.sleep(0,1);
		// } catch (InterruptedException e) {
		// // TODO: handle exception
		// }

		// System.out.println(""+failuresum);

		return failuresum;
	}

	public double[] getTime() {
		return t;
	}

	public Target copy() {
		return new Target(t, step_soll);
	}
}