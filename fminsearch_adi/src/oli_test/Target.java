package oli_test;

import org.apache.commons.math3.analysis.MultivariateFunction;
import org.apache.commons.math3.util.FastMath;

import pro2e.teamX.matlabfunctions.Matlab;
import pro2e.teamX.matlabfunctions.SVTools;

public class Target implements MultivariateFunction {
	
	Approximation approximation;
	ApproximationSettings approximationsettings = new ApproximationSettings();
	
	public Target(Approximation approx){
		this.approximation=approx;
	}



	public double value(double[] variables) {
			final double x = variables[0];
			final double y = variables[1];
			return FastMath.cos(x) * FastMath.sin(y);

		

		
		
/*
		double[] Zp= new double[] { 1 };
		double[] Np = new double[] { 1, 2, 3 };
		double[] t = approximation.step_response_scaled[1];
		
		
		double[] Polstellen = (double[]) Matlab.residue(Zp, Np)[1]; 
		
		System.out.println(Polstellen);

		
		
		
		return failuresum(approximation.step_response_scaled[0],((double[]) SVTools.step(Zp, Np, t)[0]));
	}

	private static double failuresum(double[] should, double[] is) {

		double failuresum = 0;

		for (int i = 0; i < is.length; i++) {
			failuresum = failuresum + Math.pow(should[i] - is[i], 2);
		}
		return failuresum;*/
	}
}
