package model;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.SwingWorker;

import org.apache.commons.math3.optim.PointValuePair;

import model.fMinSearch.StableFMinSearch;
import model.fMinSearch.SwingWorkerClient;
import model.fMinSearch.SwingWorkerInfoDatatype;
import userInterface.StatusBar;

public class Network extends SwingWorker<Object, SwingWorkerInfoDatatype> implements SwingWorkerClient {

	/**
	 * Generale Daten:
	 */
	private MeasurementData measurementData;
	private Model model;
	ExecutorService threadExecutor = Executors.newFixedThreadPool(1);

	/**
	 * Normierte Eingangssignale (Sprungantwort aus der Klasse Messwerte):
	 */
	private double[] timeFullNormed;
	private double[] stepFullNormed;
	private double[] timeLenghtNormed;
	private static final int NORM_NUMBER_OF_DATA = 200;

	private PointValuePair[] startValues = new PointValuePair[9];
	private Approximation[] approximations = new Approximation[9];
	public Network(MeasurementData measurementData, Model model) {
		// Setze die Grundlegenden Verknüpfungen der Klasse:
		this.measurementData = measurementData;
		this.model = model;

		threadExecutor.execute(this);
	}

	private void prepareCalculation() {

		// Die anzunähernde Sprungantwort wird normiert um die Berechnung zu
		// vereinfachen:
		double[][] stepResponseMeasurement = scalingStepResponse(measurementData.getFinalData());
		timeFullNormed = stepResponseMeasurement[0];
		stepFullNormed = stepResponseMeasurement[1];
		timeLenghtNormed = stepResponseMeasurement[2];

		// Startwerte werden berechnet:
		Target target = new Target(timeFullNormed, stepFullNormed);
		startValues = StableFMinSearch.getStartValues(target, this);
		SwingWorkerInfoDatatype info = new SwingWorkerInfoDatatype();
		info.statusFehler = false;
		info.isStatus = true;
		info.statusText = "Startwerte berechnet.";
		swingAction(info);
	}

	public void calculateApproximation(int order) {
		if (approximations[order - 2] == null) {
			approximations[order - 2] = new Approximation(startValues[order - 2],
					new Target(timeFullNormed, stepFullNormed), order, timeFullNormed, stepFullNormed, timeLenghtNormed,
					this);
			threadExecutor.execute(approximations[order-2]);
		}
	}

	@Override
	protected Object doInBackground() throws Exception {
		prepareCalculation();
		return 0;
	}

	@Override
	protected void process(List<SwingWorkerInfoDatatype> arg) {
		if (isCancelled() == false) {
			super.process(arg);
			for (int i = 0; i < arg.size(); i++) {
				SwingWorkerInfoDatatype info = arg.get(i);
				if (info.isStatus) {
					if (info.statusFehler) {
						StatusBar.showStatus(info.statusText, StatusBar.FEHLER);
					} else {
						StatusBar.showStatus(info.statusText, StatusBar.INFO);
					}
				}
			}
		}

	}

	@Override
	protected void done() {
		super.done();
		StatusBar.showStatus("Approximation beendet.", StatusBar.INFO);
		model.notifyObservers(Model.NOTIFY_REASON_NETWORK_START_VALUES);
	}

	/**
	 * Normiert die Sprungantwort: 1) Normiert die Zeitachse 2) Normiert die
	 * Anzahl der Messpunkte
	 * 
	 * @param stepResponseOriginal
	 * @return
	 */
	private static double[][] scalingStepResponse(double[][] stepResponseOriginal) {

		// Berechnet den Faktor um die Anzahl der Messpunkte zu normieren:
		int originalLenght = stepResponseOriginal[0].length;
		int factor = originalLenght / NORM_NUMBER_OF_DATA;
		factor = (int) Math.max(1.0, factor);

		// Berechnet den Faktor um die Zeitachse zu normieren:
		double scalefactorTime = Math.log10(stepResponseOriginal[0][stepResponseOriginal[0].length - 1]) - 1;

		// Erzeugt die Strucktur der normierten Daten:
		int newLenght = originalLenght / factor;
		double[][] stepResponseNew = new double[3][newLenght];

		// Erstellt die normierten Daten:
		for (int i = 0; i < newLenght; i++) {
			stepResponseNew[0][i] = stepResponseOriginal[0][i * factor] * Math.pow(10.0, -scalefactorTime); // Zeit
																											// normiert
																											// und
																											// Anzahl
																											// Messwerte
																											// normiert.
			stepResponseNew[1][i] = stepResponseOriginal[1][i * factor]; // Anzahl
																			// Messwerte
																			// normiert.
			stepResponseNew[2][i] = stepResponseOriginal[0][i * factor]; // Anzahl
																			// Messwerte
																			// normiert.
		}

		return stepResponseNew;
	}

	public void approximationDone() {
		if (isCancelled() == false) {
			model.notifyObservers(Model.NOTIFY_REASON_APPROXIMATION_DONE);
		}
	}

	@Override
	public void swingAction(SwingWorkerInfoDatatype info) {
		publish(info);
	}

	/**
	 * Gibt die Übertragungsfunktion der Polstellen-Ordnung 2-10 zurück.
	 * 
	 * @return
	 */
	public Approximation getApprox(int order) {
		return approximations[order - 2];
	}
}