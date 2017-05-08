package userInterface;

import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import projectTfunI.GlobalSettings;
import projectTfunI.Utility;

public class InputPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	// --------------------------------------------------------------------
	// General:
	private Controller controller;
	private CardLayout cardLayout = new CardLayout();

	// --------------------------------------------------------------------
	// Tab and Card Messwerte Einlesen:
	private JPanel cardEinlesen = new JPanel(new GridBagLayout());
	private final String EINLESEN = "EINLESEN";
	private JButton btWeiterEinlesen = new JButton("Weiter");

	// --------------------------------------------------------------------
	// Tab and Card Messwerte Bearbeiten:
	private JPanel cardBearbeiten = new JPanel(new GridBagLayout());
	private final String BEARBEITEN = "BEARBEITEN";
	private JButton btWeiterBearbeiten = new JButton("Weiter");

	// --------------------------------------------------------------------
	// Tab and Card Übertragungsfunktion Berechnen:
	private JPanel cardBerechnen = new JPanel(new GridBagLayout());
	private final String BERECHNEN = "BERECHNEN";
	private JButton btWeiterBerechnen = new JButton("Weiter");

	// --------------------------------------------------------------------
	// Tab and Card Übertragungsfunktion Vertifizieren:
	private JPanel cardVertifizieren = new JPanel(new GridBagLayout());
	private final String VERTIFIZIEREN = "VERTIFIZIEREN";

	public InputPanel(Controller controller) {
		this.controller = controller;
		setLayout(cardLayout);
		setOpaque(true);
		setBorder(MyBorderFactory.createMyBorder("  Eingabe  "));

		Utility.setAllBackgrounds(this, GlobalSettings.colorBackground);
		this.setBackground(GlobalSettings.colorBackgroundBlueBright);
		cardEinlesen.setBackground(GlobalSettings.colorBackground);
		// cardEinlesen.add(new Label("TestEinlesen"));
		cardBearbeiten.setBackground(GlobalSettings.colorBackground);
		// cardBearbeiten.add(new Label("TestBearbeiten"));
		cardBerechnen.setBackground(GlobalSettings.colorBackground);
		// cardBerechnen.add(new Label("TestBerechnen"));
		cardVertifizieren.setBackground(GlobalSettings.colorBackground);
		// cardVertifizieren.add(new Label("TestVertifizieren"));

		add(cardEinlesen, EINLESEN);
		add(cardBearbeiten, BEARBEITEN);
		add(cardBerechnen, BERECHNEN);
		add(cardVertifizieren, VERTIFIZIEREN);

		cardEinlesen.add(btWeiterEinlesen, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.LAST_LINE_END, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		cardBearbeiten.add(btWeiterBearbeiten, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.LAST_LINE_END, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		cardBerechnen.add(btWeiterBerechnen, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.LAST_LINE_END, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

		btWeiterEinlesen.addActionListener(this);
		btWeiterBearbeiten.addActionListener(this);
		btWeiterBerechnen.addActionListener(this);

	}

	public void setActualMode(int mode) {
		switch (mode) {
		case Controller.EINLESEN:

			break;
		case Controller.BEARBEITEN:

			break;
		case Controller.BERECHNEN:

			break;
		case Controller.VERTIFIZIEREN:

			break;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btWeiterEinlesen)) {
			controller.setActualMode(Controller.BEARBEITEN);
		}
		if (e.getSource().equals(btWeiterBearbeiten)) {
			controller.setActualMode(Controller.BERECHNEN);
		}
		if (e.getSource().equals(btWeiterBerechnen)) {
			controller.setActualMode(Controller.VERTIFIZIEREN);
		}
	}
}
