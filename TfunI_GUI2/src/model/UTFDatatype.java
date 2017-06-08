package model;

/**
 * Datentyp welcher eine Übertragungsfunktion representiert. Diese besteht aus
 * einem Bruch mit einer Konstanten ({@link #zaehler})in Zähler und einem
 * Polynom im Nenner. Das Attribut Ordnung definert die Ordnung des
 * Nennerpolynoms. Mit den Attributen {@link #sigma} und
 * {@link UTFDatatype#koeffWQ} werden die Polstellen der Übertragungsfunktion
 * definiert.
 * 
 * @author Team 1
 *
 */
public class UTFDatatype {

	public double zaehler;
	public double[] koeffWQ;
	public double sigma;
	public int ordnung;

}
