import java.util.Observable;

public class MeasurementData extends Observable{
	public static final int XAXIS = 0;
	public static final int MEASUREMENTS = 1;
	public static final int STEP = 2;

	private double rawData[][];
	private double meanData[][];
	private double finalData[][]; // Data for calculation
	
	private double deadTime = 0;
	private double offset = 0;
	private double tail = 0;
	private double stepTime = 0;
	private double stepHeight = 1;
	private double stepData[][];
	private double originalStep[][];
	private double n = 0;

	/**
	 * 
	 * @param data
	 *            data[0] = x-axis(time), data[1] = measurements(measurement
	 *            values), data[3] = step(step response values)
	 * @throws IllegalArgumentException
	 */
	public MeasurementData(double data[][]) throws IllegalArgumentException {
		// check for exceptions
		if (data.length > 3 || data.length < 1 || data[0].length < 1 || data[MEASUREMENTS].length != data[XAXIS].length
				|| data[XAXIS].length != data[STEP].length)
			throw new IllegalArgumentException();

		// update data
		rawData = new double[data.length][data[XAXIS].length];
		meanData = new double[data.length][data[XAXIS].length];
		finalData = new double[data.length][data[XAXIS].length];
		stepData = new double[data.length][data[XAXIS].length];
		originalStep = new double[data.length][data[XAXIS].length];
		for (int i = 0; i < data[XAXIS].length; i++) {
			rawData[XAXIS][i] = data[XAXIS][i];
			meanData[XAXIS][i] = data[XAXIS][i];
			finalData[XAXIS][i] = data[XAXIS][i];
			stepData[XAXIS][i] = data[XAXIS][i];
			originalStep[XAXIS][i] = data[XAXIS][i];

			rawData[MEASUREMENTS][i] = data[MEASUREMENTS][i];
			meanData[MEASUREMENTS][i] = data[MEASUREMENTS][i];
			finalData[MEASUREMENTS][i] = data[MEASUREMENTS][i];

			// Update Step
			if (data.length == 3) {
				stepData[MEASUREMENTS][i] = data[STEP][i];
				originalStep[MEASUREMENTS][i] = data[STEP][i];

				// Calculate step time from step array
				if (data[STEP][i] != 0 && data[STEP][i - 1] == 0) {
					stepTime = data[XAXIS][i - 1];
					stepHeight = data[STEP][i];
				}

			} else {
				stepData[MEASUREMENTS][i] = 1;
				originalStep[MEASUREMENTS][i] = 1;

			}

		}
		
		super.hasChanged();
		super.notifyObservers();

	}


	/**
	 * Methode um mit dem Mittelwert zu filtern. Mit dem Parameter n kann man bestimmen
	 * über wie viele Werte gemittelt wird.
	 * @param n
	 */
	public void setMovingMean(int n) {
		for (int i = 0; i < rawData[MEASUREMENTS].length; i++) {
			int count = 0;
			for (int j = i-n; j <= i+n; j++) {
				if (j >= 0 && j < rawData[MEASUREMENTS].length) {
					meanData[1][i]+= rawData[MEASUREMENTS][j];
					count=count+1;
				}
			}
			meanData[MEASUREMENTS][i] /= count;
		}
		
		super.hasChanged();
		super.notifyObservers();

	}

	/**
	 * sets the respective limits
	 * 
	 * @param deadTime
	 * @param offset
	 * @param tail
	 */
	public void setLimits(double deadTime, double offset, double tail) {
		this.deadTime = deadTime;
		this.offset = offset;
		this.tail = tail;
		
		super.hasChanged();
		super.notifyObservers();
	}
	 

	/**
	 * Sets the dead time, offset, tail automatically
	 */
	public void autoLimits() {
		cutFront(rawData, 20, 0.2);
		cutTail(rawData, 10, 0.002);
		
		super.hasChanged();
		super.notifyObservers();
	}

	/**
	 * 
	 * @param y
	 * @param n
	 * @param q
	 * @return
	 */
	private double[][] cutFront(double y [][], int n, double q){
		double m = meanData[MEASUREMENTS][0];
		int c = 1;
		while (Math.abs((y[MEASUREMENTS][c]-m)/y[MEASUREMENTS][c]) < q && y.length >= c+n) {
			m=0;
			for (int i = 0; i < n; i++) {
				m +=y[MEASUREMENTS][c+n];	
			}
			m /= n;
			c++;	
		}
		c=c-1;
		for (int i = 0; i < y.length-c; i++) {
			y[1][i]=y[1][i+c];
		}
		for (int i = 0; i < y.length-c; i++) {
			y[0][i]=y[0][i+c];
		}
		return y;
	}
	/**
	 * 
	 * @param y
	 * @param n
	 * @param q
	 * @return
	 */
	private double[][] cutTail(double y [][], int n, double q){
		double m = meanData[MEASUREMENTS][meanData.length-1];
		int c = 1;
		while (Math.abs((y[MEASUREMENTS][meanData.length-1-c]-m)/y[MEASUREMENTS][meanData.length-1-c]) < q && y.length >= c+n) {
			m=0;
			for (int i = 0; i < n; i++) {
				m +=y[MEASUREMENTS][meanData.length-1-c-n];	
			}
			m /= n;
			c++;	
		}
		c=c-1;
		for (int i = 0; i < y.length-c; i++) {
			y[1][i]=y[1][i];
		}
		for (int i = 0; i < y.length-c; i++) {
			y[0][i]=y[0][i];
		}
		return y;
	}
	
	/**
	 * 
	 * @param stepTime
	 */
	public void setStepTime(double stepTime) {
		this.stepTime = stepTime;
		for (int i = 0; i < rawData[XAXIS].length; i++) {
			if (rawData[XAXIS][i] < stepTime) {
				stepData[MEASUREMENTS][i] = 0;	
			}else{
				stepData[MEASUREMENTS][i] = stepHeight;	
			}	
		}
		
		super.hasChanged();
		super.notifyObservers();
	}
	/**
	 * 
	 * @param stepHeight
	 */
	public void setStepHeight(double stepHeight) {
		this.stepHeight = stepHeight;
		for (int i = 0; i < stepData[MEASUREMENTS].length; i++) {
			if (stepData[MEASUREMENTS][i] != 0) {
				stepData[MEASUREMENTS][i] = stepHeight;	
			}	
		}
		
		super.hasChanged();
		super.notifyObservers();

	}

	/**
	 * sets the step back to the initial time. If there wasn't an initial time
	 * the step will be set at t = 0;
	 */
	public void setOriginalStep() {
		for (int i = 0; i < stepData[MEASUREMENTS].length; i++) {
			stepData[MEASUREMENTS][i]=originalStep[MEASUREMENTS][i];
		}
		
		super.hasChanged();
		super.notifyObservers();
	}

	/**
	 * 
	 * @return The raw data; rawData[0][]: Measurement time (t), rawData[1][]:
	 *         measurement values (y)
	 */
	public double[][] getRawData() {
		return rawData;
	}

	/**
	 * 
	 * @return The data ready for the calculations finalData[0][]: Measurement
	 *         time (t), finalData[1][]: measurement values (y)
	 */
	public double[][] getFinalData() {
		return finalData;
	}

	/**
	 * 
	 * @return the moving average of the raw data meanData[0][]: Measurement
	 *         time (t), meanData[1][]: averaged measurement values (y)
	 */
	public double[][] getMeanData() {
		return meanData;
	}

	/**
	 * 
	 * @return dead time
	 */
	public double getDeadTime() {
		return deadTime;
	}

	/**
	 * 
	 * @return offset
	 */
	public double getOffset() {
		return offset;
	}

	/**
	 * 
	 * @return The uninteresting end/tail
	 */
	public double getTail() {
		return tail;
	}

	/**
	 * 
	 * @return step[0][]: Measurement time (t), step[1][]: Step value (s)
	 */
	public double[][] getstep() {
		return stepData;
	}

	/**
	 * 
	 * @return step time
	 */
	public double getstepTime() {
		return stepTime;
	}

	/**
	 * 
	 * @return step height
	 */
	public double getstepHeight() {
		return stepHeight;
	}

}
