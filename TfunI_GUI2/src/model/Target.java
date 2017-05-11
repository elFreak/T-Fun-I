package model;

import org.apache.commons.math3.analysis.MultivariateFunction;

import matlabfunction.Matlab;
import matlabfunction.SVTools;

public class Target implements MultivariateFunction {

	private double[] t;
	private double[] step_soll;
	private int ord;

	public Target(double[] t, double[] step_soll, int ord) {
		this.t = t;
		this.step_soll = step_soll;
		this.ord = ord;
	}

	public double value(double[] variables) {
		//final double x0 = variables[0];
		//final double x1 = variables[1];
		//final double x2 = variables[2];
		//final double x3 = variables[3];
		//final double x4 = variables[4];
		//final double x5 = variables[5];
		//final double x6 = variables[6];
		//final double x7 = variables[7];
		
		return failuresum(step_soll, omega2polstep(ord, variables, t));

	}

	//x0=[K wp1 qp1 wp2 qp2 wp3 qp3]        n=even
	// x0=[K wp1 qp1 wp2 qp2 wp3 qp3 sig]    n=odd
	
/**
 * Schrittanwort aus wp und qp berechnen.
 * @param ordnung
 * @param data
 * @param time
 * @return
 */

	public static double[] omega2polstep(int ordnung, double[] data, double[] time) {
		double[] zaehler1 = new double[1];
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

	private static double failuresum(double[] should, double[] is) {

		double failuresum = 0;

		for (int i = 0; i < is.length; i++) {
			failuresum = failuresum + Math.pow(should[i] - is[i], 2);
		}
		System.out.println(""+failuresum);

		return failuresum;
	}
}