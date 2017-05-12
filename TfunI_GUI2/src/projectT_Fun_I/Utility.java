package projectT_Fun_I;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.MediaTracker;
import javax.swing.ImageIcon;

public class Utility {

	private static Container p = new Container();

	public static Image loadImage(String strBild) {
		MediaTracker tracker = new MediaTracker(p);
		Image img = (new ImageIcon(strBild)).getImage();
		tracker.addImage(img, 0);
		try {
			tracker.waitForID(0);
		} catch (InterruptedException ex) {
			System.out.println("Can not load image: " + strBild);
		}
		return img;
	}

	public static Image loadResourceImage(String strBild) {
		MediaTracker tracker = new MediaTracker(p);
		Image img = (new ImageIcon(Utility.class.getResource("bilder" + "/" + strBild))).getImage();
		tracker.addImage(img, 0);
		try {
			tracker.waitForID(0);
		} catch (InterruptedException ex) {
			System.out.println("Can not load image: " + strBild);
		}
		return img;
	}

	public static ImageIcon loadResourceIcon(String strBild) {
		MediaTracker tracker = new MediaTracker(p);
		ImageIcon icon = new ImageIcon(Utility.class.getResource("bilder" + "/" + strBild));
		// System.out.println(Utility.class.getResource("icons" + File.separator
		// + strBild));
		tracker.addImage(icon.getImage(), 0);
		try {
			tracker.waitForID(0);
		} catch (InterruptedException ex) {
			System.out.println("Can not load image: " + strBild);
		}
		return icon;
	}

	public static void setAllFonts(Component comp, Font font) {
		Component[] comps = ((Container) comp).getComponents();
		comp.setFont(font);
		for (int i = 0; i < comps.length; i++) {
			comps[i].setFont(font);
			if (comps[i] instanceof Container) {
				setAllFonts(((Container) comps[i]).getComponents(), font);
			}
		}
	}
	
	public static void setAllFonts(Component[] comps, Font font) {
		for (int i = 0; i < comps.length; i++) {
			comps[i].setFont(font);
			if (comps[i] instanceof Container) {
				setAllFonts(((Container) comps[i]).getComponents(), font);
			}
		}
	}
	
	public static void setAllForegrounds(Component comp, Color color) {
		Component[] comps = ((Container) comp).getComponents();
		comp.setForeground(color);
		for (int i = 0; i < comps.length; i++) {
			comps[i].setForeground(color);
			if (comps[i] instanceof Container) {
				setAllForegrounds(((Container) comps[i]).getComponents(), color);
			}
		}
	}

	public static void setAllForegrounds(Component[] comps, Color color) {
		for (int i = 0; i < comps.length; i++) {
			comps[i].setForeground(color);
			;
			if (comps[i] instanceof Container) {
				setAllForegrounds(((Container) comps[i]).getComponents(), color);
			}
		}
	}
	
	public static void setAllBackgrounds(Component comp, Color color) {
		Component[] comps = ((Container) comp).getComponents();
		comp.setBackground(color);
		for (int i = 0; i < comps.length; i++) {
			comps[i].setBackground(color);
			if (comps[i] instanceof Container) {
				setAllBackgrounds(((Container) comps[i]).getComponents(), color);
			}
		}
	}

	public static void setAllBackgrounds(Component[] comps, Color color) {
		for (int i = 0; i < comps.length; i++) {
			comps[i].setBackground(color);
			;
			if (comps[i] instanceof Container) {
				setAllBackgrounds(((Container) comps[i]).getComponents(), color);
			}
		}
	}
	
	
}
