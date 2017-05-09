
public class Test {

	
	public static void main(String args[]){
		MeasurementData measurementData = new MeasurementData(new double[][]{
																{1,2,3,4},{4,3,2,1}
																});
		double[][] asdf = measurementData.getFinalData();
		System.out.println(asdf[0][1]);
		asdf[0][1]=5;
		asdf = measurementData.getFinalData();
		System.out.println(asdf[0][1]);


		
	}
}
