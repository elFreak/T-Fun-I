package IOCard;

import JavaPlot.Plot;
import JavaPlot.Slider;
import model.Model;
import userInterface.Controller;
import userInterface.WindowContainer;

public class OutputCardBearbeiten extends WindowContainer {
	private static final long serialVersionUID = 1L;
	
	public final static String KEY_START = "Start";
	public final static String KEY_END = "End";
	public final static String KEY_OFFSET = "Offset";
	
	public Plot plotBearbeiten;
	
	public OutputCardBearbeiten(OutputPanel outputPanel, Controller controller) {
		plotBearbeiten = new Plot(controller);
		addComponent(plotBearbeiten);

		plotBearbeiten.addTrace(outputPanel.traceRaw);
		plotBearbeiten.addTrace(outputPanel.traceStep);
		plotBearbeiten.addSubplot();
		plotBearbeiten.addTrace(outputPanel.tracePreprocessed);
		outputPanel.traceStep.dataValid = false;
		outputPanel.traceRaw.dataValid = false;
		outputPanel.tracePreprocessed.dataValid = false;
		plotBearbeiten.setSubplot(0);
		plotBearbeiten.addSlider(Slider.HORIZONTAL,KEY_OFFSET);
		plotBearbeiten.addSlider(Slider.VERTICAL,KEY_START);
		plotBearbeiten.addSlider(Slider.VERTICAL,KEY_END);
		plotBearbeiten.setSliderPosition(KEY_OFFSET, 0);
		plotBearbeiten.setSliderPosition(KEY_START, -1000);
		plotBearbeiten.setSliderPosition(KEY_END, 1000);
	}
	
	
	public void update(java.util.Observable obs, Object obj) {
		plotBearbeiten.setSliderPosition(KEY_OFFSET, ((Model) obs).measurementData.getOffset());
		plotBearbeiten.setSliderPosition(KEY_START, ((Model) obs).measurementData.getDeadTime());
		plotBearbeiten.setSliderPosition(KEY_END, ((Model) obs).measurementData.getTail());
	}
}
