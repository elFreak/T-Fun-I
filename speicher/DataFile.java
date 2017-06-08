package speicher;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class DataFile {

	public static void createdatafile() {
		FilePackageDatatype[] ones = new FilePackageDatatype[6];
		for (int i = 0; i < 6; i++) {
			ones[i] = new FilePackageDatatype();
		}

		for (int i = 5; i < 11; i++) {
			ones[i - 5].ordnung = (byte) i;
			ones[i - 5].utf.zaehler = 1;
			ones[i - 5].utf.koeffWQ = new double[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
			ones[i - 5].utf.sigma = 1;

			for (int j = 0; j < 2500; j++) {
				ones[i - 5].doubl[j] = 1;
			}
		}
		FileOutputStream fileos;
		try {
			fileos = new FileOutputStream("database.tiffany", true);
			DataOutputStream os = new DataOutputStream(fileos);

			for (int j = 0; j < 6; j++) {
				os.writeByte(ones[j].ordnung);
				os.writeDouble(ones[j].utf.zaehler);
				for (int i = 0; i < 10; i++) {
					os.writeDouble(ones[j].utf.koeffWQ[i]);
				}
				os.writeDouble(ones[j].utf.sigma);
				for (int i = 0; i < 2500; i++) {
					os.writeDouble(ones[j].doubl[i]);
				}
			}
			os.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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
			if (data.utf.zaehler == 0) {
				data.utf.zaehler = -1;
			}
			for (int i = 0; i < 10; i++) {
				if (data.utf.koeffWQ[i] == 0) {
					data.utf.koeffWQ[i] = -1;
				}
			}
			if (data.utf.sigma == 0) {
				data.utf.sigma = -1;
			}

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
			// Ausgelesene Daten werden in ein FilePackageDatatype-Array
			// gespeichert.
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
