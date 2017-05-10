package IOCard;

import JavaPlot.Plot;
import JavaPlot.Slider;
import userInterface.OutputPanel;
import userInterface.WindowContainer;

public class OutputCardEinlesen extends WindowContainer {
	private static final long serialVersionUID = 1L;
	
	public Plot plotEinlesen = new Plot();
	
	public OutputCardEinlesen(OutputPanel outputPanel){
		addComponent(plotEinlesen);

		plotEinlesen.addTrace(outputPanel.traceStep);
		plotEinlesen.addTrace(outputPanel.traceRaw);
		outputPanel.traceStep.dataValid = false;
		outputPanel.traceRaw.dataValid = false;
	}

}
