package model;

import java.util.List;

import javax.swing.SwingWorker;

import userInterface.StatusBar;

public class Approximation extends SwingWorker<Object, Integer> {

	public Approximation() {
	}

	public void stopp() {
	}

	@Override
	public Object doInBackground() {
		int zaeler = 0;
		try {
			for(zaeler=1;zaeler<=10;zaeler++){
				Thread.sleep(500);
				publish(zaeler);
			}
			
		} catch (InterruptedException e) {
		}

		return 0;
	}
	
	@Override
	protected void process(List<Integer> zaeler) {
		super.process(zaeler);
		StatusBar.showStatus("Zähler: "+zaeler.get(0));
	}
	
	@Override
	public void done() {
		super.done();
		StatusBar.showStatus("Fertig ");
	}
}
