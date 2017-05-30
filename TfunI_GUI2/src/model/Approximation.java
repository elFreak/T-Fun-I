package model;

import java.util.List;
import javax.swing.SwingWorker;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.optim.PointValuePair;

import com.sun.corba.se.impl.orbutil.threadpool.TimeoutException;

import matlabfunction.Matlab;
import matlabfunction.SVTools;
import model.fMinSearch.Message;
import model.fMinSearch.StableFMinSearch;
import model.fMinSearch.SwingWorkerClient;
import userInterface.StatusBar;

public class Approximation extends SwingWorker<Object, Message> implements SwingWorkerClient {

	/**
	 * Grundlegende Verknüpfungen
	 */
	Network network;

	/**
	 * Zu optimierendes System:
	 */
	private Target target;
	private int order;
	double[] timeFullNormed;
	double[] stepFullNormed;
	double[] timeLenghtNormed;
	private double threshold;

	/**
	 * Startwerte:
	 */
	private PointValuePair startValues;

	/**
	 * Schrittantwort, Polstellen und Übertragungsfunktion:
	 */
	private UTFDatatype utf = new UTFDatatype();
	private double[][] stepResponse;
	private PointValuePair[] pole = new PointValuePair[2];
	private double korrKoef = 0;

	public Approximation(PointValuePair startValues, Target target, int order, double[] timeFullNormed,
			double[] stepFullNormed, double[] timeLenghtNormed, Network network, double threshold) {
		// Setze die Grundlegenden Verknüpfungen der Klasse:
		this.startValues = startValues;
		this.target = target;
		this.order = order;
		this.timeFullNormed = timeFullNormed;
		this.stepFullNormed = stepFullNormed;
		this.timeLenghtNormed = timeLenghtNormed;
		this.network = network;
		this.threshold = threshold;
	}

	private void calculate() throws TimeoutException {

		// Berechnet aus den vorher berechneten Startwerten eine möglichst
		// genaue Übertragungsfunktion.
		PointValuePair optimum = StableFMinSearch.getUtfN(target, order, startValues, this, 3, threshold);
		utf = new UTFDatatype();
		utf.ordnung = order;
		utf.zaehler = optimum.getPoint()[0];
		if (optimum.getPoint().length % 2 == 1) {
			utf.koeffWQ = new double[optimum.getPoint().length - 1];

		} else {
			utf.koeffWQ = new double[optimum.getPoint().length - 2];
			utf.sigma = optimum.getPoint()[optimum.getPoint().length - 1];
		}
		for (int i = 0; i < utf.koeffWQ.length; i++) {
			utf.koeffWQ[i] = optimum.getPoint()[i + 1];
		}

		// Dazugehörige Sprungantwort berechnen:
		stepResponse = new double[][] { timeLenghtNormed, Target.omega2polstep(optimum.getPoint(), timeFullNormed) };

		// Dazugehörige Polstellen berechnen:
		double[] nenner1 = new double[utf.ordnung + 1];
		double[] nenner2 = new double[utf.ordnung + 1];
		if (utf.ordnung != 1) {
			if (utf.ordnung % 2 == 0) {
				nenner1 = new double[] { 1, utf.koeffWQ[0] / utf.koeffWQ[1], Math.pow(utf.koeffWQ[0], 2.0) };
				for (int i = 2; i < utf.ordnung - 1; i += 2) {
					nenner2 = new double[] { 1, utf.koeffWQ[i] / utf.koeffWQ[i + 1], Math.pow(utf.koeffWQ[i], 2.0) };
					nenner1 = Matlab.conv(nenner1, nenner2);
				}
			} else {
				double[] koeffAll = new double[utf.ordnung];
				for (int l = 0; l < utf.koeffWQ.length; l++) {
					koeffAll[l] = utf.koeffWQ[l];
				}
				koeffAll[koeffAll.length - 1] = utf.sigma;

				nenner1 = new double[] { 1, utf.koeffWQ[0] / utf.koeffWQ[1], Math.pow(utf.koeffWQ[0], 2.0) };
				for (int i = 2; i < utf.ordnung - 1; i += 2) {
					nenner2 = new double[] { 1, utf.koeffWQ[i] / utf.koeffWQ[i + 1], Math.pow(utf.koeffWQ[i], 2.0) };
					nenner1 = Matlab.conv(nenner1, nenner2);
				}
				nenner1 = Matlab.conv(new double[] { 1, -utf.sigma }, nenner1);
			}
		} else {
			nenner1 = new double[] { 1, -utf.sigma };
		}
		Complex[] roots = new Complex[utf.ordnung];
		roots = Matlab.roots(nenner1);

		// Den Wert der Polstellen in die entsprechende Variable schreiben:
		double[] real = new double[order];
		double[] imag = new double[order];
		for (int i = 0; i < utf.ordnung; i++) {
			real[i] = roots[i].getReal();
			imag[i] = roots[i].getImaginary();
		}
		pole[0] = new PointValuePair(real, 0);
		pole[1] = new PointValuePair(imag, 0);

		// Den Korelationskoeffizienten berechnen:
		korrKoef = Korrelation.korrKoeff(stepFullNormed, stepResponse[1]);

		// Den Benutzer informieren:
		swingAction(new Message("Berechnung abgeschlossen (Ordnung " + order + ").", false));

	}

	@Override
	protected Object doInBackground() throws Exception {
		swingAction(new Message("Berechnung gestarted (Ordnung " + order + ").", false));
		try {
			calculate();
		} catch (TimeoutException e) {
			swingAction(new Message(
					"Probleme bei der Berechnung (Ordnung " + order
							+ ").\nVersuchen Sie folgendes:\n1) Versichern Sie sich, dass die Messwerte korekt bearbeited wurden.\n2) Passen Sie den Threshold an und starten Sie dann die Berechnung neu.",
					true));
		}
		return 0;
	}

	@Override
	protected void process(List<Message> arg) {
		if (isCancelled() == false) {
			super.process(arg);
			for (int i = 0; i < arg.size(); i++) {
				Message message = arg.get(i);

				if (message.isError) {
					StatusBar.showStatus(message.message, StatusBar.FEHLER);
				} else {
					StatusBar.showStatus(message.message, StatusBar.INFO);
				}

			}
		}
	}

	@Override
	protected void done() {
		super.done();
		network.approximationDone();
	}

	@Override
	public void swingAction(Message info) {
		publish(info);
	}

	public double[][] getStepResponse() {
		return stepResponse;
	}

	public UTFDatatype getUtf() {
		return utf;
	}

	public PointValuePair[] getPole() {
		return pole;
	}

	public double getKorrKoef() {
		return korrKoef;
	}

	public void setUtf(UTFDatatype utf) {
		this.utf = utf;
		network.approximationDone();
	}

}