package model;

import java.util.Observable;

public class Model extends Observable{
		
	public Approximation approximation;
	public MeasurementData measurementData;
	
	public Model() {
		
	}

	public void setMesuredData(double[][] data) {
		measurementData = new MeasurementData(this, data);
		approximation = new Approximation(measurementData, this);
		notifyObservers();
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