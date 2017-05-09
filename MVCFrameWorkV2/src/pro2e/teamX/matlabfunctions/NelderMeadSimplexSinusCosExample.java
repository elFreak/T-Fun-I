package pro2e.teamX.matlabfunctions;

import java.util.Arrays;

import javax.xml.bind.ParseConversionEvent;

import org.apache.commons.math3.analysis.MultivariateFunction;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.optim.InitialGuess;
import org.apache.commons.math3.optim.MaxEval;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
import org.apache.commons.math3.optim.nonlinear.scalar.ObjectiveFunction;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.NelderMeadSimplex;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.SimplexOptimizer;
import org.apache.commons.math3.util.FastMath;

//import oli_test.Approximation;
//import oli_test.ApproximationSettings;

public class NelderMeadSimplexSinusCosExample {

	public static void main(String[] args) {
		Filter filter = FilterFactory.createButter(3, 1.0);
		double[] t = Matlab.linspace(0, 15.0, 100);
		
		double[] step_soll = (double[])SVTools.step(filter.B, filter.A, t)[0];
		
		for (int i = 0; i < step_soll.length; i++) {
			System.out.println(""+step_soll[i]);
		}
		
		Target target = new Target(t, step_soll, 3);
		
		SimplexOptimizer optimizer = new SimplexOptimizer(1e-4, 1e-6);

		Complex[] rA = Matlab.roots(filter.A);
		
		double[] s = target.omega2polstep(3, new double[] {1.0, rA[0].abs(), (rA[0].abs()/(-2.0*rA[0].getReal())), rA[0].getReal()}, t);
		
		//		//FourExtrema fourExtrema = new FourExtrema();
//
		PointValuePair optimum = optimizer.optimize(new MaxEval(1000), new ObjectiveFunction(target), GoalType.MINIMIZE,
				new InitialGuess(new double[] {1.0, rA[0].abs(), (rA[0].abs()/(-2.0*rA[0].getReal())), rA[0].getReal()  }), new NelderMeadSimplex(new double[] { 0.2, 0.2, 0.2, 0.2 }));
//
		System.out.println(Arrays.toString(optimum.getPoint()) + " : " + optimum.getSecond());
		System.out.println(""+rA[0].abs());
		System.out.println(""+rA[0].abs()/(-2.0*rA[0].getReal()));
		System.out.println(""+rA[0].getReal());
	}

	//	private static class Target implements MultivariateFunction {
	//		public double value(double[] variables) {
	//			final double x = variables[0];
	//			final double y = variables[1];
	//			return FastMath.cos(x) * FastMath.sin(y);
	//			//return x+y;
	//		} 
	//	}
}

class Target implements MultivariateFunction {

	private double[] t;
	private double[] step_soll;
	private int ord;

	//Approximation approximation;
	//ApproximationSettings approximationsettings = new ApproximationSettings();

	public Target(double[] t, double[] step_soll, int ord) {
		this.t = t;
		this.step_soll = step_soll;
		this.ord = ord;
	}

	public double value(double[] variables) {
		final double x0 = variables[0];
		final double x1 = variables[1];
		final double x2 = variables[2];
		final double x3 = variables[3];
		//final double x4 = variables[4];


		return failuresum(step_soll, omega2polstep(ord, variables, t));

	}

	//x0=[K wp1 qp1 wp2 qp2 wp3 qp3]        n=even
	// x0=[K wp1 qp1 wp2 qp2 wp3 qp3 sig]    n=odd

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
		return failuresum;
	}
}

//	private static class FourExtrema implements MultivariateFunction
//    {
//        // The following function has 4 local extrema.
//        final double xM = -3.841947088256863675365;
//        final double yM = -1.391745200270734924416;
//        final double xP = 0.2286682237349059125691;
//        final double yP = -yM;
//        final double valueXmYm = 0.2373295333134216789769; // Local maximum.
//        final double valueXmYp = -valueXmYm; // Local minimum.
//        final double valueXpYm = -0.7290400707055187115322; // Global minimum.
//        final double valueXpYp = -valueXpYm; // Global maximum.
//
//        public double value(double[] variables)
//        {
//            final double x = variables[0];
//            final double y = variables[1];
//            return (x == 0 || y == 0) ? 0 : FastMath.atan(x)
//                * FastMath.atan(x + 2) * FastMath.atan(y) * FastMath.atan(y)
//                / (x * y);
//        }
//    }
