package userInterface;

import model.Model;
import model.UTFDatatype;

/**
 * 
 * @author Team 1
 *
 */
public class Controller {

	// --------------------------------------------------------------------
	// General:
	private View view;
	private Model model;

	// --------------------------------------------------------------------
	// Programm-Flow:
	// --------------------------------------------------------------------
	// ProgrammFlow:
	public final static int EINLESEN = 0;
	public final static String KEY_EINLESEN = "EINLESEN";
	public final static int BEARBEITEN = 1;
	public final static String KEY_BEARBEITEN = "BEARBEITEN";
	public final static int BERECHNEN = 2;
	public final static String KEY_BERECHNEN = "BERECHNEN";
	public final static int VERIFIZIEREN = 3;
	public final static String KEY_VERIFIZIEREN = "VERIFIZIEREN";

	public int mode = EINLESEN;

	public Controller(Model model) {
		this.model = model;
	}

	public void setView(View view) {
		this.view = view;
		view.inputPanel.setActualMode(EINLESEN);
		view.outputPanel.setActualMode(EINLESEN);
	}

	public void setActualMode(int mode) {
		boolean doChange = true;

		switch (this.mode) {
		case EINLESEN:
			if (mode != EINLESEN && model.measurementData == null) {
				doChange = false;
				StatusBar.showStatus("Lesen Sie zuerst Messwerte ein.", StatusBar.FEHLER);
			}
			break;
		case BEARBEITEN:
			break;
		case BERECHNEN:
			break;
		case VERIFIZIEREN:
			break;
		}

		if (doChange) {

			switch (mode) {
			case EINLESEN:
				break;
			case BEARBEITEN:
				break;
			case BERECHNEN:
				model.updateNetwork();
				break;
			case VERIFIZIEREN:
				boolean oneOrderDone = false;
				boolean[] orderDone = getBerechnenCBActive();
				for(int i=0;i<orderDone.length;i++) {
					if(orderDone[i]) {
						view.inputPanel.setVerifizizerenOrder(i+1);
						oneOrderDone = true;
						break;
					}
				}
				if(oneOrderDone==false) {
					for(int i=0;i<10;i++) {
						if(model.network.getApprox(i+1)!=null) {
							if(model.network.getApprox(i+1).isDone()) {
								view.inputPanel.setVerifizizerenOrder(i+1);
								oneOrderDone = true;
								break;
							}
						}
					}
				}
				if(oneOrderDone==false) {
					mode = this.mode;
					StatusBar.showStatus("Keine Berechnungen vorhanden. Berechne zuerst mindestens eine Ordnung.", StatusBar.FEHLER);
				}
				break;
			}
			this.mode = mode;
			view.inputPanel.setActualMode(mode);
			view.outputPanel.setActualMode(mode);
		}
	}

	public void setMesuredData(double[][] data) {
		if (data != null) {
			model.setMesuredData(data);
			model.measurementData.autoLimits();
			StatusBar.showStatus("Neue Sprungantwort wurde eingelesen.", StatusBar.INFO);
		}
	}

	public void filterChanged(int n) {
		model.measurementData.setMovingMean(n);
	}

	public void setRange(double tail, double offset) {
		if (tail < model.measurementData.getstepTime()) {
			if (tail != model.measurementData.getTail()) {
				tail = model.measurementData.getstepTime();
			}
		}
		model.measurementData.setLimits(offset, tail);
	}

	public void setStep(double stepTime, double stepHeight) {
		if (stepTime > model.measurementData.getTail()) {
			stepTime = model.measurementData.getTail();
		}

		model.measurementData.setStepHeight(stepHeight);
		model.measurementData.setStepTime(stepTime);
	}

	public void setStepTime(double stepTime) {
		if (stepTime > model.measurementData.getTail()) {
			stepTime = model.measurementData.getTail();
		}

		model.measurementData.setStepTime(stepTime);
	}

	public void setOriginalStep() {
		model.measurementData.setOriginalStep();
	}

	public void calculateUTF(int order) {
		model.berechneUTF(order);
	}

	public void autoLimmits() {
		model.measurementData.autoLimits();
	}

	public void setBerechnenCBActive(boolean[] value) {
		for (int i = 0; i < value.length; i++) {
			view.outputPanel.tracesSolution[i].dataValid = value[i];
			view.outputPanel.tracesPole[i].dataValid = value[i];
		}
		model.notifyObservers(Model.NOTIFY_REASON_APPROXIMATION_UPDATE);
	}

	public boolean[] getBerechnenCBActive() {
		return view.inputPanel.getBerechneCB();
	}

	public void changeApproximation(UTFDatatype utf) {
		model.network.getApprox(utf.ordnung).setUtf(utf);

	}

	public void berechnungLoeschen() {
		model.setBackNetwork();
	}

	public void setThresholdAndNorm(double threshold, int norm) {
		model.setNextThresholdandNorm(threshold, norm);
	}

	public void setVerifizierenOrder(int order) {
		view.outputPanel.setVerifzizerenOrder(order);
		model.notifyObservers(Model.NOTIFY_REASON_APPROXIMATION_UPDATE);
	}
}
