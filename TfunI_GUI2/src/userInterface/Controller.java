package userInterface;

/**
 * 
 * @author Team 1
 *
 */
public class Controller {

	// --------------------------------------------------------------------
	// General:
	private View view;

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
	public final static int VERTIFIZIEREN = 3;
	public final static String KEY_VERTIFIZIEREN = "VERTIFIZIEREN";

	public Controller() {

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
		case Controller.VERTIFIZIEREN:

			break;
		}
	}

	public void setMesuredData(double[][] data) {
		
	}
}
