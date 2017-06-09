package userInterface.IOCard;

import java.util.Observer;

import model.Model;
import userInterface.WindowContainer;
import userInterface.JavaPlot.Plot;

/**
 * Oberfläche für Ausgaben im Zustand Berechnen.
 * 
 * @author Team 1
 *
 */
public class OutputCardBerechnen extends WindowContainer {
	private static final long serialVersionUID = 1L;

	private Plot plotBerechnen = new Plot();

	private Plot plotPolstellen = new Plot();

	private Plot plotCompare = new Plot();

	/**
	 * Erzeugt und initialisiert das Objekt.
	 * 
	 * @param controller
	 */
	public OutputCardBerechnen(OutputPanel outputPanel) {

		addComponent(plotBerechnen);
		addComponent(plotPolstellen);
		addComponent(plotCompare);

		plotCompare.setSubplot(0);
		plotCompare.addTrace(outputPanel.traceKorKoeffCompare);
		plotBerechnen.setSubplot(0);
		plotBerechnen.addTrace(outputPanel.tracePreprocessed);

		plotCompare.setAxisLabel(Plot.XAXIS, "Polstellenordnung", "", "");
		plotCompare.setAxisLabel(Plot.Y1AXIS, "Abweichung", "", "  in dBm");
		plotBerechnen.setAxisLabel(Plot.XAXIS, "t", "", " in s");
		plotBerechnen.setAxisLabel(Plot.Y1AXIS, "U", "", "  in V");
		plotPolstellen.setAxisLabel(Plot.XAXIS, "Real(s)", "", "");
		plotPolstellen.setAxisLabel(Plot.Y1AXIS, "Imag(s)", "", "");

		for (int i = 0; i < outputPanel.tracesSolution.length; i++) {
			plotBerechnen.setSubplot(0);
			plotBerechnen.addTrace(outputPanel.tracesSolution[i]);

			plotPolstellen.setSubplot(0);
			plotPolstellen.addTrace(outputPanel.tracesPole[i]);

			plotCompare.setSubplot(0);
			plotCompare.addTrace(outputPanel.traceKorKoeffPoints[i]);
		}

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

		if (reason == Model.NOTIFY_REASON_APPROXIMATION_UPDATE) {
			plotCompare.setRangeIdeal();

			plotPolstellen.setRangeIdeal();
			plotBerechnen.setRangeIdeal();
		}

	}

	/**
	 * Skaliert alle Plots automatisch.
	 */
	public void setAllRangeIdeal() {
		plotBerechnen.setRangeIdeal();
		plotCompare.setRangeIdeal();
		plotPolstellen.setRangeIdeal();
	}
}
