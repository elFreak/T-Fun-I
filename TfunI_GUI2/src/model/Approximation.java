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
import speicher.StartValueSaver;
import userInterface.StatusBar;

public class Approximation extends SwingWorker<Object, Integer> {

	/**
	 * General Data:
	 */
	private MeasurementData measurementData;
	private Model model;
	private double[][] solutionSignal;

	/**
	 * Erstellt ein Objekt welches in der Lage ist eine Übertragungsfunktion zu
	 * berechnen. Die Berechnung wird in einem eigenen Task durchgeführt. Die
	 * Übertragungsfunktion wird so berechnet, dass sie möglichst genau mit den
	 * Messdaten übereitstimmt. Ein Objekt dieser Klasse kann nur eine
	 * Übertragungsfunktion von genau einer Ordnung (Polstellen-Ordnung)
	 * berechnen.
	 * 
	 * @param measurementData
	 * @param model
	 */
	public Approximation(MeasurementData measurementData, Model model) {
		this.measurementData = measurementData;
		this.model = model;
	}

	/**
	 * Eigentliche Berechnungsmethode.
	 */
	private void berechne() {

		// Die Zeitachse der Messwerte wird normiert um die Berechnung zu
		// vereinfachen.
		double[] time_normiert = scalingTime(measurementData.getFinalData())[0];

		// Die Übertragungsfunktion und die Zusammenhänge mit den Messwerten
		// werden definiert.
		Target target = new Target(time_normiert, measurementData.getFinalData()[1]);

		PointValuePair optimum = StableFMinSearch.fminsearch(target, 4);
		
		solutionSignal = new double[][] { measurementData.getFinalData()[0],
				Target.omega2polstep(optimum.getPoint(), time_normiert) };
	}

	@Override
	protected Object doInBackground() {
		berechne();
		return 0;
	}

	@Override
	protected void process(List<Integer> arg) {
		super.process(arg);
	}

	@Override
	protected void done() {
		super.done();
		StatusBar.showStatus("Approximation beendet.", StatusBar.INFO);
		model.notifyObservers(Model.NOTIFY_REASON_APPROXIMATION_DONE);
	}

	/**
	 * Normiert die Zeitachse des gegebenen Arrays.
	 * 
	 * @param step_response_m
	 * @return
	 */
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

	public double[][] getSolutionSignal() {
		return solutionSignal;
	}

	public void setSolutionSignal(double[][] solutionSignal) {
		this.solutionSignal = solutionSignal;
	}

}
