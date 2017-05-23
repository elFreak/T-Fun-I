package model;

import org.apache.commons.math3.optim.PointValuePair;

/**
 * Klasse welche einen stabilen FMinSearch für die Berechnung einer Übertragungsfunktion zur Verfügung stellt.
 * 
 * @author Team 1
 *
 */
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
				new_utf[1] = Math.sqrt(Math.sqrt((new_utf[1])));
				new_utf[2] = (new_utf[2]) / 4;
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

			if (i == ordnung) {
				utf = berechneOrdnungN(target, i, utf, client, 2);
			} else {
				utf = berechneOrdnungN(target, i, utf, client, 0);
			}
			

			SwingWorkerInfoDatatype info1 = new SwingWorkerInfoDatatype();
			info1.isStatus = true;
			info1.isFehler = false;
			info1.status = "Ordnung " + i + " wurde berechne.";
			client.swingAction(info1);

		}

		return utf;
	}

	private static PointValuePair berechneOrdnungN(Target target, int ordnung, PointValuePair startValue,
			SwingWorkerClient client, int accuracy) {
		double verbesserungsKoeff = 1e-3;
		double[] polySeiteLaenge = new double[ordnung + 1];

		// Werte initializieren:
		for (int i = 0; i < ordnung + 1; i++) {
			polySeiteLaenge[i] = 0.2;
		}

		switch (ordnung) {
		case 2:
			verbesserungsKoeff /= 1e2;
			break;
		case 3:
			verbesserungsKoeff /= 1e2;
			break;
		case 4:
			verbesserungsKoeff /= 1e4;
			break;
		case 5:
			verbesserungsKoeff /= 1e4;
			break;
		case 6:
			verbesserungsKoeff /= 1e5;
			break;
		case 7:
			verbesserungsKoeff /= 1e5;
			break;
		case 8:
			verbesserungsKoeff /= 1e6;
			break;
		case 9:
			verbesserungsKoeff /= 1e6;
			break;
		case 10:
			verbesserungsKoeff /= 1e7;
			break;

		}

		PointValuePair koeffizienten = startValue; // nur
													// für
													// den
													// Compiler!

		// Berechnen:
		for (int i = 0; i <= accuracy; i++) {
			koeffizienten = berechnen(target, verbesserungsKoeff, koeffizienten.getPoint(), polySeiteLaenge, client);
			verbesserungsKoeff /= 10;
			for(int j=0;j<polySeiteLaenge.length;j++) {
				//polySeiteLaenge[j] /= 1.2;
			}
			SwingWorkerInfoDatatype info = new SwingWorkerInfoDatatype();
			info.isStatus = true;
			info.isFehler = false;
			info.status = "Signal verbessert. Durchgang: "+(i+1)+".";
			client.swingAction(info);
		}

		return koeffizienten;
	}

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
						&& System.currentTimeMillis() - startTime > 3500) {
					overwatchedTask.cancel(true);
					problem = true;
					SwingWorkerInfoDatatype info = new SwingWorkerInfoDatatype();
					info.isFehler = false;
					info.isStatus = true;
					info.status = "Zu langsam!";
					client.swingAction(info);
					verbesserungsKoeff *= 1000;
					break;
				}

				// Eine kleine Verzögerung muss eingebaut werden, damit alles
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
