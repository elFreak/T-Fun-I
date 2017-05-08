
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

	public void setMovingMean(double n) {

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
