package userInterface;

import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import JavaPlot.Plot;
import projectTfunI.GlobalSettings;
import projectTfunI.Utility;

public class OutputPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	/**
	 * General
	 */
	private Controller controller;
	private CardLayout cardLayout = new CardLayout();

	/**
	 * Card "Einlesen"
	 */
	private WindowContainer cardEinlesen = new WindowContainer();
	public Plot plotEinlesen = new Plot();

	/**
	 * Card "Bearbeiten"
	 */
	private WindowContainer cardBearbeiten = new WindowContainer();
	public Plot plotBearbeiten = new Plot();
	
	/**
	 * Card "Berechnen"
	 */
	private WindowContainer cardBerechnen = new WindowContainer();

	/**
	 * Card "Verifizieren"
	 */
	private WindowContainer cardVertifizieren = new WindowContainer();

	public OutputPanel(Controller controller) {
		// ------------------------------------------------------------------
		// Output-Panel
		// ------------------------------------------------------------------
		// Output-Panel Design:
		setBorder(MyBorderFactory.createMyBorder("  Ausgabe  "));
		Utility.setAllBackgrounds(this, GlobalSettings.colorBackground);
		setOpaque(true);
		setBackground(GlobalSettings.colorBackgroundBlueBright);
		// Input-Panel Layout:
		setLayout(cardLayout);
		add(cardEinlesen, Controller.KEY_EINLESEN);
		add(cardBearbeiten, Controller.KEY_BEARBEITEN);
		add(cardBerechnen, Controller.KEY_BERECHNEN);
		add(cardVertifizieren, Controller.KEY_VERTIFIZIEREN);

		// Init Cards:
		cardEinlesenInit();
		cardBearbeitenInit();
		cardBerechnenInit();
		cardVertifizierenInit();
	}

	/**
	 * 
	 */
	private void cardEinlesenInit() {
		cardEinlesen.addComponent(plotEinlesen);
	}

	/**
	 * 
	 */
	private void cardBearbeitenInit() {
		cardBearbeiten.addComponent(plotBearbeiten);
		plotBearbeiten.addSubplot();
		plotBearbeiten.connectSubplots();
	}

	/**
	 * 
	 */
	private void cardBerechnenInit() {

	}

	/**
	 * 
	 */
	private void cardVertifizierenInit() {

	}

	public void setActualMode(int mode) {
		switch (mode) {
		case Controller.EINLESEN:
			cardLayout.show(this, Controller.KEY_EINLESEN);
			break;
		case Controller.BEARBEITEN:
			cardLayout.show(this, Controller.KEY_BEARBEITEN);
			break;
		case Controller.BERECHNEN:
			cardLayout.show(this, Controller.KEY_BERECHNEN);
			break;
		case Controller.VERTIFIZIEREN:
			cardLayout.show(this, Controller.KEY_VERTIFIZIEREN);
			break;
		}
	}
}