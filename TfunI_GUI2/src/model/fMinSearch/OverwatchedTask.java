package model.fMinSearch;

import javax.swing.SwingWorker;
import org.apache.commons.math3.optim.InitialGuess;
import org.apache.commons.math3.optim.MaxEval;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
import org.apache.commons.math3.optim.nonlinear.scalar.ObjectiveFunction;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.NelderMeadSimplex;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.SimplexOptimizer;

import model.Target;

/**
 * 
 * Klasse OverwatchesTask: Ermöglicht die Überwachung der Berechnung.
 * 
 * @author Team 1
 *
 */
public class OverwatchedTask extends SwingWorker<Object, double[]> {

	/**
	 * Eigenschaften:
	 */
	private double verbesserungsKoeff;
	private double[] startWert;
	private double[] polySeiteLaenge;
	private Target target;
	private PointValuePair optimum;

	/**
	 * Überwachungsvariablen
	 */
	public static final int STATUS_FERTIG = 1;
	public static final int STATUS_IN_ARBEIT = 2;
	public static final int STATUS_PROBLEM_ABFRAGEN = 3;
	private int[] status;

	// -----------------------------------------------------------------------------------------------------------------
	// Konstrucktor:
	// -----------------------------------------------------------------------------------------------------------------
	/**
	 * Erstellt den Überwachbaren Task.
	 * 
	 * @param target
	 * @param verbesserungsKoeff
	 * @param startWert
	 * @param polySeiteLaenge
	 * @param status
	 */
	public OverwatchedTask(Target target, double verbesserungsKoeff, double[] startWert, double[] polySeiteLaenge,
			int[] status) {
		this.target = target;
		this.verbesserungsKoeff = verbesserungsKoeff;
		this.startWert = startWert;
		this.polySeiteLaenge = polySeiteLaenge;

		this.status = status;
		status[0] = STATUS_IN_ARBEIT;

		optimum = new PointValuePair(startWert, 0);

	}

	// -----------------------------------------------------------------------------------------------------------------
	// Berechnungs Methoden:
	// -----------------------------------------------------------------------------------------------------------------
	/**
	 * Definiert, was im eigenen Thread gemacht werden soll.
	 * 
	 */
	@Override
	protected Object doInBackground() throws Exception {
		SimplexOptimizer optimizer = new SimplexOptimizer(verbesserungsKoeff, verbesserungsKoeff);

		optimum = optimizer.optimize(new MaxEval(10000), new ObjectiveFunction(target), GoalType.MINIMIZE,
				new InitialGuess(startWert), new NelderMeadSimplex(polySeiteLaenge));

		return new PointValuePair(new double[] { 0 }, 0); // nur für den
															// Compiler!
	}

	/**
	 * Sobald der Thread beendet ist, wird der Status entsprechend gesetzt.
	 */
	@Override
	protected void done() {
		super.done();
		status[0] = STATUS_FERTIG;
	}


	// -----------------------------------------------------------------------------------------------------------------
	// Get-Methoden:
	// -----------------------------------------------------------------------------------------------------------------
	public PointValuePair getOptimum() {
		return optimum;
	}

}
