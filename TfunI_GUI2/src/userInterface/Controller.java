package userInterface;


import model.Approximation;

public class Controller {
	// --------------------------------------------------------------------
	// General:
	private View view;

	// --------------------------------------------------------------------
	// Programm-Flow:
	// --------------------------------------------------------------------
	// ProgrammFlow:
	public final static int EINLESEN = 0;
	public final static int BEARBEITEN = 1;
	public final static int BERECHNEN = 2;
	public final static int VERTIFIZIEREN = 3;
	
	//Test$$$$$$$$$$$$$$$$$$$$$$$$$
	public Approximation approximation = new Approximation();

	public Controller() {

	}

	public void setView(View view) {
		this.view = view;
		view.inputPanel.setActualMode(EINLESEN);
	}

	public void setActualMode(int mode) {
		view.inputPanel.setActualMode(mode);
		
	}
}
