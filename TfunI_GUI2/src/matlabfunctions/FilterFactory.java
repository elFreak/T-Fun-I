package pro2e.teamX.matlabfunctions;

import org.apache.commons.math3.complex.Complex;

public class FilterFactory {
	// 21

	/**
	 * <pre>
	 * [filter] = BUTTER(N,Wn,'s'), design an Nth order lowpass analog Butterworth filters.
	 * 
	 * - Gemäss Algorithmus in der Aufgabenstellung.
	 *  
	 * </pre>
	 *
	 * @param 	N		Order
	 * @param 	Wg		Cut-off-frequency in rad/s
	 * @return 			Filter - Object
	 */
	public static Filter createButter(int N, double Wg) {
		// 7
		Complex[] p = new Complex[N];

		for (int n = 0; n < p.length; n++) {
			p[n] = new Complex(0.0, (n * Math.PI / N + Math.PI / 2.0 * (1.0 + 1.0 / N))).exp().multiply(Wg);
		}

		double[] A = Matlab.real(Matlab.poly(p));
		double[] B = new double[] { A[A.length - 1] };

		return new Filter(B, A, new Complex[] {}, p);
	}

	/**
	 * <pre>
	 * [filter] = CHEBY1(N,R,Wp,'s') designs an Nth order lowpass analog Chebyshev filter 
	 * with R decibels of peak-to-peak ripple in the passband.
	 * 
	 * - Gemäss Algorithmus in der Aufgabenstellung.
	 * 
	 * </pre>
	 * 
	 * @param N		Order
	 * @param R		Ripple in dB
	 * @param Wg	Cut-off-frequency in rad/s.
	 * @return		Filter - Object
	 */
	public static Filter createCheby1(int N, double R, double Wg) {
		// 14
		Complex[] p = new Complex[N];

		double eps = Math.sqrt(Math.pow(10.0, R / 10.0) - 1.0);
		double sre = Math.sinh((1.0 / N) * Matlab.asinh(1.0 / eps));
		double sim = Math.cosh((1.0 / N) * Matlab.asinh(1.0 / eps));

		for (int n = 0; n < p.length; n++) {
			p[n] = new Complex(0.0, (n * Math.PI / N + Math.PI / 2.0 * (1.0 + 1.0 / N))).exp().multiply(Wg);
			p[n] = new Complex(sre * p[n].getReal(), sim * p[n].getImaginary());
		}

		double[] A = Matlab.real(Matlab.poly(p));
		double[] B;
		if (N % 2 == 0)
			B = new double[] { A[A.length - 1] * Math.sqrt(Math.pow(10.0, -R / 10.0)) };
		else
			B = new double[] { A[A.length - 1] };

		return new Filter(B, A, new Complex[] {}, p);
	}
}
