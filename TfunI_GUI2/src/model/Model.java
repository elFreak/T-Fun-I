package model;

import java.util.Observable;

public class Model extends Observable{
	
	public MeasurementData measurementData;
	
	public Model() {
	}

	public void setMesuredData(double[][] data) {
		measurementData = new MeasurementData(data);
		notifyObservers();
	}
	
	@Override
	public void notifyObservers(){
		super.setChanged();
		super.notifyObservers();
	}
}