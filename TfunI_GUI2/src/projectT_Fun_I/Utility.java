package projectT_Fun_I;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.MediaTracker;
import javax.swing.ImageIcon;

/**
 * Stellt diverse statische Methoden zur Verfügung. Diese Methoden lassen sich
 * in zwei Gruppen einordnen. Erstens Methoden um Bildaten in das Programm zu laden und
 * diese als Objekt zurückgeben. Zweitens Methoden um bestimmte Attribute von
 * Componenten systematisch zu verändern.
 * 
 * @author Team 1
 *
 */
public class Utility {

	private static Container p = new Container();

	/**
	 * Gibt das Bild an der Stelle strBild als Image-Objekt zurück;
	 * 
	 * @param strBild
	 * @return
	 */
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

	/**
	 * Gibt das Bild im Ordner bilder an der Stelle strBild als Image-Objekt
	 * zurück;
	 * 
	 * @param strBild
	 * @return
	 */
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

	/**
	 * Gibt das Icon im Ordner bilder an der Stelle strBild als ImageIcon-Objekt
	 * zurück;
	 * 
	 * @param strBild
	 * @return
	 */
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

	/**
	 * Setzt den Font des Componenten comp und alle Fonts der Componenten die zu
	 * comp gehören.
	 * 
	 * @param comp
	 * @param font
	 */
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

	/**
	 * Setzt die Fonts der Componenten comps und alle Fonts der Componenten die
	 * zu einem der comps gehören.
	 * 
	 * @param comps
	 * @param font
	 */
	public static void setAllFonts(Component[] comps, Font font) {
		for (int i = 0; i < comps.length; i++) {
			comps[i].setFont(font);
			if (comps[i] instanceof Container) {
				setAllFonts(((Container) comps[i]).getComponents(), font);
			}
		}
	}

	/**
	 * Setzt den Foreground des Componenten comp und alle Foreground der
	 * Componenten die zu comp gehören.
	 * 
	 * @param strBild
	 * @return
	 */
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

	/**
	 * Setzt die Foregrounds der Componenten comps und alle Foregrounds der
	 * Componenten die zu einem der comps gehören.
	 * 
	 * @param comps
	 * @param color
	 */
	public static void setAllForegrounds(Component[] comps, Color color) {
		for (int i = 0; i < comps.length; i++) {
			comps[i].setForeground(color);
			;
			if (comps[i] instanceof Container) {
				setAllForegrounds(((Container) comps[i]).getComponents(), color);
			}
		}
	}

	/**
	 * Setzt den Background des Componenten comp und alle Backgrounds der
	 * Componenten die zu comp gehören.
	 * 
	 * @param comp
	 * @param color
	 */
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

	/**
	 * Setzt die Backgrounds der Componenten comps und alle Backgrounds der
	 * Componenten die zu einem der comps gehören.
	 * 
	 * @param comps
	 * @param color
	 */
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
