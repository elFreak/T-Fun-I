package IOCard;

import JavaPlot.Plot;
import userInterface.WindowContainer;

public class OutputCardEinlesen extends WindowContainer {
	private static final long serialVersionUID = 1L;
	
	private OutputPanel outputPanel;
	
	private Plot plotEinlesen = new Plot();
	private boolean plotEinlesenTraceRawAdded = false;
	private boolean plotEinlesenTraceStepAdded = false;
	
	public OutputCardEinlesen(OutputPanel outputPanel){
		this.outputPanel = outputPanel;
		addComponent(plotEinlesen);

		plotEinlesen.addTrace(outputPanel.traceStep);
		plotEinlesen.addTrace(outputPanel.traceRaw);
		outputPanel.traceStep.dataValid = false;
		outputPanel.traceRaw.dataValid = false;
	}

	public void update(java.util.Observable obs, Object obj) {
		plotEinlesen.setRangeIdeal();
		
		if(outputPanel.traceRaw.dataValid&&!plotEinlesenTraceRawAdded) {
			plotEinlesen.addTrace(outputPanel.traceRaw);
		}
		
		if(outputPanel.traceRaw.dataValid&&!plotEinlesenTraceStepAdded) {
			plotEinlesen.addTrace(outputPanel.traceStep);
		}
	}
}
