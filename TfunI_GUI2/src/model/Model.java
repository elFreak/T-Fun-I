package model;

import java.util.Observable;

/**
 * Dient als Model nach dem MVC-Pattern. Verwalted in erster Line ein
 * {@link Network} und ein {@link MeasurementData}.
 * 
 * @author Team 1
 *
 */
public class Model extends Observable {

	// Verknüpfungen zu anderen Objekten:
	public Network network;
	public MeasurementData measurementData;

	// Eigenschaften:
	private double nextThreshold = 1e-8;
	private boolean networkChanged = false;

	// Obserververwaltung:
	public static final int NOTIFY_REASON_MEASUREMENT_CHANGED = 0;
	public static final int NOTIFY_REASON_APPROXIMATION_UPDATE = 1;
	public static final int NOTIFY_REASON_NETWORK_START_VALUES = 2;
	public static final int NOTIFY_REASON_THRESHOLD_OR_NORM_CHANGED = 3;
	public static final int NOTIFY_REASON_UPDATE_NETWORK = 4;

	// -----------------------------------------------------------------------------------------------------------------
	// Konstrucktor:
	// -----------------------------------------------------------------------------------------------------------------
	/**
	 * Erzeugt das Objekt.
	 */
	public Model() {
	}

	// -----------------------------------------------------------------------------------------------------------------
	// Diverse Methoden:
	// -----------------------------------------------------------------------------------------------------------------
	/**
	 * Erstellt ein neues Network, falls sich die Daten vom {@link MeasurementData} geändert haben.
	 */
	public void updateNetwork() {
		if (networkChanged) {
			if (network != null) {// Falls bereits ein Network besteht, wird
									// dieses aufgelöst.
				network.stop();
			}
			network = new Network(measurementData, this);
			networkChanged = false;
			notifyObservers(NOTIFY_REASON_UPDATE_NETWORK);
		}
		notifyObservers(Model.NOTIFY_REASON_APPROXIMATION_UPDATE);
	}
	
	/**
	 * Erstellt auf jeden Fall ein neues Network.
	 */
	public void setBackNetwork() {
		networkChanged = true;
		updateNetwork();
	}

	/**
	 * Leited den Befehl weiter an das {@link Network}
	 * 
	 * @param order
	 */
	public void berechneUTF(int order) {
		network.calculateApproximation(order);
	}

	/**
	 * Erstellt ein neues {@link MeasurementData} anhand der übergebenen Sprungantwort.
	 * 
	 * @param data
	 */
	public void setMesuredData(double[][] data) {
		measurementData = new MeasurementData(this, data);
		notifyObservers(NOTIFY_REASON_MEASUREMENT_CHANGED);
	}

	@Override
	public void notifyObservers(Object object) {

		if ((int) object == NOTIFY_REASON_MEASUREMENT_CHANGED) {
			networkChanged = true;
		}

		super.setChanged();
		super.notifyObservers(object);
	}

	// -----------------------------------------------------------------------------------------------------------------
	// Get-/Set-Methoden:
	// -----------------------------------------------------------------------------------------------------------------

	public void setNextThresholdandNorm(double threshold, int norm) {
		this.nextThreshold = threshold;
		measurementData.setNorm(norm);
		notifyObservers(Model.NOTIFY_REASON_THRESHOLD_OR_NORM_CHANGED);
	}

	public double getNextThreshold() {
		return nextThreshold;
	}

}