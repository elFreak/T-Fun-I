package model;

import java.util.Observable;

public class Model extends Observable{
		
	public MeasurementData measurementData;
	
	public Model() {
	}

	public void setMesuredData(double[][] data) {
		measurementData = new MeasurementData(this, data);
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