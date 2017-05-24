package userInterface.IOCard;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;

import projectT_Fun_I.GlobalSettings;
import userInterface.MyBorderFactory;
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
//				double[] range = plotPolstellen.getRange();
//				range[1] = (range[1]-range[0])*0.2;
//				range[2] = (range[2]*1.2);
//				range[3] = (range[3]*1.2);
//				plotPolstellen.setRange(Plot.XAXIS, range[0], range[1]);
//				plotPolstellen.setRange(Plot.Y1AXIS, range[2], range[3]);
			}
		}
	}
}
