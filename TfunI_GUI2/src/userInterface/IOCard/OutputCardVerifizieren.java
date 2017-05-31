package userInterface.IOCard;

import java.util.Observable;

import model.Model;
import userInterface.Controller;
import userInterface.WindowContainer;
import userInterface.JavaPlot.Plot;

public class OutputCardVerifizieren extends WindowContainer {
	private static final long serialVersionUID = 1L;

	private Plot plotVerifizieren = new Plot();
	
	private Plot plotPolstellen = new Plot();
	
	private KoefPanel koefPanel;

	public OutputCardVerifizieren(OutputPanel outputPanel, Controller controller) {
		koefPanel = new KoefPanel(controller);
		addComponent(plotVerifizieren);
		addComponent(plotPolstellen);
		addComponent(koefPanel);
		
		plotVerifizieren.addSubplot();
		plotVerifizieren.connectSubplots();
		
		plotVerifizieren.setSubplot(0);
		plotVerifizieren.addTrace(outputPanel.tracePreprocessed);
		plotVerifizieren.addTrace(outputPanel.traceVerifizierenStep);
		plotVerifizieren.setSubplot(1);
		plotVerifizieren.addTrace(outputPanel.traceVerifizierenError);
		
		plotPolstellen.addTrace(outputPanel.traceVerifizierenPole);
		
		plotVerifizieren.setAxisLabel(Plot.XAXIS, "t", "", "s");
		plotPolstellen.setAxisLabel(Plot.XAXIS, "t", "", "s");		
	}

	public void update(Observable obs, Object obj) {
		koefPanel.update(obs, obj);
	}

	public void setVerifizierenOrder(int order) {
		koefPanel.setOrdnung(order);
	}
	
	public void setAllRangeIdeal() {
		plotVerifizieren.setSubplot(0);
		plotVerifizieren.setRangeIdeal();
		plotPolstellen.setRangeIdeal();
	}
}
