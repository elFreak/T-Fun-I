package userInterface.IOCard;

import java.util.Observable;

import model.Model;
import userInterface.WindowContainer;
import userInterface.JavaPlot.Plot;

public class OutputCardVerifizieren extends WindowContainer {
	private static final long serialVersionUID = 1L;

	private Plot plotVerifizieren = new Plot();
	private KoefPanel koefPanel = new KoefPanel();

	public OutputCardVerifizieren(OutputPanel outputPanel) {
		addComponent(plotVerifizieren);
		addComponent(koefPanel);
	}

	public void update(Observable obs, Object obj) {
		if ((int) obj == Model.NOTIFY_REASON_APPROXIMATION_DONE) {
			//koefPanel.update(obs);
		}
	}
}
