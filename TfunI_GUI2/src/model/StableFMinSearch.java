package model;

import org.apache.commons.math3.optim.PointValuePair;

import userInterface.StatusBar;

public class StableFMinSearch {

	static public PointValuePair fminsearch(Target target, int ordnung) {

		double verbesserungsKoeff = 1e-4;
		double[] startWert = new double[ordnung + 1];
		double[] polySeiteLaenge = new double[ordnung + 1];

		// Werte initializieren:
		for (int i = 0; i < ordnung + 1; i++) {
			startWert[i] = 1.0;
			polySeiteLaenge[i] = 0.2;
		}
		if (Math.floor(ordnung / 2.0) != Math.ceil(ordnung / 2.0)) {
			startWert[startWert.length - 1] = -1;
		}

		PointValuePair koeffizienten = new PointValuePair(startWert, 0); // nur
																					// für
																					// den
																					// Compiler!

		// Berechnen:
		for (int i = 0; i <= 4; i++) {
			verbesserungsKoeff /= 10000;
			for (int j = 0; j < ordnung + 1; j++) {
				polySeiteLaenge[i] /= 2;
			}
			koeffizienten = berechnen(target, verbesserungsKoeff, koeffizienten.getPoint(), polySeiteLaenge);
		}

		return koeffizienten;
	}

	/**
	 * Eigentliche Berechnungsfunktion, welche dafür sorgt, dass Programme die
	 * abkacken, neugestarted werden.
	 * 
	 * @param target
	 * @param verbesserungsKoeff
	 * @param startWert
	 * @param polySeiteLaenge
	 * @return
	 */
	private static PointValuePair berechnen(Target target, double verbesserungsKoeff, double[] startWert,
			double[] polySeiteLaenge) {
		// Berechnung:
		OverwatchedTask overwatchedTask;
		boolean problem;
		do {
			problem = false;
			int[] status = new int[] { OverwatchedTask.STATUS_IN_ARBEIT };
			overwatchedTask = new OverwatchedTask(target, verbesserungsKoeff, startWert, polySeiteLaenge, status);

			// Berechnung starten:
			overwatchedTask.execute();
			long startTime = 0;

			while (status[0] != OverwatchedTask.STATUS_FERTIG) {

				if (status[0] == OverwatchedTask.STATUS_IN_ARBEIT) {
					status[0] = OverwatchedTask.STATUS_PROBLEM_ABFRAGEN;
					startTime = System.currentTimeMillis();
				}

				if (status[0] == OverwatchedTask.STATUS_PROBLEM_ABFRAGEN
						&& System.currentTimeMillis() - startTime > 2000) {
					problem = true;
					StatusBar.showStatus("Abgestürzt", StatusBar.FEHLER);
					break;
				}

				// Eine kleine Verzögerung muss eingebaut werden, damit alles
				// funktioniert.
				try {
					Thread.sleep(1);
				} catch (Exception e) {
				}
			}
		} while (problem);
		return overwatchedTask.getOptimum();
	}
}
