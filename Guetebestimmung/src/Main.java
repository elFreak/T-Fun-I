import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.StringTokenizer;
import java.awt.Frame;

public class Main {
	public static void main(String args[]) {
		double[] vektor_1 = { 2, 7, 6, 9, 55 };
		double[] vektor_2 = { 2, 55, 4, 25, 8 };
		double[][] vek = new double [][]{{2, 7, 6, 9, 55},{2, 55, 4, 25, 8}};
		double[] hans=vek[0];
		
		System.out.println(vek[0].length);
		System.out.println("Korrelation: " + korrKoeff(vektor_1, vektor_2));
		System.out.println("relative Abweichung: " + relAbweichung(vektor_1, vektor_2));
		System.out.println("potenzierte Fehlersumme: " + potenzFehler(vektor_1, vektor_2,3));
	}

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

	public static double[] fehlerPlotData(double[] soll, double[] ist) {
		double[] errorData = new double[soll.length];
		for (int i = 0; i < soll.length; i++) {
			errorData[i] = (ist[i]-soll[i])/soll[i]*100.0;
		}
		return errorData;
	}
	
	public static double relAbweichung(double[] soll, double[] ist) {
		double abweichung=0;
		for (int i = 0; i < soll.length; i++) {
			abweichung=abweichung+(Math.abs(soll[i]-ist[i])/soll[i]);
		}
		abweichung=abweichung*100.0/soll.length;
		return abweichung;
	}
	
	public static double potenzFehler(double[] soll, double[] ist, int potenz) {
		double fehlerSumme=0;
		for (int i = 0; i < soll.length; i++) {
			fehlerSumme=fehlerSumme+Math.pow(Math.abs(soll[i]-ist[i]), potenz);
		}
		return fehlerSumme;
	}

	public static double mean(double[] a) {
		double mean = 0;
		for (int i = 0; i < a.length; i++) {
			mean = mean + a[i];
		}
		mean /= a.length;
		return mean;
	}

	public static double norm(double[] a) {
		double norm = 0;
		for (int i = 0; i < a.length; i++) {
			norm = norm + Math.pow(a[i], 2.0);
		}
		norm = Math.sqrt(norm);
		return norm;
	}

}
