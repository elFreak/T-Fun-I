package userInterface.IOCard;

import model.Model;
import userInterface.WindowContainer;
import userInterface.JavaPlot.Plot;
import userInterface.JavaPlot.Trace;

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

	private Plot plotCompare = new Plot();
	private boolean plotCompareTraceKorKoeffAdded = false;
	private boolean[] plotCompareTraceKorPointsAdded = new boolean[] { false, false, false, false, false, false, false,
			false, false };

	public OutputCardBerechnen(OutputPanel outputPanel) {
		this.outputPanel = outputPanel;

		addComponent(plotBerechnen);
		addComponent(plotPolstellen);
		addComponent(plotCompare);

	}

	public void update(java.util.Observable obs, Object obj) {

		if (outputPanel.traceKorKoeffCompare.dataValid && !plotCompareTraceKorKoeffAdded) {
			plotCompare.setSubplot(0);
			plotCompareTraceKorKoeffAdded = true;
			plotCompare.addTrace(outputPanel.traceKorKoeffCompare);
		}
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
			} else {
				plotBerechnenTraceSolutionAdded[i] = false;
			}
			if (outputPanel.tracesPole[i].dataValid && !plotPolstellenTracePoleAdded[i]) {
				plotPolstellen.setSubplot(0);
				plotPolstellenTracePoleAdded[i] = true;
				plotPolstellen.addTrace(outputPanel.tracesPole[i]);
				plotPolstellen.setRangeIdeal();
			} else {
				plotPolstellenTracePoleAdded[i] = false;
			}
			if (outputPanel.traceKorKoeffPoints[i].dataValid && !plotCompareTraceKorPointsAdded[i]) {
				plotCompare.setSubplot(0);
				plotCompareTraceKorPointsAdded[i] = true;
				plotCompare.addTrace(outputPanel.traceKorKoeffPoints[i]);
				plotCompare.setRangeIdeal();
				plotCompare.setRange(Trace.XAXIS, 1.0, 11.0);
			} else {
				plotCompareTraceKorPointsAdded[i] = false;
			}
		}

		if (((int) obj) == Model.NOTIFY_REASON_NEW_DATA) {
			plotBerechnen.setRangeIdeal();
			plotCompare.setRangeIdeal();
			plotPolstellen.setRangeIdeal();

		}
	}
}
