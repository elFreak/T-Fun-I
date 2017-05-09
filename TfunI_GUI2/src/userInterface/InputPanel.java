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
	private InputCardEinlesen inputCardEinlesen = new InputCardEinlesen(this.controller);
	
	
	private JPanel cardEinlesen = new JPanel(new GridBagLayout());
	private JButton btEinlesen = new JButton("Öffnen");

	
//	 Card "Bearbeiten"
	private InputCardBearbeiten inputCardBearbeiten = new InputCardBearbeiten(this.controller);
	

	/**
	 * Card "Berechnen"
	 */
	private InputCardBerechnen inputCardBerechnen = new InputCardBerechnen(this.controller);

	/**
	 * Card "Verifizieren"
	 */
	private InputCardVertifizieren inputCardVertifizieren = new InputCardVertifizieren(this.controller);

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
		// Input-Panel Layout:
		setLayout(cardLayout);
		add(inputCardEinlesen, Controller.KEY_EINLESEN);
		add(inputCardBearbeiten, Controller.KEY_BEARBEITEN);
		add(inputCardBerechnen, Controller.KEY_BERECHNEN);
		add(inputCardVertifizieren, Controller.KEY_VERTIFIZIEREN);
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
