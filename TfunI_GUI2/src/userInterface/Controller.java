package userInterface;

import model.Model;

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
