package userInterface;

import java.util.Arrays;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.optim.InitialGuess;
import org.apache.commons.math3.optim.MaxEval;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
import org.apache.commons.math3.optim.nonlinear.scalar.ObjectiveFunction;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.NelderMeadSimplex;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.SimplexOptimizer;

import JavaPlot.Plot;
import JavaPlot.Trace;
import matlabfunction.Filter;
import matlabfunction.FilterFactory;
import matlabfunction.Matlab;
import matlabfunction.SVTools;
import model.Model;
import model.Target;

/**
 * 
 * @author Team 1
 *
 */
public class Controller {

	// --------------------------------------------------------------------
	// General:
	private View view;
	private Model model;

	// --------------------------------------------------------------------
	// Programm-Flow:
	// --------------------------------------------------------------------
	// ProgrammFlow:
	public final static int EINLESEN = 0;
	public final static String KEY_EINLESEN = "EINLESEN";
	public final static int BEARBEITEN = 1;
	public final static String KEY_BEARBEITEN = "BEARBEITEN";
	public final static int BERECHNEN = 2;
	public final static String KEY_BERECHNEN = "BERECHNEN";
	public final static int VERIFIZIEREN = 3;
	public final static String KEY_VERIFIZIEREN = "VERTIFIZIEREN";

	public Controller(Model model) {
		this.model = model;
	}

	/**
	 * 
	 * @param view
	 */
	public void setView(View view) {
		this.view = view;
		view.inputPanel.setActualMode(EINLESEN);
		view.outputPanel.setActualMode(EINLESEN);
	}

	/**
	 * 
	 * @param mode
	 */
	public void setActualMode(int mode) {
		view.inputPanel.setActualMode(mode);
		view.outputPanel.setActualMode(mode);

		switch (mode) {
		case Controller.EINLESEN:

			break;

		case Controller.BEARBEITEN:

			break;
		case Controller.BERECHNEN:

			break;
		case Controller.VERIFIZIEREN:
//			Filter filter = FilterFactory.createButter(3, 2.0);
//			double[] t = Matlab.linspace(0, 15.0, 1000);
//			
//			double[] step_soll = (double[])SVTools.step(filter.B, filter.A, t)[0];
//			
//			double [][] soll_plot = new double[][]{t,step_soll};
//			
//			
//			Plot plot = new Plot();
//			Trace trace = new Trace();
//			trace.data=soll_plot;
//			view.outputPanel.cardVerifizieren.plot.addTrace(trace);
//
//			
//			for (int i = 0; i < step_soll.length; i++) {
//				System.out.println(""+step_soll[i]);
//			}
//			
//			Target target = new Target(t, step_soll, 3);
//			
//			SimplexOptimizer optimizer = new SimplexOptimizer(1e-3, 1e-2);
//
//			Complex[] rA = Matlab.roots(filter.A);
//			
//			double[] startwerte = new double[] {1.0, rA[0].abs(), (rA[0].abs()/(-2.0*rA[0].getReal())), rA[0].getReal() };
//
//			PointValuePair optimum = optimizer.optimize(new MaxEval(10000), new ObjectiveFunction(target), GoalType.MINIMIZE,
//					new InitialGuess(startwerte), new NelderMeadSimplex(new double[] { 0.02, 0.02, 0.02, 0.02 }));
//			
//			System.out.println(Arrays.toString(optimum.getPoint()) + " : " + optimum.getSecond());
//			System.out.println(""+rA[0].abs());
//			System.out.println(""+rA[0].abs()/(-2.0*rA[0].getReal()));
//			System.out.println(""+rA[0].getReal());
//			
//			double[] step_approx = target.omega2polstep(3, optimum.getPoint(), t);
//			for (int i = 0; i < step_approx.length; i++) {
//				System.out.println(""+step_approx[i]);
//			}
//			
//			double [][] approx_plot = new double[][]{t,step_approx};
//			
//			Trace traceapprox = new Trace();
//			traceapprox.data=approx_plot;
//			view.outputPanel.cardVerifizieren.plot.addTrace(traceapprox);
			model.approximation.berechne();
			
			break;
		}
	}

	public void setMesuredData(double[][] data) {
		model.setMesuredData(data);
	}
	
	public void filterChanged(int n) {
		model.measurementData.setMovingMean(n);
	}
	
	public void setRange(double start, double end, double offset) {
		model.measurementData.setLimits(start, offset, end);
	}
}
