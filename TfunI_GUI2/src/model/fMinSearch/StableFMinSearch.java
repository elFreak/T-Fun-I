package model.fMinSearch;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.commons.math3.optim.PointValuePair;
import com.sun.corba.se.impl.orbutil.threadpool.TimeoutException;

import model.Message;
import model.SwingWorkerClient;
import model.Target;

/**
 * Klasse welche einen stabilen FMinSearch für die Berechnung einer
 * Übertragungsfunktion zur Verfügung stellt.
 * 
 * @author Team 1
 *
 */
public class StableFMinSearch {

	// -----------------------------------------------------------------------------------------------------------------
	// Berechnungs Methoden:
	// -----------------------------------------------------------------------------------------------------------------
	/**
	 * Bereichnet eine möglichst genau mit dem Eingagnssignal übereinstimmende
	 * Übertragungsfunktion für genau eine Polstellenordnung.
	 * 
	 * @param target
	 * @param ordnung
	 * @param startValue
	 * @param client
	 * @param accuracy
	 * @param threshold
	 * @return
	 * @throws TimeoutException
	 */
	public static PointValuePair getUtfN(Target target, int ordnung, PointValuePair startValue,
			SwingWorkerClient client, int accuracy, double threshold) throws TimeoutException {

		// Bestimme wie genau die Berechnung sein soll:
		double[] verbesserungsKoeff = new double[]{threshold};
		double[] polySeiteLaenge = new double[ordnung + 1];
		for (int i = 0; i < ordnung + 1; i++) {
			polySeiteLaenge[i] = 0.1;
		}

		PointValuePair koeffizienten = startValue; // nur für den Compiler!

		// Berechne
		for (int i = 0; i <= accuracy; i++) {
			koeffizienten = calculate(target, verbesserungsKoeff, koeffizienten.getPoint(), polySeiteLaenge, client);
			verbesserungsKoeff[0] /= 10;
		}

		return koeffizienten;
	}

	/**
	 * Startet und überwacht die eigentliche Berechnung, welche in einem
	 * überwachten Task stattfindet.
	 * 
	 * @param target
	 * @param verbesserungsKoeff
	 * @param startWert
	 * @param polySeiteLaenge
	 * @param client
	 * @return
	 * @throws TimeoutException
	 */
	private static PointValuePair calculate(Target target, double[] verbesserungsKoeff, double[] startWert,
			double[] polySeiteLaenge, SwingWorkerClient client) throws TimeoutException {
		// Berechnung:
		double newVerbesserungsKoeff = verbesserungsKoeff[0];
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
					status);

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
					verbesserungsKoeff[0] = newVerbesserungsKoeff;
					// verbesserungsKoeff[0] = newVerbesserungsKoeff;
					if (newVerbesserungsKoeff > 1) {
						newVerbesserungsKoeff = 1;
					}
					for (int i = 0; i < startWert.length; i++) {
						newStartWert[i] = startWert[i];
					}
					newTarget.isCanceled = true;
					newTarget = target.copy();
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