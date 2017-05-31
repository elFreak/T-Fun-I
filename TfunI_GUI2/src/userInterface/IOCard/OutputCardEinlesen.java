package userInterface.IOCard;

import model.Model;
import userInterface.WindowContainer;
import userInterface.JavaPlot.Plot;

public class OutputCardEinlesen extends WindowContainer {
	private static final long serialVersionUID = 1L;
	private Plot plotEinlesen = new Plot();

	public OutputCardEinlesen(OutputPanel outputPanel) {
		
		addComponent(plotEinlesen);

		plotEinlesen.addTrace(outputPanel.traceStep);
		plotEinlesen.addTrace(outputPanel.traceRaw);
		plotEinlesen.setAxisLabel(Plot.XAXIS, "t", "", "s");
	}

	public void update(java.util.Observable obs, Object obj) {
		int reason = (int) obj;
		if (reason == Model.NOTIFY_REASON_MEASUREMENT_CHANGED) {
			plotEinlesen.setRangeIdeal();
		}
	}

	public void setAllRangeIdeal() {
		plotEinlesen.setRangeIdeal();
	}
}
