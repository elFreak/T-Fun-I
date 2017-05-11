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

import IOCard.OutputCardBearbeiten;
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
	public final static String KEY_VERIFIZIEREN = "VERIFIZIEREN";

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
	
	public void setRange(double deadTime, double tail, double offset) {
		model.measurementData.setLimits(deadTime, offset, tail);
	}
	
	public void setStep(double stepTime, double stepHeight){
		model.measurementData.setStepHeight(stepHeight);
		model.measurementData.setStepTime(stepTime);
	}
}
