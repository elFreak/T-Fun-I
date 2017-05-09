package oli_test;

import java.util.Arrays;

import org.apache.commons.math3.analysis.MultivariateFunction;
import org.apache.commons.math3.optim.InitialGuess;
import org.apache.commons.math3.optim.MaxEval;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
import org.apache.commons.math3.optim.nonlinear.scalar.ObjectiveFunction;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.NelderMeadSimplex;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.SimplexOptimizer;
import org.apache.commons.math3.util.FastMath;

import pro2e.teamX.matlabfunctions.SVTools;
//import pro2e.teamX.matlabfunctions.NelderMeadSimplexSinusCosExample.Target;

public class Approximation{
	public static void main(String args[]) {
		Approximation approx = new Approximation();
		//calculate();
		//ApproximationSettings approximationsettings = new ApproximationSettings();
		/*
		SimplexOptimizer optimizer = new SimplexOptimizer(1e-10, 1e-30);
		Target target = new Target();

		PointValuePair optimum = optimizer.optimize(new MaxEval(approximationsettings.MaxEval_int), new ObjectiveFunction(target), GoalType.MINIMIZE,
			new InitialGuess(approximationsettings.start_values), new NelderMeadSimplex(new double[] { 0.02, 0.02 }));

		System.out.println(Arrays.toString(optimum.getPoint()) + " : " + optimum.getSecond());	*/
	}
	
	//Bleiben hier
	double[][] step_response = new double[2][6];
	double[][] step_response_scaled;
	public double correlation_coefficient;
	public double failuresum;
	
	 int order;
	 int value_reducer;
	 int exponent;
	 static double[] start_values = {3, 1.5};
	 static int MaxEval_int=100;
	
	public Approximation() {

		step_response_scaled = scaling(step_response);
		ApproximationSettings approximationsettings = new ApproximationSettings();
		Approximation.calculate(this);
		
	}

	public static void calculate(Approximation approx) {
		
		SimplexOptimizer optimizer = new SimplexOptimizer(1e-10, 1e-30);
		Target target = new Target(approx);

		PointValuePair optimum = optimizer.optimize(new MaxEval(MaxEval_int), new ObjectiveFunction(target), GoalType.MINIMIZE,
			new InitialGuess(start_values), new NelderMeadSimplex(new double[] { 0.02, 0.02 }));

		System.out.println(Arrays.toString(optimum.getPoint()) + " : " + optimum.getSecond());		
	}

	private static double[][] scaling(double[][] step_response_m) {
		double[] t_old = step_response_m[0];
		double scalefactor = Math.log10(t_old[t_old.length - 1]) - 1;
		double[][] step_response_scaled_m = new double[2][step_response_m[0].length];
		double[] t_scaled = new double[t_old.length];

		for (int i = 0; i < t_old.length; i++) {
			t_scaled[i] = t_old[i] * Math.pow(10.0, -scalefactor);
			step_response_scaled_m[0][i] = t_scaled[i];
		}

		return step_response_scaled_m;
	}
	
	public static void ApproximationSettings() {

		 
	}


}

