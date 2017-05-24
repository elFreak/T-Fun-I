package model.fMinSearch;

import java.util.List;

import javax.swing.SwingWorker;
import org.apache.commons.math3.optim.InitialGuess;
import org.apache.commons.math3.optim.MaxEval;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
import org.apache.commons.math3.optim.nonlinear.scalar.ObjectiveFunction;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.NelderMeadSimplex;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.SimplexOptimizer;

import model.Target;

public class OverwatchedTask extends SwingWorker<Object, double[]> {

	private double verbesserungsKoeff;
	private double[] startWert;
	private double[] polySeiteLaenge;
	private Target target;

	private PointValuePair optimum;
	public static final int STATUS_FERTIG = 1;
	public static final int STATUS_IN_ARBEIT = 2;
	public static final int STATUS_PROBLEM_ABFRAGEN = 3;

	private SwingWorkerClient client;

	private int[] status;

	public OverwatchedTask(Target target, double verbesserungsKoeff, double[] startWert, double[] polySeiteLaenge,
			int[] status, SwingWorkerClient client) {
		this.target = target;
		this.verbesserungsKoeff = verbesserungsKoeff;
		this.startWert = startWert;
		this.polySeiteLaenge = polySeiteLaenge;
		this.client = client;

		this.status = status;
		status[0] = STATUS_IN_ARBEIT;

		optimum = new PointValuePair(startWert, 0);

	}

	@Override
	protected Object doInBackground() {
		SimplexOptimizer optimizer = new SimplexOptimizer(verbesserungsKoeff, verbesserungsKoeff);
		optimum = optimizer.optimize(new MaxEval(10000), new ObjectiveFunction(target), GoalType.MINIMIZE,
				new InitialGuess(startWert), new NelderMeadSimplex(polySeiteLaenge));

		if (isCancelled() == false) {
			publish(optimum.getPoint());
		}

		return new PointValuePair(new double[] { 0 }, 0); // nur für den
															// Compiler!
	}

	@Override
	protected void process(List<double[]> chunks) {
		status[0] = STATUS_IN_ARBEIT;
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
