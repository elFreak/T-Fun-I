package IOCard;

import java.awt.CardLayout;

import javax.swing.JPanel;

import JavaPlot.Trace;
import projectT_Fun_I.GlobalSettings;
import projectT_Fun_I.Utility;
import userInterface.Controller;
import userInterface.MyBorderFactory;

public class OutputPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	/**
	 * General
	 */
	private Controller controller;
	private CardLayout cardLayout = new CardLayout();

	/**
	 * Traces
	 */
	public JavaPlot.Trace traceStep;
	public JavaPlot.Trace traceRaw;
	public JavaPlot.Trace traceMean;
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
		traceMean = new Trace();
		traceMean.usePreferedColor = true;
		traceMean.preferedColor = GlobalSettings.colorTraceOrange;
		traceSolution=new Trace();

		// Init Cards:
		cardEinlesen = new OutputCardEinlesen(this);
		cardBearbeiten = new OutputCardBearbeiten(this,controller);
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
}
