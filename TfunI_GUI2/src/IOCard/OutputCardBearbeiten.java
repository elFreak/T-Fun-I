package IOCard;

import JavaPlot.Plot;
import JavaPlot.Slider;
import userInterface.Controller;
import userInterface.OutputPanel;
import userInterface.WindowContainer;

public class OutputCardBearbeiten extends WindowContainer {
	private static final long serialVersionUID = 1L;
	
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
		plotBearbeiten.addSlider(Slider.HORIZONTAL,"Offset");
		plotBearbeiten.addSlider(Slider.VERTICAL,"Start");
		plotBearbeiten.addSlider(Slider.VERTICAL,"End");
	}
}
