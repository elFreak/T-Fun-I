package userInterface;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;

import javax.swing.JPanel;

import projectTfunI.GlobalSettings;
import projectTfunI.Utility;

public class InputPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	// --------------------------------------------------------------------
	// General:
	private Controller controller;

	// --------------------------------------------------------------------
	// Tab and Card Messwerte Einlesen:
	private JPanel cardEinlesen = new JPanel(new GridBagLayout());

	// --------------------------------------------------------------------
	// Tab and Card Messwerte Bearbeiten:
	private JPanel cardBearbeiten = new JPanel(new GridBagLayout());

	// --------------------------------------------------------------------
	// Tab and Card Übertragungsfunktion Berechnen:
	private JPanel cardBerechnen = new JPanel(new GridBagLayout());

	// --------------------------------------------------------------------
	// Tab and Card Übertragungsfunktion Vertifizieren:
	private JPanel cardVertifizieren = new JPanel(new GridBagLayout());

	public InputPanel(Controller controller) {
		this.controller = controller;
		setLayout(new GridLayout(1,1));
		setOpaque(true);
		setBorder(MyBorderFactory.createMyBorder("  Eingabe  "));

		Utility.setAllBackgrounds(this, GlobalSettings.colorBackground);
		this.setBackground(GlobalSettings.colorBackgroundBlueBright);
		cardEinlesen.setBackground(GlobalSettings.colorBackground);
		cardEinlesen.add(new Label("TestEinlesen"));
		cardBearbeiten.setBackground(GlobalSettings.colorBackground);
		cardBearbeiten.add(new Label("TestBearbeiten"));
		cardBerechnen.setBackground(GlobalSettings.colorBackground);
		cardBerechnen.add(new Label("TestBerechnen"));
		cardVertifizieren.setBackground(GlobalSettings.colorBackground);
		cardVertifizieren.add(new Label("TestVertifizieren"));

	}

	public void setActualMode(int mode) {
		removeAll();
		switch (mode) {

		case Controller.EINLESEN:
			add(cardEinlesen);
			break;
		case Controller.BEARBEITEN:
			add(cardBearbeiten);
			break;
		case Controller.BERECHNEN:
			add(cardBerechnen);
			break;
		case Controller.VERTIFIZIEREN:
			add(cardVertifizieren);
			break;
		}
		
		
		repaint();
	}
}
