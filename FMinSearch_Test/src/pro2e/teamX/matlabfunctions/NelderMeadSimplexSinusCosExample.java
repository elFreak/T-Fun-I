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
		SimplexOptimizer optimizer = new SimplexOptimizer(0.1,-1);
		Target target = new Target();

		PointValuePair optimum = optimizer.optimize(new MaxEval(1000), new ObjectiveFunction(target), GoalType.MINIMIZE,
				new InitialGuess(new double[] { 1 }), new NelderMeadSimplex(new double[] { 0.5}));

		System.out.println(Arrays.toString(optimum.getPoint()) + " : " + optimum.getSecond());
	}

	private static class Target implements MultivariateFunction {
		public double value(double[] variables) {
			final double x = variables[0];
			//final double y = variables[1];
			return FastMath.pow((x-2), 2)+10;
		}
	}
}
