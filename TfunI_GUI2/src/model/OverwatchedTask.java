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
	public static final int STATUS_ANFRAGE_PROBLEM = 2;
	public static final int STATUS_ALLES_OK = 2;
	private int[] status;

	public OverwatchedTask(Target target, double verbesserungsKoeff, double[] startWert, double[] polySeiteLaenge,
			int[] status) {
		this.target = target;
		this.verbesserungsKoeff = verbesserungsKoeff;
		this.startWert = startWert;
		this.polySeiteLaenge = polySeiteLaenge;

		this.status = status;
		status[0] = STATUS_ALLES_OK;

	}

	ObjectiveFunction function = new ObjectiveFunction(target);

	@Override
	protected Object doInBackground() {
		SimplexOptimizer optimizer = new SimplexOptimizer(verbesserungsKoeff, verbesserungsKoeff);
		optimum = optimizer.optimize(new MaxEval(10000), function, GoalType.MINIMIZE, new InitialGuess(startWert),
				new NelderMeadSimplex(polySeiteLaenge));
		return new PointValuePair(new double[] { 0 }, 0); // nur für den
															// Compiler!
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
