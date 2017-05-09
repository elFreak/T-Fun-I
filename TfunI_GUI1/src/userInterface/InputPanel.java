package userInterface;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import projectTfunI.GlobalSettings;
import projectTfunI.Utility;

public class InputPanel extends JTabbedPane implements ActionListener {
	private static final long serialVersionUID = 1L;

	// --------------------------------------------------------------------
	// Tab and Card Messwerte:
	private JPanel cardMesswerte = new JPanel(new GridBagLayout());
	private String textMesswerte = " Einglesen ";
	private JButton btWeiterMesswerte = new JButton("Weiter");

	// --------------------------------------------------------------------
	// Tab and Card Signalbearbearbeitung:
	private JPanel cardBearbeitung = new JPanel(new GridBagLayout());
	private String textBearbeitung = " Vorbereiten ";
	private JButton btWeiterBearbetung = new JButton("Weiter");

	// --------------------------------------------------------------------
	// Tab and Card Berechnung:
	private JPanel cardBerechnung = new JPanel(new GridBagLayout());
	private String textBerechnung = " Berechnen ";

	public InputPanel() {
		setOpaque(true);
		setBorder(MyBorderFactory.createMyBorder("  Eingabe  "));
		setFocusable(true);

		// Tab and Card - Messwerte:
		addTab(textMesswerte, cardMesswerte);
		cardMesswerte.setLayout(new GridBagLayout());
		cardMesswerte.add(btWeiterMesswerte, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		btWeiterMesswerte.addActionListener(this);

		// Tab and Card - Bearbeiten:
		addTab(textBearbeitung, cardBearbeitung);
		cardBearbeitung.setLayout(new GridBagLayout());
		cardBearbeitung.add(btWeiterBearbetung, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		btWeiterBearbetung.addActionListener(this);

		// Tab and Card - Berechnung:
		addTab(textBerechnung, cardBerechnung);
		cardBerechnung.setLayout(new GridBagLayout());

		// Background Colors:
		Utility.setAllBackgrounds(this, GlobalSettings.colorBackground);
		this.setBackground(GlobalSettings.colorBackgroundBlueBright);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btWeiterMesswerte)) {
			setSelectedIndex(1);
		}
		if (e.getSource().equals(btWeiterBearbetung)) {
			setSelectedIndex(2);
		}

	}

}
