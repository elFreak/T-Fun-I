package pro2e.teamX.matlabfunctions;

import java.applet.Applet;
import java.util.Arrays;

import javax.swing.JApplet;
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

import JavaPlot.Plot;
import JavaPlot.Trace;
import model.Target;

//import oli_test.Approximation;
//import oli_test.ApproximationSettings;

public class NelderMeadSimplexSinusCosExample extends JApplet {

	public static void main(String[] args) {
		Filter filter = FilterFactory.createButter(3, 1.0);
		double[] t = Matlab.linspace(0, 15.0, 100);
		
		double[] step_soll = (double[])SVTools.step(filter.B, filter.A, t)[0];
		
		double [][] soll_plot = new double[][]{t,step_soll};
		
		
		Plot plot = new Plot();
		
		Trace trace = new Trace();
		trace.data=soll_plot;
		plot.addTrace(trace);
		
		
		
		for (int i = 0; i < step_soll.length; i++) {
			System.out.println(""+step_soll[i]);
		}
		
		Target target = new Target(t, step_soll, 3);
		
		SimplexOptimizer optimizer = new SimplexOptimizer(1e-10, 1e-6);

		Complex[] rA = Matlab.roots(filter.A);
		
		double[] startwerte = new double[] {1.0, rA[0].abs(), (rA[0].abs()/(-2.0*rA[0].getReal())), rA[0].getReal()  };
		//double[] startwerte = new double[] {1.0, 1.0, -1.0, 1.0 ,1.0,1.0 ,1.0};
		
		//double[] s = target.omega2polstep(3, new double[] {1.0, rA[0].abs(), (rA[0].abs()/(-2.0*rA[0].getReal())), rA[0].getReal()}, t);
		
		//		//FourExtrema fourExtrema = new FourExtrema();
//
		PointValuePair optimum = optimizer.optimize(new MaxEval(1000), new ObjectiveFunction(target), GoalType.MINIMIZE,
				new InitialGuess(startwerte), new NelderMeadSimplex(new double[] { 0.2, 0.2, 0.2, 0.2 }));
//
		System.out.println(Arrays.toString(optimum.getPoint()) + " : " + optimum.getSecond());
		System.out.println(""+rA[0].abs());
		System.out.println(""+rA[0].abs()/(-2.0*rA[0].getReal()));
		System.out.println(""+rA[0].getReal());
		
		double[] step_approx = target.omega2polstep(3, optimum.getPoint(), t);
		for (int i = 0; i < step_approx.length; i++) {
			System.out.println(""+step_approx[i]);
		}
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
