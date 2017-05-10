package pro2e.teamX.matlabfunctions;

import org.apache.commons.math3.complex.Complex;

public class Filter {
	public double[] B, A;
	public Complex[] rB, rA;

	public Filter(double[] B, double[] A, Complex[] rB, Complex[] rA) {
		this.B = B;
		this.A = A;
		this.rB = rB;
		this.rA = rA;
	}

	@Override
	public String toString() {
		String s = "";

		s += "Nominator: ";
		for (int i = 0; i < B.length; i++) {
			s += " " + B[i] + ",";
		}

		s += "\nDenominator: ";
		for (int i = 0; i < A.length; i++) {
			s += " " + A[i] + ",";
		}

		return s;
	}

}
