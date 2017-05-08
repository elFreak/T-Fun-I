
public class MeasurementData {
//	hey
	private double rawData[][];
	private double meanData[][];
	private double finalData[][];

	private double deadTime;
	private double offset;
	private double tail;
	private double stepTime;
	private double stepHeight;
	private double step[][];
	private double originalStep[][];
	private double n;

	public MeasurementData(double data[][][]) {

	}
	/**
	 * Methode um mit dem Mittelwert zu filtern. Mit dem Parameter n kann man bestimmen
	 * über wie viele Werte gemittelt wird.
	 * @param n
	 */
	public void setMovingMean(int n) {
		for (int i = 0; i < rawData[1].length; i++) {
			int count = 0;
			for (int j = i-n; j <= i+n; j++) {
				if (j > 0 && j <= rawData[1].length) {
					rawData[1][i]+= rawData[1][j];
					count=count+1;
				}	
			}
			rawData[1][i] /= count;
		}

	}

	public void setLimits(double deadTime, double offset, double tail) {

	}

	public void autoLimits() {

	}

	public void setStepTime(double stepTime) {

	}

	public void setStepHeight(double stepHeight) {

	}
	
	public void setOriginalStep() {

	}

	public double[][] getRawData() {
		return rawData;
	}

	public double[][] getFinalData() {
		return finalData;
	}

	public double[][] getMeanData() {
		return meanData;
	}

	public double getDeadTime() {
		return deadTime;
	}

	public double getOffset() {
		return offset;
	}

	public double getTail() {
		return tail;
	}

	public double[][] getstep() {
		return step;
	}

	public double getstepTime() {
		return stepTime;
	}

	public double getstepHeight() {
		return stepHeight;
	}

	

}
