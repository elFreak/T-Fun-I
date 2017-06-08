package projectT_Fun_I;

import java.awt.Color;
import java.awt.Font;

import javax.swing.UIManager;

/**
 * Stellt alle Grundeinstellungen des Programms als public Attribute zur
 * Verfügung. Diese Einstellungen müssen bei Programmstart durch die statische
 * Methode {@link #init()} initialisiert werden. Die Einstellungen lassen sich in
 * folgende Gruppen unterteilen: Farben, Fonts, Strichstärken und Initialwerte.
 * 
 * @author Team 1
 *
 */
public class GlobalSettings {

	// Farben:
	public static Color colorBackground = UIManager.getColor("window");
	public static Color colorBackgroundGrey = UIManager.getColor("menu");
	public static Color colorBackgroundGreyBright = new Color(240, 240, 240);
	public static Color colorBackgroundBlueBright = new Color(218, 227, 243);
	public static Color colorBackgroundBlueDark = new Color(143, 170, 220);
	public static Color colorBackgroundGreen = new Color(170, 210, 140);
	public static Color colorBackgroundBlack = new Color(100, 100, 100);
	public static Color colorBackgroundWhite = new Color(250, 250, 250);
	public static Color colorText = new Color(40, 40, 40);
	public static Color colorTextGrey = new Color(75, 75, 75);
	public static Color colorTextInfo = colorTextGrey;
	public static Color colorTextFehler = new Color(240, 20, 20);
	public static Color colorGridBright = new Color(250, 250, 250);
	public static Color colorGridDark = new Color(70, 70, 70);
	public static Color colorGridBlue = new Color(180, 210, 240);
	public static Color colorGridYellow = new Color(250, 250, 20);
	public static Color colorTraceGreen = new Color(20, 250, 20);
	public static Color colorTraceYellow = new Color(255, 217, 102);
	public static Color colorTraceOrange = new Color(197, 90, 17);
	public static Color[] colorsTraceSolution = new Color[10];
	public static Color colorTraceGrey = new Color(200, 200, 250);
	public static Color colorSliderBlue = new Color(0, 180, 240);
	public static Color colorSliderViolet = new Color(204, 153, 255);

	// Fonts:
	public static Font fontText;
	public static Font fontTextSmall;
	public static Font fontMath;

	// Strichstärken:
	public static int traceThinkness;

	//Initialwerte:
	public static double korrKoeffMin = 0.995;
	public static double startValueThreshold = 1E-12;
	public static int startValueAnzahlWerte = 220;

	/**
	 * Methode um diverse Einstellungen zu initialisieren. Muss bei
	 * Programmstart einmal aufgerufen werden.
	 */
	public static void init() {
		
		// Fonts:
		Font systemFont = UIManager.getDefaults().getFont("TextPane.font");
		fontText = new Font(systemFont.getName(), Font.PLAIN, (int) (systemFont.getSize() * 1.3));
		fontTextSmall = new Font(systemFont.getName(), Font.PLAIN, (int) (systemFont.getSize() * 1.1));
		fontMath = new Font("Cambria Math", Font.ITALIC, (int) (systemFont.getSize() * 1.3));

		// Colors:
		colorsTraceSolution[0] = new Color(255, 140, 100);
		colorsTraceSolution[1] = new Color(255, 160, 0);
		colorsTraceSolution[2] = new Color(255, 255, 0);
		colorsTraceSolution[3] = new Color(210, 255, 50);
		colorsTraceSolution[4] = new Color(102, 255, 60);
		colorsTraceSolution[5] = new Color(0, 255, 155);
		colorsTraceSolution[6] = new Color(0, 255, 255);
		colorsTraceSolution[7] = new Color(0, 160, 255);
		colorsTraceSolution[8] = new Color(155, 100, 255);
		colorsTraceSolution[9] = new Color(255, 0, 255);

		// Strichstärken:
		traceThinkness = Math.max(fontText.getSize() / 8, 2);
	}

}
