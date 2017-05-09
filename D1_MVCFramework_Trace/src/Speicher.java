import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Speicher {
	
	private double[] darray = new double[100];
	private double[] darrayis = new double[100];

	boolean lesen = true;

	public void save(String name, double[][] values, Complex[] complex) {
		// Create a bin-File:
		String filename = "C:" + File.separator + "TfunI" + File.separator + name + ".bin";
		if (lesen == false) {

			for (int i = 0; i < darray.length; i++) {
				darray[i] = i*1.5;
			}

			
			try {
				FileOutputStream fileos = new FileOutputStream(filename);
				ObjectOutputStream os = new ObjectOutputStream(fileos);

				for (int i = 0; i < 100; i++) {
					os.writeDouble(darray[i]);
				}

				os.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if (lesen == true) {

			try {
				FileInputStream fileis = new FileInputStream(filename);
				ObjectInputStream is = new ObjectInputStream(fileis);

				for (int i = 0; i < 100; i++) {
					darrayis[i] = is.readDouble();
				}

				is.close();
				for (int i = 0; i < 100; i++) {
					System.out.println(darrayis[i]);
				}

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// Define values to save
		// this.xyvalues = values;
		// this.complexzeroes = complex;

	}

	public static void main(String args[]) {

		// Values for Testing
		double[][] xywerte = new double[3][100];
		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 3; j++) {
				xywerte[j][i] = 1;
			}
		}

		Complex[] nullstellen = new Complex[15];
		for (int i = 0; i < 15; i++) {
			nullstellen[i] = new Complex(1.0, 2.0);
		}
		Speicher speicher = new Speicher();
		speicher.save("Name", xywerte, nullstellen);
		// speicher.kurve(xywerte, nullstellen);
		// speicher.doc("savevalues");
	}
}
