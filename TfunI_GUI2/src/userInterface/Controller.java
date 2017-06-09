package userInterface;

import javax.swing.JCheckBox;

import model.Approximation;
import model.MeasurementData;
import model.Model;
import model.Network;
import model.UTFDatatype;
import userInterface.IOCard.OutputCardBerechnen;
import userInterface.IOCard.OutputCardVerifizieren;
import userInterface.IOCard.OutputPanel;

/**
 * Dient als Controller nach dem MVC-Pattern. Seine Hauptaufgabe besteht in der
 * Weitergabe von Benutzeraktionen auf der {@link View}. Dabei muss der
 * {@link Controller} zum einen das {@link Model} und teilweise auch einen Teil
 * vom {@link View} über Benutzeraktionen informieren. Der {@link Controller}
 * verwalted ausserdem den aktuellen Zustand vom Programm.
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

	/**
	 * Erzeugt das {@link Object}.
	 * 
	 * @param model
	 */
	public Controller(Model model) {
		this.model = model;
	}

	/**
	 * Setzt die {@link View}.
	 * 
	 * @param view
	 */
	public void setView(View view) {
		this.view = view;
		view.inputPanel.setActualMode(EINLESEN);
		view.outputPanel.setActualMode(EINLESEN);
	}

	/**
	 * Setzt den aktuellen Zustand. Dabei müssen jedoch je nach gewünschtem
	 * Zustand diverse Bedingungen erfüllt sein. Damit wird verhindert, dass der
	 * Benutzer in einen Zustand wechseln kann, in welchem noch gar keine
	 * Aktionen verfügbar sind. Falls der Zustandswechsel nicht möglich ist,
	 * wird der Benutzer mittels der {@link StatusBar} informiert.
	 * 
	 * @param mode
	 */
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
				for (int i = 0; i < orderDone.length; i++) {
					if (orderDone[i]) {
						view.inputPanel.setVerifizizerenOrder(i + 1);
						oneOrderDone = true;
						break;
					}
				}
				if (oneOrderDone == false) {
					if (model.network != null) {
						for (int i = 0; i < 10; i++) {
							if (model.network.getApprox(i + 1) != null) {
								if (model.network.getApprox(i + 1).isDone()) {
									view.inputPanel.setVerifizizerenOrder(i + 1);
									oneOrderDone = true;
									break;
								}
							}
						}
					}
				}
				if (oneOrderDone == false) {
					mode = this.mode;
					StatusBar.showStatus("Keine Berechnungen vorhanden. Berechne zuerst mindestens eine Ordnung.",
							StatusBar.FEHLER);
				}
				break;
			}
			this.mode = mode;
			view.inputPanel.setActualMode(mode);
			view.outputPanel.setActualMode(mode);
		}
	}

	/**
	 * Übermittelt dem {@link Model} neue Messdaten.
	 * 
	 * @param data
	 */
	public void setMesuredData(double[][] data) {
		if (data != null) {
			model.setMesuredData(data);
			model.measurementData.autoLimits();
			model.measurementData.setMovingMean(0);
			StatusBar.showStatus("Neue Sprungantwort wurde eingelesen.", StatusBar.INFO);
		}
	}

	/**
	 * Informiert die {@link MeasurementData} über eine Änderung am Filterwert.
	 * 
	 * @param n
	 */
	public void filterChanged(int n) {
		model.measurementData.setMovingMean(n);
	}

	/**
	 * Informiert die {@link MeasurementData} über eine Änderung des Offsets und
	 * wieviel vom Signal hinten abgeschnitten wird.
	 * 
	 * @param tail
	 * @param offset
	 */
	public void setRange(double tail, double offset) {
		if (tail < model.measurementData.getstepTime()) {
			if (tail != model.measurementData.getTail()) {
				tail = model.measurementData.getstepTime();
			}
		}
		model.measurementData.setLimits(offset, tail);
	}

	/**
	 * Informiert die {@link MeasurementData} über eine Änderung der Sprunghöhe.
	 * 
	 * @param stepTime
	 * @param stepHeight
	 */
	public void setStep(double stepTime, double stepHeight) {
		if (stepTime > model.measurementData.getTail()) {
			stepTime = model.measurementData.getTail();
		}

		model.measurementData.setStepHeight(stepHeight);
		model.measurementData.setStepTime(stepTime);
	}

	/**
	 * Informiert die {@link MeasurementData} über eine Änderung der Sprungzeit.
	 * 
	 * @param stepTime
	 */
	public void setStepTime(double stepTime) {
		if (stepTime > model.measurementData.getTail()) {
			stepTime = model.measurementData.getTail();
		}

		model.measurementData.setStepTime(stepTime);
	}

	/**
	 * Gibt den {@link MeasurementData} den Befehl, den Sprung zurückzusetzen.
	 */
	public void setOriginalStep() {
		model.measurementData.setOriginalStep();
	}

	/**
	 * Gibt den {@link MeasurementData} den Befehl, den Offset und wieviel vom
	 * Signal hinten abgeschnitten wird, automatisch zu bestimmen.
	 */
	public void autoLimmits() {
		model.measurementData.autoLimits();
	}

	/**
	 * Gibt dem {@link Model} den Befehl, die Übertragungsfunktion der
	 * gewünschten Polstellenordnung zu Berechnen.
	 * 
	 * @param order
	 */
	public void calculateUTF(int order) {
		model.berechneUTF(order);
	}

	/**
	 * Gibt dem {@link OutputPanel} den Befehl, die {@link JCheckBox} für die
	 * Ordnungsauswahl vom {@link OutputCardBerechnen} anhand der Argumente zu
	 * setzen.
	 * 
	 * @param value
	 */
	public void setBerechnenCBActive(boolean[] value) {
		for (int i = 0; i < value.length; i++) {
			view.outputPanel.tracesSolution[i].dataValid = value[i];
			view.outputPanel.tracesPole[i].dataValid = value[i];
		}
		model.notifyObservers(Model.NOTIFY_REASON_APPROXIMATION_UPDATE);
	}

	/**
	 * Gibt als Rückgabewert aus, welche {@link JCheckBox} für die
	 * Ordnungsauswahl vom {@link OutputCardBerechnen} aktiv sind.
	 * 
	 * @return CBActive[boolean]
	 */
	public boolean[] getBerechnenCBActive() {
		return view.inputPanel.getBerechneCB();
	}

	/**
	 * Ändert die Übertragungsfunktion von der im {@link UTFDatatype}
	 * mitgegebenen Ordnung im {@link Network}
	 * 
	 * @param utf
	 */
	public void changeApproximation(UTFDatatype utf) {
		model.network.getApprox(utf.ordnung).setUtf(utf);

	}

	/**
	 * Setzt das {@link Network} zurück und löst damit alle
	 * {@link Approximation}.
	 */
	public void berechnungLoeschen() {
		model.setBackNetwork();
	}

	/**
	 * Informiert das {@link Model} über eine Änderung vom Threshold.
	 * 
	 * @param threshold
	 * @param norm
	 */
	public void setThresholdAndNorm(double threshold, int norm) {
		model.setNextThresholdandNorm(threshold, norm);
	}

	/**
	 * Gibt dem {@link OutputPanel} den Befehl, die {@link JCheckBox} für die
	 * Ordnungsauswahl vom {@link OutputCardVerifizieren} anhand der Argumente
	 * zu setzen.
	 * 
	 * @param order
	 */
	public void setVerifizierenOrder(int order) {
		view.outputPanel.setVerifzizerenOrder(order);
		model.notifyObservers(Model.NOTIFY_REASON_APPROXIMATION_UPDATE);
	}
}
