package userInterface.IOCard;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import model.Model;
import projectT_Fun_I.GlobalSettings;
import userInterface.Controller;
import userInterface.SplitPaneContainer;
import userInterface.JavaPlot.Plot;

/**
 * Oberfläche für Ausgaben im Zustand Verifizieren.
 * 
 * @author Team 1
 *
 */
public class OutputCardVerifizieren extends JPanel {
	private static final long serialVersionUID = 1L;

	JPanel panelLeft = new JPanel(new GridBagLayout());

	private Plot plotVerifizieren = new Plot();
	private Plot plotPolstellen = new Plot();

	private KoefPanel koefPanel;

	/**
	 * Erzeugt und initialisiert das Objekt.
	 * 
	 * @param controller
	 */
	public OutputCardVerifizieren(OutputPanel outputPanel, Controller controller) {

		JPanel panelBackground = new JPanel(new GridLayout(1, 1));
		panelBackground.setBackground(GlobalSettings.colorBackgroundWhite);
		koefPanel = new KoefPanel(controller);
		// panelLeft.add(plotPolstellen, new GridBagConstraints(0, 0, 2, 1, 1.0,
		// 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new
		// Insets(0, 0, 0, 0), 0, 0));
		panelLeft.add(koefPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		// //panelLeft.add(panelBackground, new GridBagConstraints(1, 0, 1, 1,
		// 0.0, 0.0, GridBagConstraints.CENTER,
		// GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		// add(new SplitPaneContainer(JSplitPane.HORIZONTAL_SPLIT, new
		// SplitPaneContainer(JSplitPane.VERTICAL_SPLIT, plotPolstellen,
		// koefPanel, 1.0, 1.0), plotVerifizieren, 1.0, 1.0));

		setBackground(GlobalSettings.colorBackgroundWhite);
		setLayout(new GridBagLayout());

		add(new SplitPaneContainer(JSplitPane.HORIZONTAL_SPLIT,
				new SplitPaneContainer(JSplitPane.VERTICAL_SPLIT, plotPolstellen, panelLeft, 1.0, 0.05),
				plotVerifizieren, 0.05, 1.0),
				new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 0), 0, 0));

		plotVerifizieren.addSubplot();
		plotVerifizieren.connectSubplots();

		plotVerifizieren.setSubplot(0);
		plotVerifizieren.addTrace(outputPanel.tracePreprocessed);
		plotVerifizieren.addTrace(outputPanel.traceVerifizierenStep);
		plotVerifizieren.setSubplot(1);
		plotVerifizieren.addTrace(outputPanel.traceVerifizierenError);

		plotPolstellen.addTrace(outputPanel.traceVerifizierenPole);

		plotVerifizieren.setAxisLabel(Plot.XAXIS, "t", "", " in s");
		plotVerifizieren.setAxisLabel(Plot.Y1AXIS, "U", "", "  in V");
		plotPolstellen.setAxisLabel(Plot.XAXIS, "Real(s)", "", "");
		plotPolstellen.setAxisLabel(Plot.Y1AXIS, "Imag(s)", "", "");
	}

	/**
	 * Updated das Objekt. 
	 * @see Observer
	 * 
	 * @param obs
	 * @param obj
	 */
	public void update(Observable obs, Object obj) {
		koefPanel.update(obs, obj);

		int reason = (int) obj;
		if (reason == Model.NOTIFY_REASON_APPROXIMATION_UPDATE) {
			setAllRangeIdeal();
		}
	}

	/**
	 * Setzt die aktuelle Ordnung.
	 * 
	 * @param order
	 */
	public void setVerifizierenOrder(int order) {
		koefPanel.setOrdnung(order);
	}

	/**
	 * Skaliert alle Plots automatisch.
	 */
	public void setAllRangeIdeal() {
		plotVerifizieren.setSubplot(0);
		plotVerifizieren.setRangeIdeal();
		plotPolstellen.setRangeIdeal();
	}
}
