package userInterface.IOCard;

import java.awt.CardLayout;

import javax.swing.JPanel;

import model.Model;
import projectT_Fun_I.GlobalSettings;
import projectT_Fun_I.Utility;
import userInterface.Controller;
import userInterface.MyBorderFactory;
import userInterface.JavaPlot.Trace;

public class OutputPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	/**
	 * General
	 */
	Controller controller;
	private CardLayout cardLayout = new CardLayout();

	/**
	 * Traces
	 */

	public Trace traceStep;
	public Trace traceRaw;
	public Trace tracePreprocessed;
	public Trace traceMean;
	public Trace[] tracesSolution = new Trace[9];
	public Trace[] tracesPole = new Trace[9];
	public Trace traceKorKoeffCompare;
	public Trace[] traceKorKoeffPoints = new Trace[9];

	/**
	 * Cards:
	 */
	private OutputCardEinlesen cardEinlesen;
	private OutputCardBearbeiten cardBearbeiten;
	private OutputCardBerechnen cardBerechnen;
	private OutputCardVerifizieren cardVerifizieren;

	public OutputPanel(Controller controller) {
		this.controller = controller;

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

		traceMean = new Trace();
		traceMean.usePreferedColor = true;
		traceMean.preferedColor = GlobalSettings.colorTraceOrange;

		traceKorKoeffCompare = new Trace();
		traceKorKoeffCompare.usePreferedColor = true;
		traceKorKoeffCompare.preferedColor = GlobalSettings.colorTraceGrey;

		for (int i = 0; i < traceKorKoeffPoints.length; i++) {
			traceKorKoeffPoints[i] = new Trace();
			traceKorKoeffPoints[i].usePreferedColor = true;
			traceKorKoeffPoints[i].preferedColor = GlobalSettings.colorsTraceSolution[i];
			traceKorKoeffPoints[i].lineType = Trace.LINE_NONE;
			traceKorKoeffPoints[i].pointType = Trace.POINT_BULLET;
		}

		for (int i = 0; i < tracesSolution.length; i++) {
			tracesSolution[i] = new Trace();
			tracesSolution[i].usePreferedColor = true;
			tracesSolution[i].preferedColor = GlobalSettings.colorsTraceSolution[i];
			tracesPole[i] = new Trace();
			tracesPole[i].usePreferedColor = true;
			tracesPole[i].preferedColor = GlobalSettings.colorsTraceSolution[i];
			tracesPole[i].lineType = Trace.LINE_NONE;
			tracesPole[i].pointType = Trace.POINT_CROSS;
		}

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
		if (!(obs instanceof Model) || !(obj instanceof Integer)) {
			throw (new IllegalArgumentException());
		}

		switch ((int) obj) {
		case Model.NOTIFY_REASON_NEW_DATA:
			for (int i = 0; i < tracesSolution.length; i++) {
				tracesSolution[i].dataValid = false;
				tracesPole[i].dataValid = false;
			}
			for (int i = 0; i < traceKorKoeffPoints.length; i++) {
				traceKorKoeffPoints[i].dataValid = false;
			}
			traceKorKoeffCompare.dataValid = false;

			break;
		case Model.NOTIFY_REASON_MEASUREMENT_CHANGED:
			traceStep.data = ((Model) obs).measurementData.getstep();
			traceStep.dataValid = true;
			traceRaw.data = ((Model) obs).measurementData.getRawData();
			traceRaw.dataValid = true;
			tracePreprocessed.data = ((Model) obs).measurementData.getFinalData();
			tracePreprocessed.dataValid = true;
			traceMean.data = ((Model) obs).measurementData.getMeanData();
			traceMean.dataValid = true;
			break;
		case Model.NOTIFY_REASON_APPROXIMATION_DONE:
			traceKorKoeffCompare.data = ((Model) obs).network.getKorrelationComparison();
			traceKorKoeffCompare.dataValid = true;

			for (int i = 0; i < tracesSolution.length; i++) {
				if (((Model) obs).network.getApprox(i + 2) != null && controller.getBerechnenCBActive()[i] == true) {
					if (((Model) obs).network.getApprox(i + 2).getPole()[0] != null) {
						tracesSolution[i].data = ((Model) obs).network.getApprox(i + 2).getStepResponse();
						tracesSolution[i].dataValid = true;
						tracesPole[i].data = new double[][] {
								((Model) obs).network.getApprox(i + 2).getPole()[0].getPoint(),
								((Model) obs).network.getApprox(i + 2).getPole()[1].getPoint() };
						tracesPole[i].dataValid = true;
						traceKorKoeffPoints[i].data = new double[][] { { i + 2 },
								{ ((Model) obs).network.getApprox(i + 2).getKorrKoef() } };
						traceKorKoeffPoints[i].dataValid = true;
					} else {
						tracesSolution[i].dataValid = false;
						tracesPole[i].dataValid = false;
						traceKorKoeffPoints[i].dataValid = false;
					}
				}
			}
			break;
		}

		cardEinlesen.update(obs, obj);
		cardBearbeiten.update(obs, obj);
		cardBerechnen.update(obs, obj);
	}
}
