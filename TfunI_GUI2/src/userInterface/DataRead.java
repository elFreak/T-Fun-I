package userInterface;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import javafx.stage.FileChooser;

/**
 * Stellt die statische Methode {@link #csvread()} zur Verfügung.
 * 
 * @author Team 1
 *
 */
public class DataRead {

	/**
	 * Öffnet einen {@link FileChooser}. Sobal der Benutzer eine csv-Datei
	 * ausgewählt hat, werden die darin enthaltenen Daten eingelesen. Bei
	 * Problemen wird der Benutzer mittels der {@link StatusBar} informiert.<s
	 * 
	 * @return data
	 */
	public static double[][] csvread() {
		JFileChooser chooser = new JFileChooser(new File(".\\"));
		BufferedReader eingabeDatei = null;
		File f = null;
		double[][] data = null;
		double[][] returndata = null;
		int nLines = 0;
		int nColumns = 0;
		boolean lengtherror = false;
		boolean negativetime = false;

		try {
			// Filechooser Filter einstellen
			FileNameExtensionFilter filter = new FileNameExtensionFilter("csv Datei", "csv");
			chooser.addChoosableFileFilter(filter);
			chooser.removeChoosableFileFilter(chooser.getChoosableFileFilters()[0]);

			// File auswählen und Pfad bestimmen
			chooser.showOpenDialog(chooser);
			f = chooser.getSelectedFile();
			if (f == null) {

			} else {
				String Filepath = f.getPath();
				// Anzahl Zeilen und Anzahl Kolonnen festlegen und festlegen ob
				// Spalten oder Zeilenvektor vorhanden ist:
				eingabeDatei = new BufferedReader(new FileReader(Filepath));

				String[] s = eingabeDatei.readLine().split("[, ]+");
				int t = s.length;
				eingabeDatei.close();
				eingabeDatei = new BufferedReader(new FileReader(Filepath));

				// Je nachdem, ob es sich bei der csv Datei um Spalten oder
				// Zeilenvektoren handelt,
				// wird die Datei anders eingelesen.
				if (t <= 3) {
					nColumns = s.length;
					while (eingabeDatei.readLine() != null) {
						nLines++;
					}

					// Gezählte Anzahl Zeilen und Kolonnen lesen:
					eingabeDatei.close();
					eingabeDatei = new BufferedReader(new FileReader(Filepath));
					data = new double[nColumns][nLines];
					for (int i = 0; i < nLines; i++) {
						s = eingabeDatei.readLine().split("[, ]+");
						// Abfrage, ob alle Vektoren dieselbe Länge besitzten.
						if (s.length != nColumns) {
							lengtherror = true;
						}
						for (int k = 0; k < s.length; k++) {
							data[k][i] = Double.parseDouble(s[k]);
						}
					}
					// Je nachdem, ob es sich bei der csv Datei um Spalten oder
					// Zeilenvektoren handelt,
					// wird die Datei anders eingelesen.
				} else {
					nLines = s.length;
					while (eingabeDatei.readLine() != null) {
						nColumns++;
					}

					// Gezählte Anzahl Zeilen und Kolonnen lesen:
					eingabeDatei.close();
					eingabeDatei = new BufferedReader(new FileReader(Filepath));
					data = new double[nColumns][nLines];
					for (int i = 0; i < nColumns; i++) {
						s = eingabeDatei.readLine().split("[, ]+");
						// Abfrage, ob alle Vektoren dieselbe Länge besitzten.
						if (s.length != nLines) {
							lengtherror = true;
						}
						for (int k = 0; k < s.length; k++) {
							data[i][k] = Double.parseDouble(s[k]);
						}
					}
				}
			}
			for (int i = 0; i < data[0].length; i++) {
				if (data[0][i] < 0) {
					negativetime = true;
				}
			}

			// Fehler werden abgefangen.
		} catch (IOException exc) {

		} catch (NumberFormatException exc) {
			if (f.getName().endsWith(".csv")) {
				String s = exc.toString();
				System.out.println(s);
				StatusBar.showStatus("Datei enthällt nicht nur Zahlen. Fehler bei Dateipunkt " + s.substring(50) + ".",
						StatusBar.FEHLER);
			}
		} catch (NullPointerException exc) {
			StatusBar.showStatus("Datei enthällt keine Daten.", StatusBar.FEHLER);
		}

		if (lengtherror) {
			StatusBar.showStatus("Die Vektoren besitzen nicht dieselbe Länge.", StatusBar.FEHLER);
		} else if (negativetime) {
			StatusBar.showStatus("Der Zeitvektor darf keine negativen Werte beinnhalten.", StatusBar.FEHLER);
		} else {
			returndata = data;
		}
		// Daten werden zurückgegeben.
		return returndata;
	}

}
