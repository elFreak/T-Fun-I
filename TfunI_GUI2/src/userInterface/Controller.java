package userInterface;

import model.Model;
import sun.awt.RepaintArea;

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
	}

	public void setMesuredData(double[][] data) {
		model.setMesuredData(data);
		model.measurementData.autoLimits();
	}

	public void filterChanged(int n) {
		model.measurementData.setMovingMean(n);
	}

	public void setRange(double deadTime, double tail, double offset) {
		if (tail < (deadTime + model.measurementData.getstepTime())) {
			if (tail != model.measurementData.getTail()) {
				tail = (deadTime + model.measurementData.getstepTime());
			}
			if (deadTime != model.measurementData.getDeadTime()) {
				deadTime = model.measurementData.getTail() - model.measurementData.getstepTime();
			}
		}

		model.measurementData.setLimits(deadTime, offset, tail);
	}

	public void setStep(double stepTime, double stepHeight) {
		if (stepTime > model.measurementData.getTail() - model.measurementData.getDeadTime()) {
			stepTime = model.measurementData.getTail() - model.measurementData.getDeadTime();
		}

		model.measurementData.setStepHeight(stepHeight);
		model.measurementData.setStepTime(stepTime);
	}

	public void setOriginalStep() {
		model.measurementData.setOriginalStep();
	}
	
	public void calculateUTF() {
		model.berechneUTF();
	}
	
	public void autoLimmits(){
		model.measurementData.autoLimits();
	}
	
	public void cBBerechnenChanged(boolean[] value) {
		for(int i=0;i<value.length;i++) {
			view.outputPanel.tracesSolution[i].dataValid = value[i];
			view.outputPanel.tracesPole[i].dataValid = value[i];
		}
		view.repaint();
	}
}
