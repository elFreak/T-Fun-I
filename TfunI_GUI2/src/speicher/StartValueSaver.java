package speicher;

import model.Korrelation;
import model.UTFDatatype;
import projectT_Fun_I.GlobalSettings;

public class StartValueSaver {

	/**
	 * Fügt dem Speicherfile einen neuen Trace mit der dazuberechneten
	 * Übertragungsfunktion hinzu.
	 * 
	 * @param ordnung
	 * @param zaehler
	 * @param omegaqs
	 * @param sigma
	 * @param messwerte
	 */

	public static void addUTF(byte ordnung, UTFDatatype utf, double[][] messwerte) {
		FilePackageDatatype save = new FilePackageDatatype();
		double korrKoeff = 0.0;
		FilePackageDatatype[] plotpack = DataFile.getdata();
		double[] korrKoeffa = new double[plotpack.length];

		// Plots mit Ordnung kleiner als 5,
		// weniger 625 und
		// mehr als 50000 Messpunkten werden nicht gespeichert
		if (ordnung > 4 && messwerte.length > 625 && messwerte.length < 50000) {
			save.ordnung = ordnung;
			save.utf = utf;

			// Eingelesener Plot wird auf 2500 Messwerte normiert, zum
			// abspeichern
			save.doubl = plotnorm(messwerte);

			// Berechnen der Korrelationen, der berechneten Plots, mit den
			// abgespeicherten Plots
			for (int i = 0; i < plotpack.length; i++) {
				korrKoeffa[i] = Korrelation.korrKoeff(save.doubl, plotpack[i].doubl);
			}

			// Erkennung der höchsten Korrealtion
			for (int i = 1; i < korrKoeffa.length - 1; i++) {
				if (korrKoeffa[i] > korrKoeffa[i - 1]) {
					korrKoeff = korrKoeffa[i];
				}
			}
			// Ist die Korrelation kleiner als ..., wird der Plot abgespeichert
			if (korrKoeff < GlobalSettings.korrKoeffMin) {
				DataFile.setdata(save);
			}
		}
	}

	/**
	 * Sucht im Speicherfile nach einer ähnlichen Kurve und gibt die
	 * dazugehörige Übertragungsfunktion zurück.
	 * 
	 * @param plot
	 * @param ordnung
	 * @return
	 */

	public static UTFDatatype getSimilarUTF(double[][] plot, boolean[] ordnung) {

		FilePackageDatatype returnp = new FilePackageDatatype();

		if (ordnung[4] == true || ordnung[5] == true || ordnung[6] == true || ordnung[7] == true || ordnung[8] == true
				|| ordnung[9] == true) {

			// Plot auf 2500 werte normen.
			double[] plotn = plotnorm(plot);

			// Datenbank laden.
			FilePackageDatatype[] plotpack = DataFile.getdata();

			double[] korrKoeffa = new double[plotpack.length];
			double korrKoeff = 0.0;
			int position = 0;

			// Ordnung überprüfen und Korrelationskoeffizient überprüfen
			for (int i = 0; i < plotpack.length; i++) {
				if ((ordnung[4] == true && true == (plotpack[i].ordnung == 5))
						|| (ordnung[5] == true && true == (plotpack[i].ordnung == 6))
						|| (ordnung[6] == true && true == (plotpack[i].ordnung == 7))
						|| (ordnung[7] == true && true == (plotpack[i].ordnung == 8))
						|| (ordnung[8] == true && true == (plotpack[i].ordnung == 9))
						|| (ordnung[9] == true && true == (plotpack[i].ordnung == 10))) {
					korrKoeffa[i] = Korrelation.korrKoeff(plotn, plotpack[i].doubl);

					// Wenn Korrelationskoeffizient grösser als 0.8, berechnung
					// abbrechen und position speichern
					if (korrKoeffa[i] > 0.8) {
						korrKoeff = korrKoeffa[i];
						position = i;
						break;
					}
				}
			}

			// Wenn kein Korrelationskoeffizient grösser als 0.8, den grössten
			// herausfinden und position abspeichern.
			if (korrKoeff == 0) {
				for (int i = 1; i < korrKoeffa.length - 1; i++) {
					if (korrKoeffa[i] > korrKoeffa[i - 1]) {
						korrKoeff = korrKoeffa[i];
						position = i;
					}
				}
			}
			returnp.utf = plotpack[position].utf;
		} else {
			returnp.utf.zaehler = 1;
			returnp.utf.koeffWQ = new double[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
			returnp.utf.sigma = 1;
		}

		// geeignete Startwerte ausgeben mit der vorhin ermittelten position.
		return returnp.utf;
	}

	/**
	 * Methode plotnorm um die Messpunkte auf einen bestimmten Wert zu normieren
	 * 
	 * @param plot
	 * @return
	 */
	private static double[] plotnorm(double[][] plot) {
		// Wert value für die Anzahl Messewerte die herausgegeben werden
		final int value = 2500;
		double plotn[] = new double[value];

		// Anzahl Messwerte, durch verdoppeln einzelner Messwerte, auf den Wert
		// value setzen.
		if (plot[1].length < value && (value / 2) <= plot[1].length) {

			int k[] = new int[value - plot[1].length];

			// Messpunkte die verdoppelt werden ermitteln und in k schreiben
			for (int i = 0; i < (value - plot[1].length); i++) {
				k[i] = (int) (Math.abs((1 / (1 - (plot[1].length / (double) value)) * (i + 1))));
			}

			// Messpunkte im Punkt k verdoppeln und alle weiterfolgenden
			// dementsprechend verschieben.
			int t = 0;
			for (int i = 0; i < (value * 2) + plot[1].length; i++) {
				for (int j = 0; j < k.length; j++) {
					if (k[j] == (i + 1)) {
						t++;
					}
				}
				if ((i - t) < plot[1].length)
					plotn[i] = plot[1][i - t];
			}

		}

		// Anzahl Messwerte, durch verdoppeln aller Messwerte und verdrei- und
		// vervierfachen einzelner Werte,
		// auf den Wert value setzen.
		else if ((value / 2) > plot[1].length) {

			double plott[] = new double[plot[1].length * 2];

			int k[] = new int[(plot[1].length)];

			// Messpunkte die verdoppelt werden ermitteln und in k schreiben.
			for (int i = 0; i < (plot[1].length); i++) {
				k[i] = (int) (Math.abs((1 / (1 - (plot[1].length / (double) (plot[1].length * 2))) * (i + 1))));
			}

			// Messpunkte im Punkt k verdoppeln und alle weiterfolgenden
			// dementsprechend verschieben.
			int t = 0;
			for (int i = 0; i < (plot[1].length * 4) + (plot[1].length); i++) {
				for (int j = 0; j < (plot[1].length); j++) {
					if (k[j] == (i + 1)) {
						t++;
					}
				}
				if ((i - t) < (plot[1].length))
					plott[i] = plot[1][i - t];
			}

			// Messpunkte die verdrei- und vervierfacht werden ermitteln und in
			// kk schreiben.
			int kk[] = new int[value - plott.length];
			for (int i = 0; i < (value - plott.length); i++) {
				kk[i] = (int) (Math.abs((1 / (1 - (plott.length / (double) value)) * (i + 1))));
			}

			// Messpunkte im Punkt kk verdoppeln und alle weiterfolgenden
			// dementsprechend verschieben.
			int tt = 0;
			for (int i = 0; i < (value * 2) + plott.length; i++) {
				for (int j = 0; j < kk.length; j++) {
					if (kk[j] == (i + 1)) {
						tt++;
					}
				}
				if ((i - tt) < plott.length)
					plotn[i] = plott[i - tt];
			}
		}

		// Anzahl Messwerte, durch wegfallen einzelner Werte auf Wert value
		// setzten.
		else if (plot[1].length > value) {

			int k[] = new int[plot[1].length - value];

			// Messpunkte die gesstrichen werden ermitteln und in k schreiben.
			for (int i = 0; i < (plot[1].length - value); i++) {
				k[i] = (int) (Math.abs((1 / (1 - (plot[1].length / (double) value)) * (i + 1))));
			}

			// Messpunkte im Punkt k verdoppeln und alle weiterfolgenden
			// dementsprechend verschieben.
			int t = 0;
			for (int i = 0; i < plot[1].length; i++) {
				for (int j = 0; j < k.length; j++) {
					if (k[j] == i) {
						t++;
					}
				}
				if ((i + t) < plot[1].length)
					plotn[i] = plot[1][i + t];
			}
		}

		// Messwerte alle übernehmen.
		else {
			for (int i = 0; i < plot[1].length; i++) {
				plotn[i] = plot[1][i];
			}
		}

		// neue Messpunkte übernehmen.
		return plotn;
	}
}
