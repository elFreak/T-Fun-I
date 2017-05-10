package projectT_Fun_I;

import java.awt.Color;
import java.awt.Font;

import javax.swing.UIManager;

public class GlobalSettings {

	public static Color colorBackground = UIManager.getColor("window");
	public static Color colorBackgroundGrey = UIManager.getColor("menu");
	public static Color colorBackgroundGreyBright = new Color(240, 240, 240);
	public static Color colorBackgroundBlueBright = new Color(218, 227, 243);
	public static Color colorBackgroundBlueDark = new Color(143, 170, 220);
	public static Color colorBackgroundGreen = new Color(170, 210, 140);
	public static Color colorBackgroundBlack = new Color(80, 80, 80);
	public static Color colorBackgroundWhite = new Color(250, 250, 250);

	public static Color colorText = new Color(40, 40, 40);
	public static Color colorTextGrey = new Color(75, 75, 75);
	
	public static Color colorGridBright = new Color(250, 250, 250);
	public static Color colorGridDark = new Color(70,70,70);
	public static Color colorGridBlue = new Color(180,210,240);
	public static Color colorGridYellow = new Color(250,250,20);

	public static Font fontText;
	public static Font fontTextSmall;
	
	public static Color colorTraceGreen = new Color(169,209,142);
	public static Color colorTraceYellow = new Color(255, 217, 102);
	public static Color colorTraceOrange = new Color(197, 90, 17);
	
	public static Color colorSliderBlue = new Color(0, 180, 240);
	public static Color colorSliderViolet = new Color(204, 153, 255);
	
	public static int traceThinkness = 4;

	public static void init() {
		// Fonts:
		Font systemFont = UIManager.getDefaults().getFont("TextPane.font");
		fontText = new Font(systemFont.getName(), Font.PLAIN, (int) (systemFont.getSize() * 1.3));
		fontTextSmall = new Font(systemFont.getName(), Font.PLAIN, (int) (systemFont.getSize() * 1.1));
	}

}
