package userInterface.IOCard;

import java.util.Observable;

import model.Model;
import userInterface.Controller;
import userInterface.WindowContainer;
import userInterface.JavaPlot.Plot;

public class OutputCardVerifizieren extends WindowContainer {
	private static final long serialVersionUID = 1L;

	private Plot plotVerifizieren = new Plot();
	private KoefPanel koefPanel;

	public OutputCardVerifizieren(OutputPanel outputPanel, Controller controller) {
		koefPanel = new KoefPanel(controller);
		addComponent(plotVerifizieren);
		addComponent(koefPanel);
	}

	public void update(Observable obs, Object obj) {
		if ((int) obj == Model.NOTIFY_REASON_APPROXIMATION_DONE) {
			//koefPanel.update(obs);
		}
	}
}
