package model;

import org.apache.commons.math3.optim.PointValuePair;

/**
 * Klasse welche einen stabilen FMinSearch für die Berechnung einer
 * Übertragungsfunktion zur Verfügung stellt.
 * 
 * @author Team 1
 *
 */
public class StableFMinSearch {

	/**
	 * Berechnet eine ungefähr mit dem Eingangssignal übereinstimmende
	 * Übertragungsfunktion für alle Polstellenordnungen von 2-10. Diese Werte
	 * können dann als Startwerte für die genauere Methode .... benutzt werden.
	 * 
	 * @param target
	 * @param client
	 * @return
	 */
	static public PointValuePair[] getStartValues(Target target, SwingWorkerClient client) {
		double[] startWert = new double[] { 1.0, -1.0 };

		// Strucktur für Rückgabewert wird definiert.
		PointValuePair utf[] = new PointValuePair[9]; // Ordnung 2-10 ...
		utf[0] = new PointValuePair(startWert, 0);

		for (int i = 2; i <= 10; i++) {

			// Kopiere Werte von kleineren Ordnungen.
			double[] newUtf = new double[i + 1];
			if (i >= 4) { // Ordnung >= 4
				for (int j = 0; j < utf[i - 4].getPoint().length; j++) {
					newUtf[j] = utf[i - 4].getPoint()[j];
				}
				if (i % 2 == 0) {
					newUtf[1] = Math.sqrt(Math.sqrt((newUtf[1])));
					newUtf[2] = (newUtf[2]) / 4;
					newUtf[newUtf.length - 2] = newUtf[1];
					newUtf[newUtf.length - 1] = newUtf[2];
				} else {
					newUtf[newUtf.length - 1] = newUtf[newUtf.length - 3];
					newUtf[1] = Math.sqrt(Math.sqrt((newUtf[1])));
					newUtf[2] = (newUtf[2]) / 4;
					newUtf[newUtf.length - 3] = newUtf[1];
					newUtf[newUtf.length - 2] = newUtf[2];
				}
			}
			if (i == 2) { // Ordnung = 2
				newUtf[0] = 1.0;
				newUtf[1] = 1.0;
				newUtf[2] = 1.0;
			}
			if (i == 3) { // Ordnung = 3
				newUtf[0] = 1.0;
				newUtf[1] = 1.0;
				newUtf[2] = 1.0;
				newUtf[3] = -1.0;
			}
			utf[i - 2] = new PointValuePair(newUtf, 0);

			// Berechne die Ordnung N:
			utf[i - 2] = getUtfN(target, i, utf[i - 2], client, 0);
			SwingWorkerInfoDatatype info = new SwingWorkerInfoDatatype();
			info.isFehler = false;
			info.isStatus = true;
			info.status = "Startwerte für Ordnung "+i+" berechnet.";
			client.swingAction(info);
			

		}

		return utf;

	}

	/**
	 * Bereichnet eine möglichst genau mit dem Eingagnssignal übereinstimmende
	 * Übertragungsfunktion für genau eine Polstellenordnung.
	 * 
	 * @param target
	 * @param ordnung
	 * @param startValue
	 * @param client
	 * @param accuracy
	 * @return
	 */
	public static PointValuePair getUtfN(Target target, int ordnung, PointValuePair startValue,
			SwingWorkerClient client, int accuracy) {

		// Bestimme wie genau die Berechnung sein soll:
		double verbesserungsKoeff = 1e-3;
		double[] polySeiteLaenge = new double[ordnung + 1];
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
			verbesserungsKoeff /= 5e4;
			break;
		case 7:
			verbesserungsKoeff /= 5e4;
			break;
		case 8:
			verbesserungsKoeff /= 5e4;
			break;
		case 9:
			verbesserungsKoeff /= 5e4;
			break;
		case 10:
			verbesserungsKoeff /= 5e4;
			break;
		}
		PointValuePair koeffizienten = startValue; // nur für den Compiler!

		// Berechne:
		for (int i = 0; i <= accuracy; i++) {
			if(i==0) {
				verbesserungsKoeff*=1000;
			}
			koeffizienten = calculate(target, verbesserungsKoeff, koeffizienten.getPoint(), polySeiteLaenge, client);
			verbesserungsKoeff /= 10;
		}

		return koeffizienten;
	}

	/**
	 * Startet und überwacht die .....
	 * 
	 * @param target
	 * @param verbesserungsKoeff
	 * @param startWert
	 * @param polySeiteLaenge
	 * @param client
	 * @return
	 */
	private static PointValuePair calculate(Target target, double verbesserungsKoeff, double[] startWert,
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
					overwatchedTask.cancel(true);
					problem = true;
					SwingWorkerInfoDatatype info = new SwingWorkerInfoDatatype();
					info.isFehler = false;
					info.isStatus = true;
					info.status = "Zu langsam!";
					client.swingAction(info);
					verbesserungsKoeff *= 100;
					break;
				}

				// Eine kleine Verzögerung muss eingebaut werden, damit dieser
				// Thread nicht unnötig den Prozessor beansprucht.
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
				}
			}
		} while (problem);

		return overwatchedTask.getOptimum();

	}
}