package IOCard;

import JavaPlot.Plot;
import userInterface.OutputPanel;
import userInterface.WindowContainer;

public class OutputCardVerifizieren extends WindowContainer {
	private static final long serialVersionUID = 1L;
	
	public Plot plotVerifizieren = new Plot();
	
	public OutputCardVerifizieren(OutputPanel outputPanel) {
		addComponent(plotVerifizieren);

		plotVerifizieren.addTrace(outputPanel.traceRaw);
		plotVerifizieren.addTrace(outputPanel.traceStep);
		plotVerifizieren.addSubplot();
		plotVerifizieren.addTrace(outputPanel.traceRaw);
		plotVerifizieren.addTrace(outputPanel.traceMean);
		outputPanel.traceStep.dataValid = false;
		outputPanel.traceRaw.dataValid = false;
		outputPanel.traceMean.dataValid = false;
		

	}

}
