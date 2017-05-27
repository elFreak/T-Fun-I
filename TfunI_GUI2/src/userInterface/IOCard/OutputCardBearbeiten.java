package userInterface.IOCard;

import model.Model;
import userInterface.Controller;
import userInterface.WindowContainer;
import userInterface.JavaPlot.Plot;
import userInterface.JavaPlot.Slider;

public class OutputCardBearbeiten extends WindowContainer {
	private static final long serialVersionUID = 1L;

	OutputPanel outputPanel;

	public final static String KEY_START = "Start";
	public final static String KEY_END = "End";
	public final static String KEY_OFFSET = "Offset";

	private Plot plotBearbeiten;
	private boolean plotBearbeitenTraceStepAdded = false;
	private boolean plotBearbeitenTraceRawAdded = false;
	private boolean plotBearbeitenTracePreprocessedAdded = false;
	private boolean plotBearbeitenTraceMeanAdded = false;

	public OutputCardBearbeiten(OutputPanel outputPanel, Controller controller) {
		this.outputPanel = outputPanel;
		plotBearbeiten = new Plot(controller);
		addComponent(plotBearbeiten);

		plotBearbeiten.addSubplot();

		plotBearbeiten.setSubplot(0);
		plotBearbeiten.addSlider(Slider.HORIZONTAL, KEY_OFFSET);
		plotBearbeiten.addSlider(Slider.VERTICAL, KEY_START);
		plotBearbeiten.addSlider(Slider.VERTICAL, KEY_END);
		plotBearbeiten.setSliderPosition(KEY_OFFSET, 0);
		plotBearbeiten.setSliderPosition(KEY_START, -1000);
		plotBearbeiten.setSliderPosition(KEY_END, 1000);
	}

	public void update(java.util.Observable obs, Object obj) {

		plotBearbeiten.setSubplot(0);
		plotBearbeiten.setSliderPosition(KEY_OFFSET, ((Model) obs).measurementData.getOffset());
		plotBearbeiten.setSliderPosition(KEY_START, ((Model) obs).measurementData.getstepTime());
		plotBearbeiten.setSliderPosition(KEY_END, ((Model) obs).measurementData.getTail());

		plotBearbeiten.setSubplot(1);
		plotBearbeiten.setRangeIdeal();

		if (outputPanel.traceRaw.dataValid && !plotBearbeitenTraceStepAdded) {
			plotBearbeiten.setSubplot(0);
			plotBearbeitenTraceStepAdded = true;
			plotBearbeiten.addTrace(outputPanel.traceStep);
			plotBearbeiten.setRangeIdeal();
		}
		if (outputPanel.traceRaw.dataValid && !plotBearbeitenTraceRawAdded) {
			plotBearbeiten.setSubplot(0);
			plotBearbeitenTraceRawAdded = true;
			plotBearbeiten.addTrace(outputPanel.traceRaw);
			plotBearbeiten.setRangeIdeal();
		}
		if (outputPanel.traceRaw.dataValid && !plotBearbeitenTracePreprocessedAdded) {
			plotBearbeiten.setSubplot(1);
			plotBearbeitenTracePreprocessedAdded = true;
			plotBearbeiten.addTrace(outputPanel.tracePreprocessed);
			plotBearbeiten.setRangeIdeal();
		}
		if (outputPanel.traceMean.dataValid && !plotBearbeitenTraceMeanAdded) {
			plotBearbeiten.setSubplot(0);
			plotBearbeitenTraceMeanAdded = true;
			plotBearbeiten.addTrace(outputPanel.traceMean);
			plotBearbeiten.setRangeIdeal();
		}

		if (((int) obj) == Model.NOTIFY_REASON_NEW_DATA) {
			plotBearbeiten.setSubplot(0);
			plotBearbeiten.setRangeIdeal();
		}
	}
}
