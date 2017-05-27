package model;

import java.util.Observable;

public class Model extends Observable {

	public Network network;
	public boolean networkChanged = false;
	public MeasurementData measurementData;

	public static final int NOTIFY_REASON_MEASUREMENT_CHANGED = 0;
	public static final int NOTIFY_REASON_APPROXIMATION_DONE = 1;

	public Model() {
	}

	public void creatNetwork() {
		if (networkChanged) {
			if (network != null) {
				network.cancel(true);
				network.threadExecutor.shutdown();
			}
			network = new Network(measurementData, this);
			networkChanged = false;
		}
	}

	public void berechneUTF(int order) {
		network.calculateApproximation(order);
	}

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

	@Override
	public void notifyObservers() {
		super.setChanged();
		super.notifyObservers();
	}
}