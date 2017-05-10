package model;

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
		this.model=model;
	}

	public void berechne() {
		Target target = new Target(scalingTime(measurementData.getMeanData())[0], measurementData.getMeanData()[1], 3);
		SimplexOptimizer optimizer = new SimplexOptimizer(1e-10, 1e-6);
		
		PointValuePair optimum = optimizer.optimize(new MaxEval(10000), new ObjectiveFunction(target), GoalType.MINIMIZE,
				new InitialGuess(getStartingValues()), new NelderMeadSimplex(new double[] { 0.2, 0.2, 0.2, 0.2 }));
		
		double[] step_approx = target.omega2polstep(3, optimum.getPoint(), measurementData.getMeanData()[0]);
		
		stepAnswer = new double[][]{measurementData.getMeanData()[0],target.omega2polstep(3, optimum.getPoint(), measurementData.getMeanData()[0])};
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
		double[] startingValues = new double[] { 1.0, 1.0, 1.0, 1.0 };
		//double[] startingValues = new double[] {1.0, rA[0].abs(), (rA[0].abs()/(-2.0*rA[0].getReal())), rA[0].getReal()  };
		return startingValues;
	}
}
