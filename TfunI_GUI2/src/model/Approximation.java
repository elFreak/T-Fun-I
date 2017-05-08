package model;

import com.sun.org.apache.xerces.internal.util.Status;

import userInterface.StatusBar;

public class Approximation implements Runnable {

	@Override
	public void run() {
		
		int i = 0;
		try {
			for (i = 0; i < 2; i++) {
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
		}
	}
	
	public void zaeler(){
		StatusBar.showStatus("Zähler-Start");
		run();
		StatusBar.showStatus("Zähler-Ende");
	}

}
