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
import projectTfunI.GlobalSettings;
import projectTfunI.Utility;

/**
 * 
 * @author Alexander Stutz
 *
 */
public class InputPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	/**
	 * General
	 */
	private Controller controller;
	private CardLayout cardLayout = new CardLayout();

	/**
	 * Card "Einlesen"
	 */
	private JPanel cardEinlesen = new JPanel(new GridBagLayout());
	private JButton btEinlesen = new JButton("Öffnen");

	// Card "Bearbeiten"
	private InputCardBearbeiten inputCardBearbeiten;

	/**
	 * Card "Berechnen"
	 */
	private JPanel cardBerechnen = new JPanel(new GridBagLayout());

	/**
	 * Card "Verifizieren"
	 */
	private JPanel cardVertifizieren = new JPanel(new GridBagLayout());

	/**
	 * 
	 * @param controller
	 */
	public InputPanel(Controller controller) {
		this.controller = controller;

		// ------------------------------------------------------------------
		// Input-Panel
		// ------------------------------------------------------------------
		// Input-Panel Design:
		setBorder(MyBorderFactory.createMyBorder("  Eingabe  "));
		Utility.setAllBackgrounds(this, GlobalSettings.colorBackground);
		setOpaque(true);
		setBackground(GlobalSettings.colorBackgroundBlueBright);

		// Init Cards:
		cardEinlesenInit();
		inputCardBearbeiten = new InputCardBearbeiten(this.controller);
		cardBerechnenInit();
		cardVertifizierenInit();

		// Input-Panel Layout:
		setLayout(cardLayout);
		add(cardEinlesen, Controller.KEY_EINLESEN);
		add(inputCardBearbeiten, Controller.KEY_BEARBEITEN);
		add(cardBerechnen, Controller.KEY_BERECHNEN);
		add(cardVertifizieren, Controller.KEY_VERTIFIZIEREN);

	}

	/**
	 * 
	 */
	private void cardEinlesenInit() {
		cardEinlesen.setBackground(GlobalSettings.colorBackground);
		JPanel panelEinlesen = new JPanel(new GridLayout(1, 1, 10, 10));
		panelEinlesen.setBackground(GlobalSettings.colorBackground);
		panelEinlesen.setBorder(MyBorderFactory.createMyBorder("Aus CSV-Datei einlesen"));
		panelEinlesen.add(btEinlesen);
		btEinlesen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.setMesuredData(DataRead.csvread());
			}
		});
		cardEinlesen.add(panelEinlesen, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		JPanel panelBackground = new JPanel();
		panelBackground.setBackground(GlobalSettings.colorBackground);
		cardEinlesen.add(panelBackground, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.BOTH, new Insets(00, 00, 00, 00), 0, 0));
	}

	/**
	 * 
	 */
	private void cardBerechnenInit() {
		cardBerechnen.setBackground(GlobalSettings.colorBackground);
	}

	/**
	 * 
	 */
	private void cardVertifizierenInit() {
		cardVertifizieren.setBackground(GlobalSettings.colorBackground);
	}

	/**
	 * 
	 * @param mode
	 */
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
