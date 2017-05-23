package model;

import java.util.Observable;

public class Model extends Observable{
		
	public Approximation approximation;
	public MeasurementData measurementData;
	
	public static final int NOTIFY_REASON_MEASUREMENT_CHANGED = 0;
	public static final int NOTIFY_REASON_APPROXIMATION_DONE = 1;
	
	public Model() {
	}
	
	public void berechneUTF() {
		approximation = new Approximation(measurementData, this);
	}

	public void setMesuredData(double[][] data) {
		measurementData = new MeasurementData(this, data);
		notifyObservers(NOTIFY_REASON_MEASUREMENT_CHANGED);
	}
	
	@Override
	public void notifyObservers(Object object){
		super.setChanged();
		super.notifyObservers(object);
	}
	@Override
	public void notifyObservers(){
		super.setChanged();
		super.notifyObservers();
	}
}