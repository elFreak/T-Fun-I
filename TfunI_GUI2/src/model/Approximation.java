package model;

import java.util.Arrays;
import java.util.List;

import javax.swing.SwingWorker;


import org.apache.commons.math3.optim.InitialGuess;
import org.apache.commons.math3.optim.MaxEval;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
import org.apache.commons.math3.optim.nonlinear.scalar.ObjectiveFunction;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.NelderMeadSimplex;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.SimplexOptimizer;

import userInterface.StatusBar;

public class Approximation extends SwingWorker<Object, Integer> {

	MeasurementData measurementData;
	Model model;

	public double[][] stepAnswer;

	public Approximation(MeasurementData measurementData, Model model) {
		this.measurementData = measurementData;
		this.model = model;
	}

	public void berechne() {
		// matlabfunction.Filter filter =
		// matlabfunction.FilterFactory.createButter(3, 1.0);
		// double[] t = Matlab.linspace(0, 15.0, 100);
		// double[] step_soll = (double[])matlabfunction.SVTools.step(filter.B,
		// filter.A, t)[0];

		//Target target = new Target(scalingTime(measurementData.getMeanData())[0], measurementData.getMeanData()[1], 3);
		Target target = new Target(measurementData.getMeanData()[0],measurementData.getMeanData()[1],3);
		// for (int i = 0; i < step_soll.length; i++) {
		// System.out.println(""+step_soll[i]);
		// }

		// Target target = new Target(t, step_soll, 3);
		SimplexOptimizer optimizer = new SimplexOptimizer(1e-10, 1e-6);

		PointValuePair optimum = optimizer.optimize(new MaxEval(10000), new ObjectiveFunction(target),
				GoalType.MINIMIZE, new InitialGuess(getStartingValues()),
				new NelderMeadSimplex(new double[] { 0.2, 0.2, 0.2, 0.2 }));
		
		SimplexOptimizer optimizer2 = new SimplexOptimizer(1e-15, 1e-16);
		PointValuePair optimum2 = optimizer.optimize(new MaxEval(10000), new ObjectiveFunction(target),
				GoalType.MINIMIZE, new InitialGuess(optimum.getPoint()),
				new NelderMeadSimplex(new double[] { 0.2, 0.2, 0.2, 0.2 }));

		// double[] step_approx = target.omega2polstep(4, optimum.getPoint(),
		// measurementData.getMeanData()[0]);

		System.out.println(Arrays.toString(optimum.getPoint()) + " : " + optimum.getSecond());
		System.out.println(Arrays.toString(optimum2.getPoint()) + " : " + optimum2.getSecond());
		// double[] step_approx = target.omega2polstep(3, optimum.getPoint(),
		// t);
		// for (int i = 0; i < step_approx.length; i++) {
		// System.out.println("" + step_approx[i]);
		// }

		stepAnswer = new double[][] { measurementData.getMeanData()[0],
				target.omega2polstep(3, optimum2.getPoint(), measurementData.getMeanData()[0]) };
		// stepAnswer = new double[][]{t,step_approx};
		model.notifyObservers();
	}

	public void stopp() {
	}

	@Override
	public Object doInBackground() {
		int zaeler = 0;
		try {
			for (zaeler = 1; zaeler <= 10; zaeler++) {
				Thread.sleep(500);
				publish(zaeler);
			}

		} catch (InterruptedException e) {
		}

		return 0;
	}

	@Override
	protected void process(List<Integer> zaeler) {
		super.process(zaeler);
		StatusBar.showStatus("Zähler: " + zaeler.get(0));
	}

	@Override
	public void done() {
		super.done();
		StatusBar.showStatus("Fertig ");
	}

	private static double[][] scalingTime(double[][] step_response_m) {
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

	private static double[] getStartingValues() {
		double[] startingValues = new double[] { 10.0, 0.9, 1.0, 0.3 };
		// double[] startingValues = new double[] {1.0, rA[0].abs(),
		// (rA[0].abs()/(-2.0*rA[0].getReal())), rA[0].getReal() };
		return startingValues;
	}

	private static double[] removeOffset(double[] a) {
		double[] b = new double[a.length];
		for (int i = 0; i < b.length; i++) {
			b[i] = a[i] - a[0];
		}
		return b;
	}

	private static double[][] removeVorsprungzeit(double[] t, double[] x, double[] y) {
		
		int index = 2;
		while(x[index-1]<=(index-1)){
			index++;
		}
		double[] a = new double[t.length-index];
		for (int i = index-1; i < a.length; i++) {
			a[i]=t[i]-t[index];
		}
		
		return null;
	}
}
