package model;

import org.apache.commons.math3.optim.PointValuePair;

public class StableFMinSearch {

	static public PointValuePair fminsearch(Target target, int ordnung, SwingWorkerClient client) {

		double[] startWert;
		if (ordnung % 2 == 0) {
			startWert = new double[] { 1, 1, 1 };
		} else {
			startWert = new double[] { 1, -1 };
		}

		PointValuePair utf = new PointValuePair(startWert, 0);

		for (int i = ordnung % 2 + 2; i <= ordnung; i += 2) {
			double[] new_utf = new double[i + 1];
			for (int j = 0; j < utf.getPoint().length; j++) {
				new_utf[j] = utf.getPoint()[j];
			}
			if (i % 2 == 0) {
				new_utf[new_utf.length - 1] = new_utf[1];
				new_utf[new_utf.length - 2] = new_utf[2];
			} else {
				new_utf[new_utf.length - 1] = new_utf[new_utf.length - 3];
				if (i == 3) {
					new_utf[new_utf.length - 2] = 1.0;
					new_utf[new_utf.length - 3] = 1.0;
				} else {
					new_utf[new_utf.length - 2] = new_utf[1];
					new_utf[new_utf.length - 3] = new_utf[2];
				}
			}

			utf = new PointValuePair(new_utf, 0);

			utf = berechneOrdnungN(target, i, utf, client);

			SwingWorkerInfoDatatype info1 = new SwingWorkerInfoDatatype();
			info1.isStatus = true;
			info1.isFehler = false;
			info1.status = "Ordnung " + i + " wurde berechne.";
			client.swingAction(info1);

		}

		return utf;

	}

	private static PointValuePair berechneOrdnungN(Target target, int ordnung, PointValuePair startValue,
			SwingWorkerClient client) {
		double verbesserungsKoeff = 1e-6;
		double[] polySeiteLaenge = new double[ordnung + 1];

		// Werte initializieren:
		for (int i = 0; i < ordnung + 1; i++) {
			polySeiteLaenge[i] = 0.2;
		}
		
		//verbesserungsKoeff = verbesserungsKoeff / Math.pow(10, (ordnung-2)*0.5);

		PointValuePair koeffizienten = startValue; // nur
													// f�r
													// den
													// Compiler!

		// Berechnen:
		for (int i = 0; i <= 0; i++) {
			verbesserungsKoeff/=1;
			koeffizienten = berechnen(target, verbesserungsKoeff, koeffizienten.getPoint(), polySeiteLaenge, client);
		}

		return koeffizienten;
	}

	/**
	 * Eigentliche Berechnungsfunktion, welche daf�r sorgt, dass Programme die
	 * abkacken, neugestarted werden.
	 * 
	 * @param target
	 * @param verbesserungsKoeff
	 * @param startWert
	 * @param polySeiteLaenge
	 * @return
	 */
	private static PointValuePair berechnen(Target target, double verbesserungsKoeff, double[] startWert,
			double[] polySeiteLaenge, SwingWorkerClient client) {
		// Berechnung:
		OverwatchedTask overwatchedTask;
		boolean problem;
		do {
			problem = false;
			int[] status = new int[] { OverwatchedTask.STATUS_IN_ARBEIT };
			overwatchedTask = new OverwatchedTask(target, verbesserungsKoeff, startWert, polySeiteLaenge, status,
					client);

			// Berechnung starten:
			overwatchedTask.execute();
			long startTime = 0;

			while (status[0] != OverwatchedTask.STATUS_FERTIG) {

				if (status[0] == OverwatchedTask.STATUS_IN_ARBEIT) {
					status[0] = OverwatchedTask.STATUS_PROBLEM_ABFRAGEN;
					startTime = System.currentTimeMillis();
				}

				if (status[0] == OverwatchedTask.STATUS_PROBLEM_ABFRAGEN
						&& System.currentTimeMillis() - startTime > 10000) {
					problem = true;
					SwingWorkerInfoDatatype info = new SwingWorkerInfoDatatype();
					info.isFehler = true;
					info.isStatus = true;
					info.status = "Abgest�rzt. Unternehme neuen Versuch.";
					client.swingAction(info);
					verbesserungsKoeff*=10;
					break;
				}

				// Eine kleine Verz�gerung muss eingebaut werden, damit alles
				// funktioniert.
				try {
					Thread.sleep(10);
				} catch (Exception e) {
				}
			}
		} while (problem);

		return overwatchedTask.getOptimum();

	}
}
