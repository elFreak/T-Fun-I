package model.fMinSearch;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.math3.optim.PointValuePair;

import com.sun.corba.se.impl.orbutil.threadpool.TimeoutException;

import model.Target;
import model.UTFDatatype;

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
	static public PointValuePair[] getStartValues(Target target, SwingWorkerClient client, double threshold) {
		double[] startWert = new double[] { 1.0, -1.0 };

		// Strucktur für Rückgabewert wird definiert.
		PointValuePair utf[] = new PointValuePair[10]; // Ordnung 1-10 ...
		utf[0] = new PointValuePair(startWert, 0);

		try {
			for (int i = 1; i <= 10; i++) {

				// Kopiere Werte von kleineren Ordnungen.
				double[] newUtf = new double[i + 1];
				if (i >= 4) { // Ordnung >= 4
					for (int j = 0; j < utf[i - 3].getPoint().length; j++) {
						newUtf[j] = utf[i - 3].getPoint()[j];
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
				} else {
					if (i == 1) { // Ordnung = 1
						newUtf[0] = 1.0;
						newUtf[1] = -1.0;
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
				}
				utf[i - 1] = new PointValuePair(newUtf, 0);

				// Berechne die Ordnung N:
				utf[i - 1] = getUtfN(target, i, utf[i - 1], client, 0, threshold);
				client.swingAction(new Message("Startwerte berechnet (Ordnung " + i + ").", false));

				// // Dazugehörige Sprungantwort berechnen:
				// double[][] stepResponse = new double[][] { target.getTime(),
				// Target.omega2polstep(utf[i - 1].getPoint(), target.getTime())
				// };
				// Uebertragungsfuktion umschreiben:
				UTFDatatype uebergabe = new UTFDatatype();
				uebergabe.ordnung = i;
				uebergabe.zaehler = utf[i - 1].getPoint()[0];
				if (utf[i - 1].getPoint().length % 2 == 1) {
					uebergabe.koeffWQ = new double[utf[i - 1].getPoint().length - 1];

				} else {
					uebergabe.koeffWQ = new double[utf[i - 1].getPoint().length - 2];
					uebergabe.sigma = utf[i - 1].getPoint()[utf[i - 1].getPoint().length - 1];
				}
				for (int j = 0; j < uebergabe.koeffWQ.length; j++) {
					uebergabe.koeffWQ[j] = utf[i - 1].getPoint()[j + 1];
				}
			}
			client.swingAction(new Message("Alle Startwerte wurden berechnet.", false));

		} catch (TimeoutException e) {
			client.swingAction(new Message(
					"Probleme bei der Berechnung (Startwerte).\nVersuchen Sie folgendes:\n1) Versichern Sie sich, dass die Messwerte korekt bearbeited wurden.\n2) Passen Sie den Threshold an und starten Sie dann die Berechnung neu.",
					true));
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
			SwingWorkerClient client, int accuracy, double threshold) throws TimeoutException {

		// Bestimme wie genau die Berechnung sein soll:
		double verbesserungsKoeff = threshold;
		double[] polySeiteLaenge = new double[ordnung + 1];
		for (int i = 0; i < ordnung + 1; i++) {
			polySeiteLaenge[i] = 0.1;
		}

		PointValuePair koeffizienten = startValue; // nur für den Compiler!

		// Berechne

		for (int i = 0; i <= accuracy; i++) {
			koeffizienten = calculate(target, verbesserungsKoeff, koeffizienten.getPoint(), polySeiteLaenge, client);
			// overwatchedVerbesserungskoeff[0] /= 10;
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
	 * @throws TimeoutException
	 */
	private static PointValuePair calculate(Target target, double verbesserungsKoeff, double[] startWert,
			double[] polySeiteLaenge, SwingWorkerClient client) throws TimeoutException {
		// Berechnung:
		double newVerbesserungsKoeff = verbesserungsKoeff;
		double[] newStartWert = new double[startWert.length];
		Target newTarget = target.copy();
		for (int i = 0; i < startWert.length; i++) {
			newStartWert[i] = startWert[i];
		}
		OverwatchedTask overwatchedTask;
		boolean problem;
		int problemZaeler = 0;
		ExecutorService threadExecutor = Executors.newFixedThreadPool(1);

		do {
			problemZaeler++;

			if (problemZaeler > 5) {
				throw (new TimeoutException());
			}

			problem = false;
			int[] status = new int[] { OverwatchedTask.STATUS_IN_ARBEIT };
			overwatchedTask = new OverwatchedTask(newTarget, newVerbesserungsKoeff, newStartWert, polySeiteLaenge,
					status, client);

			// Berechnung starten:
			threadExecutor.execute(overwatchedTask);
			long startTime = 0;

			while (status[0] != OverwatchedTask.STATUS_FERTIG) {

				if (status[0] == OverwatchedTask.STATUS_IN_ARBEIT) {
					status[0] = OverwatchedTask.STATUS_PROBLEM_ABFRAGEN;
					startTime = System.currentTimeMillis();
				}

				if (status[0] == OverwatchedTask.STATUS_PROBLEM_ABFRAGEN
						&& System.currentTimeMillis() - startTime > 2500) {
					overwatchedTask.cancel(true);
					// threadExecutor.shutdownNow();
					// threadExecutor = Executors.newFixedThreadPool(1);
					problem = true;
					client.swingAction(new Message("bitte warten ...", false));
					newVerbesserungsKoeff = newVerbesserungsKoeff * 100;
					// verbesserungsKoeff[0] = newVerbesserungsKoeff;
					if (newVerbesserungsKoeff > 1) {
						newVerbesserungsKoeff = 1;
					}
					for (int i = 0; i < startWert.length; i++) {
						newStartWert[i] = startWert[i];
					}
					newTarget.isCanceled = true;
					newTarget = target.copy();
					System.out.println("" + newVerbesserungsKoeff);
					break;
				}

				// Eine kleine Verzögerung muss eingebaut werden, damit dieser
				// Thread nicht unnötig den Prozessor beansprucht.
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
				}
			}
		} while (problem);

		return overwatchedTask.getOptimum();

	}
}