package pro2e.teamX.matlabfunctions;

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

public class NelderMeadSimplexSinusCosExample {

	public static void main(String[] args) {
		SimplexOptimizer optimizer = new SimplexOptimizer(0.00001,-1);
		Target target = new Target();

		PointValuePair optimum = optimizer.optimize(new MaxEval(10000), new ObjectiveFunction(target), GoalType.MINIMIZE,
				new InitialGuess(new double[] { 1,1,1,1,1,1,1,1,1,1,1 }), new NelderMeadSimplex(new double[] { 0.2,0.2,0.2,0.2,0.2,0.2,0.2,0.2,0.2,0.2,0.2}));

		System.out.println(Arrays.toString(optimum.getPoint()) + " : " + optimum.getSecond());
	}

	private static class Target implements MultivariateFunction {
		public double value(double[] variables) {
			final double a = variables[0];
			final double b = variables[1];
			final double c = variables[2];
			final double d = variables[3];
			final double e = variables[4];
			final double f = variables[5];
			final double g = variables[6];
			final double h = variables[7];
			final double i = variables[8];
			final double j = variables[9];
			final double k = variables[10];
			return FastMath.pow((a-1.5), 2)+FastMath.pow((b-2.5), 2)+FastMath.pow((c-3.5), 2)+FastMath.pow((d-4.5), 2)+FastMath.pow((e-5.5), 2)+FastMath.pow((f-6.5), 2)+FastMath.pow((g-7.5), 2)+FastMath.pow((h-8.5), 2)+FastMath.pow((i-9.5), 2)+FastMath.pow((j-10.5), 2)+FastMath.pow((k-12.5), 2);
		}
	}
}
