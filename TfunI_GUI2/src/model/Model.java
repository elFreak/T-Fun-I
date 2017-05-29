package model;

import java.util.Observable;

public class Model extends Observable {
	
	private double threshold = 0.001;

	public Network network;
	public boolean networkChanged = false;
	public MeasurementData measurementData;

	public static final int NOTIFY_REASON_MEASUREMENT_CHANGED = 0;
	public static final int NOTIFY_REASON_APPROXIMATION_DONE = 1;
	public static final int NOTIFY_REASON_NETWORK_START_VALUES = 2;
	public static final int NOTIFY_REASON_THRESHOLD_CHANGED = 3;
	public static final int NOTIFY_REASON_NEW_DATA = 4;

	public Model() {
		threshold = 1e-8;setChanged();setChanged();
		notifyObservers(NOTIFY_REASON_THRESHOLD_CHANGED);
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
		if (network != null) {
			network.cancel(true);
			network.threadExecutor.shutdown();
			network = null;
		}
		notifyObservers(NOTIFY_REASON_MEASUREMENT_CHANGED);
		notifyObservers(NOTIFY_REASON_NEW_DATA);
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

	public void deleteNetwork() {
		networkChanged = true;
		creatNetwork();
		notifyObservers(Model.NOTIFY_REASON_NEW_DATA);
	}
	
	public void setThreshold(double threshold) {
		this.threshold = threshold;
		notifyObservers(Model.NOTIFY_REASON_THRESHOLD_CHANGED);
	}

	public double getThreshold() {
		// TODO Auto-generated method stub
		return threshold;
	}
}