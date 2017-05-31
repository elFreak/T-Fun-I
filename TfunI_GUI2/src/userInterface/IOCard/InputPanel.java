package userInterface.IOCard;

import java.awt.CardLayout;
import javax.swing.JPanel;import projectT_Fun_I.GlobalSettings;
import projectT_Fun_I.Utility;
import userInterface.Controller;
import userInterface.MyBorderFactory;

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
	 * Cards
	 */
	private InputCardEinlesen inputCardEinlesen;
	private InputCardBearbeiten inputCardBearbeiten;
	private InputCardBerechnen inputCardBerechnen;
	private InputCardVerifizieren inputCardVerifizieren;

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
		inputCardEinlesen = new InputCardEinlesen(this.controller);
		inputCardBearbeiten = new InputCardBearbeiten(this.controller);
		inputCardBerechnen = new InputCardBerechnen(this.controller);
		inputCardVerifizieren = new InputCardVerifizieren(this.controller);

		// Input-Panel Layout:
		setLayout(cardLayout);
		add(inputCardEinlesen, Controller.KEY_EINLESEN);
		add(inputCardBearbeiten, Controller.KEY_BEARBEITEN);
		add(inputCardBerechnen, Controller.KEY_BERECHNEN);
		add(inputCardVerifizieren, Controller.KEY_VERIFIZIEREN);
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
		case Controller.VERIFIZIEREN:
			cardLayout.show(this, Controller.KEY_VERIFIZIEREN);
			break;
		}
	}

	public void update(java.util.Observable obs, Object obj) {
		inputCardBearbeiten.update(obs, obj);
		inputCardBerechnen.update(obs, obj);
		inputCardVerifizieren.update(obs, obj);
	}

	public boolean[] getBerechneCB() {
		return inputCardBerechnen.getCBState();
	}

	public void setVerifizizerenOrder(int i) {
		inputCardVerifizieren.setOrder(i);		
	}
}
