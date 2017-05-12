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

public class OverwatchedTask extends SwingWorker<Object, Integer> {

	private double verbesserungsKoeff;
	private double[] startWert;
	private double[] polySeiteLaenge;
	private Target target;

	private PointValuePair optimum;
	public static final int STATUS_FERTIG = 1;
	public static final int STATUS_IN_ARBEIT = 2;
	public static final int STATUS_PROBLEM_ABFRAGEN = 3;

	private int[] status;

	public OverwatchedTask(Target target, double verbesserungsKoeff, double[] startWert, double[] polySeiteLaenge,
			int[] status) {
		this.target = target;
		this.verbesserungsKoeff = verbesserungsKoeff;
		this.startWert = startWert;
		this.polySeiteLaenge = polySeiteLaenge;

		this.status = status;
		status[0] = STATUS_IN_ARBEIT;

	}

	@Override
	protected Object doInBackground() {
		publish(1);
		SimplexOptimizer optimizer = new SimplexOptimizer(verbesserungsKoeff, verbesserungsKoeff);
		optimum = optimizer.optimize(new MaxEval(10000), new ObjectiveFunction(target), GoalType.MINIMIZE,
				new InitialGuess(startWert), new NelderMeadSimplex(polySeiteLaenge));
		publish(2);
		optimum = optimizer.optimize(new MaxEval(10000), new ObjectiveFunction(target), GoalType.MINIMIZE,
				new InitialGuess(optimum.getPoint()), new NelderMeadSimplex(polySeiteLaenge));
		publish(3);
		optimum = optimizer.optimize(new MaxEval(10000), new ObjectiveFunction(target), GoalType.MINIMIZE,
				new InitialGuess(optimum.getPoint()), new NelderMeadSimplex(polySeiteLaenge));
		publish(4);
		optimum = optimizer.optimize(new MaxEval(10000), new ObjectiveFunction(target), GoalType.MINIMIZE,
				new InitialGuess(optimum.getPoint()), new NelderMeadSimplex(polySeiteLaenge));
		publish(5);
		return new PointValuePair(new double[] { 0 }, 0); // nur für den
															// Compiler!
	}

	@Override
	protected void process(List<Integer> chunks) {
		System.out.println("tap"+ chunks);
		status[0]=STATUS_IN_ARBEIT;
	}

	@Override
	protected void done() {
		super.done();
		status[0] = STATUS_FERTIG;
	}

	public PointValuePair getOptimum() {
		return optimum;
	}
}
