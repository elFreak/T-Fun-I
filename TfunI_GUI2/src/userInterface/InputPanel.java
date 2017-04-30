package userInterface;

import java.awt.GridBagLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class InputPanel extends JTabbedPane {
	private static final long serialVersionUID = 1L;

	// --------------------------------------------------------------------
	// Tab and Card Messwerte:
	private JPanel cardMesswerte = new JPanel(new GridBagLayout());
	private String textMesswerte = " Messwerte ";

	// --------------------------------------------------------------------
	// Tab and Card Signalbearbearbeitung:
	private JPanel cardBearbeitung = new JPanel(new GridBagLayout());
	private String textBearbeitung = " Bearbeiten ";

	// --------------------------------------------------------------------
	// Tab and Card Berechnung:
	private JPanel cardBerechnung = new JPanel(new GridBagLayout());
	private String textBerechnung = " Berechnen ";

	// --------------------------------------------------------------------
	// Tab and Card Prüfung:
	private JPanel cardPruefung = new JPanel(new GridBagLayout());
	private String textPruefung = " Überprüfen ";

	public InputPanel() {
		setOpaque(true);
		setBorder(MyBorderFactory.createMyBorder("  Eingabe  "));
		setFocusable(true);

		// Tab and Card - Messwerte:
		addTab(textMesswerte, cardMesswerte);

		// Tab and Card - Bearbeiten:
		addTab(textBearbeitung, cardBearbeitung);

		// Tab and Card - Berechnung:
		addTab(textBerechnung, cardBerechnung);

		// Tab and Card - Prüfung:
		addTab(textPruefung, cardPruefung);
	}

}
