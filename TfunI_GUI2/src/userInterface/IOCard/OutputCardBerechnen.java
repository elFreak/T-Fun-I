package userInterface.IOCard;

import userInterface.WindowContainer;
import userInterface.JavaPlot.Plot;

public class OutputCardBerechnen extends WindowContainer {
	private static final long serialVersionUID = 1L;

	private OutputPanel outputPanel;

	private Plot plotBerechnen = new Plot();
	private boolean plotBerechnenTracePreprocessedAdded = false;
	private boolean[] plotBerechnenTraceSolutionAdded = new boolean[] { false, false, false, false, false, false, false,
			false, false };

	private Plot plotPolstellen = new Plot();
	private boolean[] plotPolstellenTracePoleAdded = new boolean[] { false, false, false, false, false, false, false,
			false, false };

	public OutputCardBerechnen(OutputPanel outputPanel) {
		this.outputPanel = outputPanel;

		addComponent(plotBerechnen);
		addComponent(plotPolstellen);
		

	}

	public void update(java.util.Observable obs, Object obj) {
		
		if (outputPanel.tracePreprocessed.dataValid && !plotBerechnenTracePreprocessedAdded) {
			plotBerechnen.setSubplot(0);
			plotBerechnenTracePreprocessedAdded = true;
			plotBerechnen.addTrace(outputPanel.tracePreprocessed);
			plotBerechnen.setRangeIdeal();
		}
		for (int i = 0; i < outputPanel.tracesSolution.length; i++) {
			if (outputPanel.tracesSolution[i].dataValid && !plotBerechnenTraceSolutionAdded[i]) {
				plotBerechnen.setSubplot(0);
				plotBerechnenTraceSolutionAdded[i] = true;
				plotBerechnen.addTrace(outputPanel.tracesSolution[i]);
				plotBerechnen.setRangeIdeal();
			}
			if (outputPanel.tracesPole[i].dataValid && !plotPolstellenTracePoleAdded[i]) {
				plotPolstellen.setSubplot(0);
				plotPolstellenTracePoleAdded[i] = true;
				plotPolstellen.addTrace(outputPanel.tracesPole[i]);
				plotPolstellen.setRangeIdeal();
			}
		}
	}
}
