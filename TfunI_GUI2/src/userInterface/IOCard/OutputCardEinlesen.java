package userInterface.IOCard;

import java.util.Observer;

import model.Model;
import userInterface.WindowContainer;
import userInterface.JavaPlot.Plot;

/**
 * Oberfläche für Ausgaben im Zustand Einlesen.
 * 
 * @author Team 1
 *
 */
public class OutputCardEinlesen extends WindowContainer {
	private static final long serialVersionUID = 1L;
	private Plot plotEinlesen = new Plot();

	/**
	 * Erzeugt und initialisiert das Objekt.
	 * 
	 * @param controller
	 */
	public OutputCardEinlesen(OutputPanel outputPanel) {
		
		addComponent(plotEinlesen);

		plotEinlesen.addTrace(outputPanel.traceStep);
		plotEinlesen.addTrace(outputPanel.traceRaw);
		plotEinlesen.setAxisLabel(Plot.XAXIS, "t", "", " in s");
		plotEinlesen.setAxisLabel(Plot.Y1AXIS, "U", "", "  in V");
	}

	/**
	 * Updated das Objekt. 
	 * @see Observer
	 * 
	 * @param obs
	 * @param obj
	 */
	public void update(java.util.Observable obs, Object obj) {
		int reason = (int) obj;
		if (reason == Model.NOTIFY_REASON_MEASUREMENT_CHANGED) {
			plotEinlesen.setRangeIdeal();
		}
	}

	/**
	 * Skaliert alle Plots automatisch.
	 */
	public void setAllRangeIdeal() {
		plotEinlesen.setRangeIdeal();
	}
}
