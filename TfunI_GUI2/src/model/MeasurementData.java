package model;

import sun.security.util.Length;

/**
 * 
 * @author Marc de Bever
 *
 */
public class MeasurementData {

	// ---Atribute
	// -------------------------------------------------------------------------------------------------------
	// Konstanten um auf die entsprechenden Daten zuzugreiffen
	public static final int XAXIS = 0;
	public static final int MEASUREMENTS = 1;
	public static final int STEP = 2;

	private Model model;

	private double rawData[][];
	private double meanData[][];
	private double finalData[][]; // Data für weitere Berechnungen

	private double deadTime = 0;
	private double offset = 0;
	private double tail;
	private double stepTime = 0;
	private double originalStepTime = 0;
	private double stepHeight = 1;
	private double originalStepHeight = 1;
	private double stepData[][];
	private double originalStep[][];
	private double n = 0;
	// -------------------------------------------------------------------------------------------------------

	// Konstruktor
	// -------------------------------------------------------------------------------------------------------
	/**
	 * Speichert die übergebenen Daten in die entsprechenden Attribute.
	 * 
	 * @param model
	 * @param data
	 *            data[0] = x-axis(time), data[1] = measurements(measurement
	 *            values), data[3] = step(step response values)
	 * @throws IllegalArgumentException
	 */
	public MeasurementData(Model model, double data[][]) throws IllegalArgumentException {
		this.model = model;

		// check for exceptions
		if (data.length > 3 || data.length < 1 || data[0].length < 1)
			throw new IllegalArgumentException(data.length + " " + data[1].length);

		// update data
		rawData = new double[2][data[XAXIS].length];
		meanData = new double[2][data[XAXIS].length];
		finalData = new double[2][data[XAXIS].length];
		stepData = new double[2][data[XAXIS].length];
		originalStep = new double[2][data[XAXIS].length];
		for (int i = 0; i < data[XAXIS].length; i++) {
			rawData[XAXIS][i] = data[XAXIS][i];
			meanData[XAXIS][i] = data[XAXIS][i];
			finalData[XAXIS][i] = data[XAXIS][i];
			stepData[XAXIS][i] = data[XAXIS][i];
			originalStep[XAXIS][i] = data[XAXIS][i];

			rawData[MEASUREMENTS][i] = data[MEASUREMENTS][i];
			meanData[MEASUREMENTS][i] = data[MEASUREMENTS][i];
			finalData[MEASUREMENTS][i] = data[MEASUREMENTS][i];

			// Sprung berechnen
			// Wenn Sprung voranden
			if (data.length == 3) {
				stepData[MEASUREMENTS][i] = data[STEP][i];
				originalStep[MEASUREMENTS][i] = data[STEP][i];

				// stepTime und stepHeight berechnen
				if(i!=0) {
					if (data[STEP][i] != 0 && data[STEP][i-1] == 0) {
						stepTime = data[XAXIS][i];
						originalStepTime=data[XAXIS][i];
						stepHeight = data[STEP][i];
						originalStepHeight = data[STEP][i];
					}
				}
			}
			// Wenn kein Sprung vorhanden
			else {
				stepData[MEASUREMENTS][i] = 1;
				originalStep[MEASUREMENTS][i] = 1;

			}

		}

	}
	// -------------------------------------------------------------------------------------------------------

	// Set Methoden
	// -------------------------------------------------------------------------------------------------------

	/**
	 * Methode um mit dem Mittelwert zu filtern. Mit dem Parameter n kann man
	 * bestimmen über wie viele Werte gemittelt wird.
	 * 
	 * @param n
	 */
	public void setMovingMean(int n) {
		// Kein Mittelwert bilden
		if (n == 0) {
			for (int i = 0; i < rawData[MEASUREMENTS].length; i++) {
				meanData[MEASUREMENTS][i] = rawData[MEASUREMENTS][i];
			}
		}
		// Mittelwert bilden
		else {
			for (int i = 0; i < rawData[MEASUREMENTS].length; i++) {
				int count = 0;
				meanData[MEASUREMENTS][i] = 0;
				for (int j = i - n; j <= i + n; j++) {
					if (j >= 0 && j < rawData[MEASUREMENTS].length) {
						meanData[MEASUREMENTS][i] += rawData[MEASUREMENTS][j];
						count = count + 1;
					}
				}
				meanData[MEASUREMENTS][i] /= count;
			}
		}

		// finalData aktualisieren
		updateFinalData();

		model.notifyObservers();

	}

	/**
	 * Setzt den Rahmen. Setzt die jeweilige Parameter. Und aktualisiert
	 * finalData
	 * 
	 * @param deadTime
	 * @param offset
	 * @param tail
	 */
	public void setLimits(double deadTime, double offset, double tail) {
		this.deadTime = deadTime;

		this.offset = offset;

		// tail vom hintersten Datenpunkt aus
		if (tail > meanData[XAXIS][meanData[XAXIS].length - 1])
			this.tail = 0;
		else
			this.tail = meanData[XAXIS][meanData[XAXIS].length - 1] - tail;

		// finalData aktualisieren
		updateFinalData();

		model.notifyObservers();
	}

	/**
	 * Setzt die jeweiligen Parameter automatisch. Und aktualisiert die
	 * jeweiligen Daten
	 * 
	 */
	public void autoLimits() {

		// Automatische Erkennung der Totzeit
//		int n = 50;
//
//		double m = meanData[MEASUREMENTS][0];
//		int frontIndex = 1;
//		while (Math.abs((y[MEASUREMENTS][frontIndex] - m) / y[MEASUREMENTS][frontIndex]) < q
//				&& y.length >= frontIndex + n) {
//			m = 0;
//			for (int i = 0; i < n; i++) {
//				m += y[MEASUREMENTS][frontIndex + n];
//			}
//			m /= n;
//			frontIndex++;
//		}
//		frontIndex = frontIndex - 1;
//
//		// Abschneiden
//		for (int i = 0; i < y.length - frontIndex; i++) {
//			y[1][i] = y[1][i + frontIndex];
//		}
//		for (int i = 0; i < y.length - frontIndex; i++) {
//			y[0][i] = y[0][i + frontIndex];
//		}
//		cutTail(rawData, 10, 0.002);

		model.notifyObservers();
	}

	/**
	 * Schneidet den Anfang ab.
	 * 
	 * @param y
	 * @param n
	 * @param q
	 * @return
	 */
	private double[][] cutFront(double y[][], int n, double q) {

		return y;
	}

	/**
	 * Schneidet das Ende ab.
	 * 
	 * @param y
	 * @param n
	 * @param q
	 * @return
	 */
	private double[][] cutTail(double y[][], int n, double q) {
		double m = meanData[MEASUREMENTS][meanData.length - 1];
		int c = 1;
		while (Math.abs((y[MEASUREMENTS][meanData.length - 1 - c] - m) / y[MEASUREMENTS][meanData.length - 1 - c]) < q
				&& y.length >= c + n) {
			m = 0;
			for (int i = 0; i < n; i++) {
				m += y[MEASUREMENTS][meanData.length - 1 - c - n];
			}
			m /= n;
			c++;
		}
		c = c - 1;
		for (int i = 0; i < y.length - c; i++) {
			y[1][i] = y[1][i];
		}
		for (int i = 0; i < y.length - c; i++) {
			y[0][i] = y[0][i];
		}
		return y;
	}

	/**
	 * Erstellt einene neue Schrittantwort mit der gesetzten stepTime.
	 * 
	 * @param stepTime
	 */
	public void setStepTime(double stepTime) {
		this.stepTime = stepTime;

		// stepData aktualisieren
		for (int i = 0; i < rawData[XAXIS].length; i++) {
			if (rawData[XAXIS][i] < stepTime) {
				stepData[MEASUREMENTS][i] = 0;
			} else {
				stepData[MEASUREMENTS][i] = stepHeight;
			}
		}

		model.notifyObservers();
	}

	/**
	 * Erstellt einene neue Schrittantwort mit der gesetzten stepHeight.
	 * 
	 * @param stepHeight
	 */
	public void setStepHeight(double stepHeight) {
		this.stepHeight = stepHeight;

		// stepData aktualisieren
		for (int i = 0; i < stepData[MEASUREMENTS].length; i++) {
			if (stepData[MEASUREMENTS][i] != 0) {
				stepData[MEASUREMENTS][i] = stepHeight;
			}
		}

		model.notifyObservers();

	}

	/**
	 * Setzt den Schritt zurück mit der Anfangszeit. Wenn keine Anfangszeit
	 * vorhanden ist wird der Schrit mit t = 0 gesetzt.
	 * 
	 */
	public void setOriginalStep() {
		for (int i = 0; i < stepData[MEASUREMENTS].length; i++) {
			stepData[MEASUREMENTS][i] = originalStep[MEASUREMENTS][i];
		}
		stepHeight = originalStepHeight;
		stepTime = originalStepTime;

		model.notifyObservers();
	}

	/**
	 * Aktualisiert finalData aus den gemittelten Daten und dem Rahmen.
	 */
	private void updateFinalData() {
		int frontIndex = 0;
		int tailIndex = 0;
		// Indexe der Zeiten berechnen
		for (int i = 0; i < meanData[XAXIS].length; i++) {
			// Index der Totzeit + stepTime bestimmen
			if (meanData[XAXIS][i] < stepTime + deadTime) {
				frontIndex++;
			}
			// Index des Tails bestimmen
			if (meanData[XAXIS][i] < meanData[XAXIS][meanData[XAXIS].length - 1] - tail) {
				tailIndex++;
			}
		}
		// finalData aktualisieren
		finalData = new double[meanData.length][tailIndex - frontIndex + 1];
		for (int i = 0; i < finalData[XAXIS].length; i++) {
			finalData[XAXIS][i] = meanData[XAXIS][i + frontIndex] - meanData[XAXIS][frontIndex];
			finalData[MEASUREMENTS][i] = meanData[MEASUREMENTS][i + frontIndex] - offset;
		}

	}
	// -------------------------------------------------------------------------------------------------------

	// Get Methoden
	// -------------------------------------------------------------------------------------------------------
	/**
	 * Gibt die Rohdaten zurück.
	 * 
	 * @return The raw data; rawData[0][]: Measurement time (t), rawData[1][]:
	 *         measurement values (y)
	 */
	public double[][] getRawData() {
		double rawData[][] = new double[this.rawData.length][this.rawData[XAXIS].length];
		for (int i = 0; i < rawData[0].length; i++) {
			rawData[XAXIS][i] = this.rawData[XAXIS][i];
			rawData[MEASUREMENTS][i] = this.rawData[MEASUREMENTS][i];

		}
		return rawData;
	}

	/**
	 * Gibt die Finalen daten zurück.
	 * 
	 * @return The data ready for the calculations finalData[0][]: Measurement
	 *         time (t), finalData[1][]: measurement values (y)
	 */
	public double[][] getFinalData() {
		double finalData[][] = new double[this.finalData.length][this.finalData[XAXIS].length];
		for (int i = 0; i < finalData[0].length; i++) {
			finalData[XAXIS][i] = this.finalData[XAXIS][i];
			finalData[MEASUREMENTS][i] = this.finalData[MEASUREMENTS][i];
		}

		return finalData;
	}

	/**
	 * Gibt die gefilterten Daten zurück.
	 * 
	 * @return the moving average of the raw data meanData[0][]: Measurement
	 *         time (t), meanData[1][]: averaged measurement values (y)
	 */
	public double[][] getMeanData() {
		double meanData[][] = new double[this.meanData.length][this.meanData[XAXIS].length];
		for (int i = 0; i < meanData[0].length; i++) {
			meanData[XAXIS][i] = this.meanData[XAXIS][i];
			meanData[MEASUREMENTS][i] = this.meanData[MEASUREMENTS][i];
		}
		return meanData;
	}

	/**
	 * Gibt die Totzeit zurück.
	 * 
	 * @return deadTime
	 */
	public double getDeadTime() {
		return deadTime;
	}

	/**
	 * Gibt die Offset zurück.
	 * 
	 * @return offset
	 */
	public double getOffset() {
		return offset;
	}

	/**
	 * Gibt die länge des uninteresanten Endes zurück.
	 * 
	 * @return tail
	 */
	public double getTail() {
		return meanData[XAXIS][meanData[XAXIS].length - 1]-tail;
	}

	/**
	 * Gibt den Schritt zurück.
	 * 
	 * @return step[0][]: Measurement time (t), step[1][]: Step value (s)
	 */
	public double[][] getstep() {
		double stepData[][] = new double[this.stepData.length][this.stepData[XAXIS].length];
		for (int i = 0; i < stepData[0].length; i++) {
			stepData[XAXIS][i] = this.stepData[XAXIS][i];
			stepData[MEASUREMENTS][i] = this.stepData[MEASUREMENTS][i];
		}
		return stepData;
	}

	/**
	 * Gibt den Schrittzeitpunkt zurück.
	 * 
	 * @return step time
	 */
	public double getstepTime() {
		return stepTime;
	}

	/**
	 * Gibt die Schritthöhe zurück.
	 * 
	 * @return step height
	 */
	public double getstepHeight() {
		return stepHeight;
	}
	// -------------------------------------------------------------------------------------------------------

}
