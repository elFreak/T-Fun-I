package IOCard;

import java.awt.CardLayout;

import javax.swing.JPanel;

import JavaPlot.Trace;
import model.Model;
import projectT_Fun_I.GlobalSettings;
import projectT_Fun_I.Utility;
import userInterface.Controller;
import userInterface.MyBorderFactory;

public class OutputPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	/**
	 * General
	 */
	private CardLayout cardLayout = new CardLayout();

	/**
	 * Traces
	 */
	public JavaPlot.Trace traceStep;
	public JavaPlot.Trace traceRaw;
	public JavaPlot.Trace tracePreprocessed;
	public JavaPlot.Trace traceSolution;

	/**
	 * Cards:
	 */
	public OutputCardEinlesen cardEinlesen;
	public OutputCardBearbeiten cardBearbeiten;
	public OutputCardBerechnen cardBerechnen;
	public OutputCardVerifizieren cardVerifizieren;

	public OutputPanel(Controller controller) {

		// Output-Panel Design:
		setBorder(MyBorderFactory.createMyBorder("  Ausgabe  "));
		Utility.setAllBackgrounds(this, GlobalSettings.colorBackground);
		setOpaque(true);
		setBackground(GlobalSettings.colorBackgroundBlueBright);

		// Init Traces:
		traceStep = new Trace();
		traceStep.usePreferedColor = true;
		traceStep.preferedColor = GlobalSettings.colorTraceGreen;
		traceRaw = new Trace();
		traceRaw.usePreferedColor = true;
		traceRaw.preferedColor = GlobalSettings.colorTraceYellow;
		tracePreprocessed = new Trace();
		tracePreprocessed.usePreferedColor = true;
		tracePreprocessed.preferedColor = GlobalSettings.colorTraceOrange;
		traceSolution = new Trace();

		// Init Cards:
		cardEinlesen = new OutputCardEinlesen(this);
		cardBearbeiten = new OutputCardBearbeiten(this, controller);
		cardBerechnen = new OutputCardBerechnen(this);
		cardVerifizieren = new OutputCardVerifizieren(this);

		// Card Layout:
		setLayout(cardLayout);
		add(cardEinlesen, Controller.KEY_EINLESEN);
		add(cardBearbeiten, Controller.KEY_BEARBEITEN);
		add(cardBerechnen, Controller.KEY_BERECHNEN);
		add(cardVerifizieren, Controller.KEY_VERIFIZIEREN);

	}

	public void setActualMode(int mode) {
		switch (mode) {
		case Controller.EINLESEN:
			cardLayout.show(this, Controller.KEY_EINLESEN);
			break;
		case Controller.BEARBEITEN:
			cardLayout.show(this, Controller.KEY_BEARBEITEN);
			break;
		case Controller.BERECHNEN:
			cardLayout.show(this, Controller.KEY_BERECHNEN);
			break;
		case Controller.VERIFIZIEREN:
			cardLayout.show(this, Controller.KEY_VERIFIZIEREN);
			break;
		}
	}

	public void update(java.util.Observable obs, Object obj) {
		// Traces Aktualisieren:
		traceStep.data = ((Model) obs).measurementData.getstep();
		traceRaw.data = ((Model) obs).measurementData.getRawData();
		tracePreprocessed.data = ((Model) obs).measurementData.getFinalData();
		traceSolution.data = ((Model) obs).approximation.stepAnswer;

		if (traceRaw.dataValid == false) {

			traceStep.dataValid = true;
			traceRaw.dataValid = true;
			tracePreprocessed.dataValid = true;
			traceSolution.dataValid = true;
			cardEinlesen.plotEinlesen.setRangeIdeal();
			cardBearbeiten.plotBearbeiten.setSubplot(0);
			cardBearbeiten.plotBearbeiten.setRangeIdeal();
		} else {
			traceStep.dataValid = true;
			traceRaw.dataValid = true;
			tracePreprocessed.dataValid = true;
			traceSolution.dataValid = true;
			cardBearbeiten.plotBearbeiten.setSubplot(1);
			cardBearbeiten.plotBearbeiten.setRangeIdeal();
		}
	}
}
