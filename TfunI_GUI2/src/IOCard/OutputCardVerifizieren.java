package IOCard;

import JavaPlot.Plot;
import userInterface.OutputPanel;
import userInterface.WindowContainer;

public class OutputCardVerifizieren extends WindowContainer {
	private static final long serialVersionUID = 1L;
	
	public Plot plotVerifizieren = new Plot();
	
	public OutputCardVerifizieren(OutputPanel outputPanel) {
		addComponent(plotVerifizieren);

		plotVerifizieren.addTrace(outputPanel.traceMean);
		plotVerifizieren.addSubplot();
		plotVerifizieren.addTrace(outputPanel.traceSolution);
		outputPanel.traceMean.dataValid = false;
		outputPanel.traceSolution.dataValid = false;

	}

}
