package IOCard;

import JavaPlot.Plot;
import JavaPlot.Slider;
import userInterface.Controller;
import userInterface.WindowContainer;

public class OutputCardBearbeiten extends WindowContainer {
	private static final long serialVersionUID = 1L;
	
	public final static String KEY_START = "Start";
	public final static String KEY_END = "End";
	public final static String KEY_OFFSET = "Offset";
	
	private Controller controller;
	public Plot plotBearbeiten;
	
	public OutputCardBearbeiten(OutputPanel outputPanel, Controller controller) {
		plotBearbeiten = new Plot(controller);
		addComponent(plotBearbeiten);

		plotBearbeiten.addTrace(outputPanel.traceRaw);
		plotBearbeiten.addTrace(outputPanel.traceStep);
		plotBearbeiten.addSubplot();
		plotBearbeiten.addTrace(outputPanel.traceRaw);
		plotBearbeiten.addTrace(outputPanel.traceMean);
		outputPanel.traceStep.dataValid = false;
		outputPanel.traceRaw.dataValid = false;
		outputPanel.traceMean.dataValid = false;
		plotBearbeiten.setSubplot(0);
		plotBearbeiten.addSlider(Slider.HORIZONTAL,KEY_OFFSET);
		plotBearbeiten.addSlider(Slider.VERTICAL,KEY_START);
		plotBearbeiten.addSlider(Slider.VERTICAL,KEY_END);
		plotBearbeiten.setSliderPosition(KEY_OFFSET, 0);
		plotBearbeiten.setSliderPosition(KEY_START, -1000);
		plotBearbeiten.setSliderPosition(KEY_END, 1000);
		plotBearbeiten.setSubplot(1);
		plotBearbeiten.addSlider(Slider.HORIZONTAL,KEY_OFFSET);
		plotBearbeiten.setSliderPosition(KEY_OFFSET, 0);
	}
}
