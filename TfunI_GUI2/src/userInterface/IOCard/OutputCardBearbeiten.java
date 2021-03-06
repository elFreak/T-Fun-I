package userInterface.IOCard;

import java.util.Observer;

import model.Model;
import userInterface.Controller;
import userInterface.WindowContainer;
import userInterface.JavaPlot.Plot;
import userInterface.JavaPlot.Slider;

/**
 * Oberfl�che f�r Ausgaben im Zustand Bearbeiten.
 * 
 * @author Team 1
 *
 */
public class OutputCardBearbeiten extends WindowContainer {
	private static final long serialVersionUID = 1L;

	public final static String KEY_START = "Start";
	public final static String KEY_END = "End";
	public final static String KEY_OFFSET = "Offset";

	private Plot plotBearbeiten;

	/**
	 * Erzeugt und initialisiert das Objekt.
	 * 
	 * @param controller
	 */
	public OutputCardBearbeiten(OutputPanel outputPanel, Controller controller) {
		plotBearbeiten = new Plot(controller);
		addComponent(plotBearbeiten);
		plotBearbeiten.addSubplot();

		plotBearbeiten.setSubplot(0);
		plotBearbeiten.addTrace(outputPanel.traceStep);
		plotBearbeiten.setSubplot(0);
		plotBearbeiten.addTrace(outputPanel.traceRaw);
		plotBearbeiten.setSubplot(1);
		plotBearbeiten.addTrace(outputPanel.tracePreprocessed);
		plotBearbeiten.setSubplot(0);
		plotBearbeiten.addTrace(outputPanel.traceMean);
		
		plotBearbeiten.setSubplot(0);
		plotBearbeiten.setAxisLabel(Plot.XAXIS, "t", "", " in s");
		plotBearbeiten.setAxisLabel(Plot.Y1AXIS, "U", "", "  in V");
		plotBearbeiten.setSubplot(1);
		plotBearbeiten.setAxisLabel(Plot.XAXIS, "t", "", " in s");
		plotBearbeiten.setAxisLabel(Plot.Y1AXIS, "U", "", "  in V");


		plotBearbeiten.setSubplot(0);
		plotBearbeiten.addSlider(Slider.HORIZONTAL, KEY_OFFSET);
		plotBearbeiten.addSlider(Slider.VERTICAL, KEY_START);
		plotBearbeiten.addSlider(Slider.VERTICAL, KEY_END);
		plotBearbeiten.setSliderPosition(KEY_OFFSET, 0);
		plotBearbeiten.setSliderPosition(KEY_START, -1000);
		plotBearbeiten.setSliderPosition(KEY_END, 1000);
	}

	/**
	 * Updated das Objekt. 
	 * @see Observer
	 * 
	 * @param obs
	 * @param obj
	 */
	public void update(java.util.Observable obs, Object obj) {

		plotBearbeiten.setSubplot(0);

		plotBearbeiten.setSliderPosition(KEY_START, ((Model) obs).measurementData.getstepTime());
		plotBearbeiten.setSliderPosition(KEY_END, ((Model) obs).measurementData.getTail());
		plotBearbeiten.setSliderPosition(KEY_OFFSET, ((Model) obs).measurementData.getOffset());

		plotBearbeiten.setSubplot(1);
		plotBearbeiten.setRangeIdeal();
	}

	/**
	 * Skaliert alle Plots automatisch.
	 */
	public void setAllRangeIdeal() {
		plotBearbeiten.setSubplot(0);
		plotBearbeiten.setRangeIdeal();
		plotBearbeiten.setSubplot(1);
		plotBearbeiten.setRangeIdeal();
	}
}
