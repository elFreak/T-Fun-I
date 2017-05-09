package userInterface;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class DataRead {
	public static double[][] csvread() {
		double[][] data = null;
		int nLines = 0;
		int nColumns = 0;
		final JFileChooser chooser = new JFileChooser("Open");
		

		try {
			//Filechooser Filter einstellen
			FileNameExtensionFilter filter = new FileNameExtensionFilter("csv Datei","csv");
			chooser.setFileFilter(filter);
			//File auswählen und Pfad bestimmen
			chooser.showOpenDialog(chooser);
			String Filepath = chooser.getSelectedFile().getPath();
			// Anzahl Zeilen und Anzahl Kolonnen festlegen und festlegen ob Spalten oder Zeilenvektor vorhanden ist:
			BufferedReader eingabeDatei = new BufferedReader(new FileReader(Filepath));
			String[] s = eingabeDatei.readLine().split("[, ]+");
			int t = s.length;
			if (t<=3) {
				nColumns = s.length;
				nLines++;
				while (eingabeDatei.readLine() != null) {
					nLines++;
				}
			}
			else {
				nLines = s.length;
				nColumns++;
				while (eingabeDatei.readLine() != null) {
					nColumns++;
				}
			}
			eingabeDatei.close();

			// Gezählte Anzahl Zeilen und Kolonnen lesen:
			eingabeDatei = new BufferedReader(new FileReader(Filepath));
			data = new double[nColumns][nLines];
			for (int i = 0; i < data.length; i++) {
				s = eingabeDatei.readLine().split("[, ]+");
				for (int k = 0; k < s.length; k++) {
					data[i][k] = Double.parseDouble(s[k]);
				}
			}
			eingabeDatei.close();
		} catch (IOException exc) {
			System.err.println("Dateifehler: " + exc.toString());
		}
		return data;
	}
}
