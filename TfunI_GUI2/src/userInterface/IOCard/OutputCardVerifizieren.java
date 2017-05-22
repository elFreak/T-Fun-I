package userInterface.IOCard;

import userInterface.WindowContainer;
import userInterface.JavaPlot.Plot;

public class OutputCardVerifizieren extends WindowContainer {
	private static final long serialVersionUID = 1L;
	
	private Plot plotVerifizieren = new Plot();
	
	public OutputCardVerifizieren(OutputPanel outputPanel) {
		addComponent(plotVerifizieren);

		plotVerifizieren.addTrace(outputPanel.tracePreprocessed);
		plotVerifizieren.addSubplot();
		plotVerifizieren.addTrace(outputPanel.traceSolution);
		outputPanel.tracePreprocessed.dataValid = false;
		outputPanel.traceSolution.dataValid = false;

	}

}
