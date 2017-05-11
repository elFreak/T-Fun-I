package speicher;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class DataFile {

	// Daten in die Datei datafile speichern. Diese wird im ProjektOrdner
	// erstellt.
	public static void setdata(FilePackageDatatype data) {

		String filename = "datafile.bin";
		
		try {
			// Datenfluss öffnen
			// Speichert: Ordnung, zaehler, omegaqs, sigma und messwerte
			// Datenfluss schliessen
			FileOutputStream fileos = new FileOutputStream(filename, true);
			DataOutputStream os = new DataOutputStream(fileos);

			os.writeByte(data.ordnung);

			os.writeDouble(data.utf.zaehler);

			for (int i = 0; i < 10; i++) {
				os.writeDouble(data.utf.koeffWQ[i]);
			}

			os.writeDouble(data.utf.sigma);

			for (int i = 0; i < 2500; i++) {
				os.writeDouble(data.doubl[i]);
			}

			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Daten aus der Datei datafile auslesen. Diese ist im ProjektOrdner.
	public static FilePackageDatatype[] getdata() {
		FilePackageDatatype[] pack = new FilePackageDatatype[800];
		for (int i = 0; i < 800; i++) {
			pack[i] = new FilePackageDatatype();
		}

		String filename = "datafile.bin";

		try {
			// Datenfluss öffnen
			// Auslesen: Ordnung, zaehler, omegaqs, sigma und messwerte
			// Ausgelesene Daten werden in ein FilePackageDatatype-Array gespeichert.
			// Datenfluss schliessen
			FileInputStream fileis = new FileInputStream(filename);
			DataInputStream is = new DataInputStream(fileis);
			int size = is.available();

			for (int k = 0; k < (size / 20097); k++) {

				pack[k].ordnung = is.readByte();

				pack[k].utf.zaehler = is.readDouble();

				for (int i = 0; i < 10; i++) {
					pack[k].utf.koeffWQ[i] = is.readDouble();
				}

				pack[k].utf.sigma = is.readDouble();

				for (int i = 0; i < 2500; i++) {
					pack[k].doubl[i] = is.readDouble();
				}

			}

			is.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return pack;

	}
}
