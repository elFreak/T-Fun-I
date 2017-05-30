package model;

/**
 * Klasse Korrelation:
 * 
 * @author Team 1
 *
 */
public class Korrelation {

	/**
	 * Dient dem Berechnen des Korrelations-Koeffizienten von zwei Vektoren.
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static double korrKoeff(double[] a, double[] b) {

		double mean_a = mean(a);
		double mean_b = mean(b);
		double norm_a = norm(a);
		double norm_b = norm(b);
		double[] ass = new double[a.length];
		double[] bss = new double[b.length];
		for (int i = 0; i < a.length; i++) {
			ass[i] = (a[i] - mean_a) / norm_a;
			bss[i] = (b[i] - mean_b) / norm_b;
		}
		double norm_ass = norm(ass);
		double norm_bss = norm(bss);
		double coeff = 0;
		for (int i = 0; i < a.length; i++) {
			coeff = coeff + ((ass[i] * bss[i]) / (norm_ass * norm_bss));
		}
		return coeff;
	}

	private static double mean(double[] a) {
		double mean = 0;
		for (int i = 0; i < a.length; i++) {
			mean = mean + a[i];
		}
		mean /= a.length;
		return mean;
	}

	private static double norm(double[] a) {
		double norm = 0;
		for (int i = 0; i < a.length; i++) {
			norm = norm + Math.pow(a[i], 2.0);
		}
		norm = Math.sqrt(norm);
		return norm;
	}

}
