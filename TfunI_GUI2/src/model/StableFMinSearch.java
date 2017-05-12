package model;

import org.apache.commons.math3.optim.PointValuePair;

import userInterface.StatusBar;

public class StableFMinSearch {

	static PointValuePair fminsearch(Target target, int ordnung) {

		double verbesserungsKoeff = 1e-4;
		double[] startWert = new double[ordnung + 1];
		double[] polySeiteLaenge = new double[ordnung + 1];
		PointValuePair koeffizienten = new PointValuePair(new double[] { 0 }, 0); // nur
																					// für
																					// den
																					// Compiler!

		// Werte initializieren:
		for (int i = 0; i < ordnung + 1; i++) {
			startWert[i] = 1.0;
			polySeiteLaenge[i] = 0.2;
		}
		if (Math.floor(ordnung / 2.0) != Math.ceil(ordnung / 2.0)) {
			startWert[startWert.length - 1] = -1;
		}

		//Berechnung:
		OverwatchedTask overwatchedTask;
		boolean problem = false;
		do {
			problem = false;
			int[] status = new int[] { OverwatchedTask.STATUS_ANFRAGE_PROBLEM };
			overwatchedTask = new OverwatchedTask(target, verbesserungsKoeff, startWert,
					polySeiteLaenge, status);

			// Berechnung starten:
			overwatchedTask.execute();
			while (status[0] != OverwatchedTask.STATUS_FERTIG) {
				
			}
			System.out.println("gut!"+status[0]);
			koeffizienten = overwatchedTask.getOptimum();
		} while (problem);

		

		return koeffizienten;
	}

}
