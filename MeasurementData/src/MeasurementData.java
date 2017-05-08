
public class MeasurementData {
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
			rawData[0][i] = data[XAXIS][i];
			meanData[0][i] = data[XAXIS][i];
			finalData[0][i] = data[XAXIS][i];
			stepData[0][i] = data[XAXIS][i];
			originalStep[0][i] = data[XAXIS][i];

			rawData[1][i] = data[MEASUREMENTS][i];
			meanData[1][i] = data[MEASUREMENTS][i];
			finalData[1][i] = data[MEASUREMENTS][i];

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

	}

	/**
	 * 
	 * @param n
	 */
	public void setMovingMean(double n) {

	}

	/**
	 * sets the respective limits
	 * 
	 * @param deadTime
	 * @param offset
	 * @param tail
	 */
	public void setLimits(double deadTime, double offset, double tail) {

	}

	/**
	 * Sets the dead time, offset, tail automatically
	 */
	public void autoLimits() {

	}

	/**
	 * 
	 * @param stepTime
	 */
	public void setStepTime(double stepTime) {

	}

	/**
	 * 
	 * @param stepHeight
	 */
	public void setStepHeight(double stepHeight) {

	}

	/**
	 * sets the step back to the initial time. If there wasn't an initial time
	 * the step will be set at t = 0;
	 */
	public void setOriginalStep() {

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
