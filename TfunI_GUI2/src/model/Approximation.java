package model;

import java.util.List;
import javax.swing.SwingWorker;
import org.apache.commons.math3.optim.PointValuePair;
import userInterface.StatusBar;

public class Approximation extends SwingWorker<Object, SwingWorkerInfoDatatype> implements SwingWorkerClient {

	/**
	 * General Data:
	 */
	private MeasurementData measurementData;
	private Model model;
	private double[][] solutionSignal;
	private double[][] zwischenSignal;
	private double[] timeNormed;
	private double[] stepNormed;

	/**
	 * Erstellt ein Objekt welches in der Lage ist eine Übertragungsfunktion zu
	 * berechnen. Die Berechnung wird in einem eigenen Task durchgeführt. Die
	 * Übertragungsfunktion wird so berechnet, dass sie möglichst genau mit den
	 * Messdaten übereitstimmt. Ein Objekt dieser Klasse kann nur eine
	 * Übertragungsfunktion von genau einer Ordnung (Polstellen-Ordnung)
	 * berechnen.
	 * 
	 * @param measurementData
	 * @param model
	 */
	public Approximation(MeasurementData measurementData, Model model) {
		this.measurementData = measurementData;
		this.model = model;
	}

	/**
	 * Eigentliche Berechnungsmethode.
	 */
	private void berechne() {

		// Die Zeitachse der Messwerte wird normiert um die Berechnung zu
		// vereinfachen.
		timeNormed = scalingTime(measurementData.getFinalData())[0];
		stepNormed = scalingTime(measurementData.getFinalData())[1];

		// Die Übertragungsfunktion und die Zusammenhänge mit den Messwerten
		// werden definiert.
		Target target = new Target(timeNormed, measurementData.getFinalData()[1]);

		PointValuePair optimum = StableFMinSearch.fminsearch(target, 10, this);
		// System.out.println(""+optimum.getPoint()[0]+optimum.getPoint()[1]+optimum.getPoint()[2]+optimum.getPoint()[3]+optimum.getPoint()[4]);

		solutionSignal = new double[][] { measurementData.getFinalData()[0],
				Target.omega2polstep(optimum.getPoint(), timeNormed) };
	}

	@Override
	protected Object doInBackground() {
		berechne();
		return 0;
	}

	@Override
	protected void process(List<SwingWorkerInfoDatatype> arg) {
		super.process(arg);
		for(int i=0;i<arg.size();i++){
			SwingWorkerInfoDatatype info = arg.get(i);
			if (info.isStatus) {
				if (info.isFehler) {
					StatusBar.showStatus(info.status, StatusBar.FEHLER);
				} else {
					StatusBar.showStatus(info.status, StatusBar.INFO);
				}
				
			}
			if (info.isUtfActuallised) {
				zwischenSignal = (new double[][] { measurementData.getFinalData()[0],
						Target.omega2polstep(info.utfKoeff, timeNormed) });
				model.notifyObservers(Model.NOTIFY_REASON_APPROXIMATION_ZWISCHENWERT);
				
			}
		}

	}

	@Override
	protected void done() {
		super.done();
		StatusBar.showStatus("Approximation beendet.", StatusBar.INFO);
		model.notifyObservers(Model.NOTIFY_REASON_APPROXIMATION_DONE);
	}

	/**
	 * Normiert die Zeitachse des gegebenen Arrays.
	 * 
	 * @param step_response_m
	 * @return
	 */
	private static double[][] scalingTime(double[][] step_response_m) {
		double[] t_old = step_response_m[0];
		double scalefactorTime = Math.log10(t_old[t_old.length - 1]) - 1;
		double[][] step_response_scaled_m = new double[2][step_response_m[0].length];
		double[] t_scaled = new double[t_old.length];

		for (int i = 0; i < t_old.length; i++) {
			t_scaled[i] = t_old[i] * Math.pow(10.0, -scalefactorTime);
			step_response_scaled_m[0][i] = t_scaled[i];
		}
		return step_response_scaled_m;
	}

	public double[][] getSolutionSignal() {
		return solutionSignal;
	}

	public void setSolutionSignal(double[][] solutionSignal) {
		this.solutionSignal = solutionSignal;
	}

	@Override
	public void swingAction(SwingWorkerInfoDatatype info) {
		publish(info);
	}

	public double[][] getZwischenSignal() {
		return zwischenSignal;
	}

}
