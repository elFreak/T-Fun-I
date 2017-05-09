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
	private final String KEY_EINLESEN = "EINLESEN";
	private JButton btEinlesen = new JButton("Öffnen");

	/**
	 * Card "Bearbeiten"
	 */
	private JPanel cardBearbeiten = new JPanel(new GridBagLayout());
	private final String KEY_BEARBEITEN = "BEARBEITEN";
	private JTextField tfFilter = new JTextField();
	private JLabel lbFilter = new JLabel("  ");
	private JTextField tfOffset = new JTextField();
	private JLabel lbOffset = new JLabel(" V");
	private JTextField tfBereichUnten = new JTextField();
	private JTextField tfBereichOben = new JTextField();
	private JLabel lbBereichUnten = new JLabel(" S");
	private JLabel lbBereichOben = new JLabel(" S");
	private JButton btAutoOffset = new JButton("Auto");
	private JButton btAutoBereich = new JButton("Auto");

	/**
	 * Card "Berechnen"
	 */
	private JPanel cardBerechnen = new JPanel(new GridBagLayout());
	private final String KEY_BERECHNEN = "BERECHNEN";

	/**
	 * Card "Verifizieren"
	 */
	private JPanel cardVertifizieren = new JPanel(new GridBagLayout());
	private final String KEY_VERTIFIZIEREN = "VERTIFIZIEREN";

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
		add(cardEinlesen, KEY_EINLESEN);
		add(cardBearbeiten, KEY_BEARBEITEN);
		add(cardBerechnen, KEY_BERECHNEN);
		add(cardVertifizieren, KEY_VERTIFIZIEREN);

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
	private void cardBearbeitenInit() {
		cardBearbeiten.setBackground(GlobalSettings.colorBackground);
		JPanel panelFiltern = new JPanel(new GridLayout(1, 3,10,10));
		panelFiltern.setBackground(GlobalSettings.colorBackground);
		panelFiltern.setBorder(MyBorderFactory.createMyBorder("Mittelwertfilter"));
		panelFiltern.add(new JLabel("Intensit.:"));
		panelFiltern.add(tfFilter);
		tfFilter.setText("10");
		tfFilter.addMouseWheelListener(new MouseWheelListener() {
			
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				int tempValue = (int)Double.parseDouble(tfFilter.getText());
				tempValue+=(int)(e.getWheelRotation()*1);
				if(tempValue<0) {
					tempValue = 0;
				}
				tfFilter.setText(String.valueOf(tempValue));				
			}
		});
		tfFilter.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int tempValue = (int)Double.parseDouble(tfFilter.getText());
				if(tempValue<0) {
					tempValue = 0;
				}
				tfFilter.setText(String.valueOf(tempValue));	
			}
		});
		JPanel panelBackground = new JPanel();
		panelBackground.setBackground(GlobalSettings.colorBackground);
		panelFiltern.add(panelBackground);
		JPanel panelOffset = new JPanel(new GridLayout(1, 3,10,10));
		panelOffset.setBackground(GlobalSettings.colorBackground);
		panelOffset.setBorder(MyBorderFactory.createMyBorder("Offset"));
		panelOffset.add(new JLabel("Offset:"));
		panelOffset.add(tfOffset);
		panelOffset.add(btAutoOffset);
		JPanel panelBereich = new JPanel(new GridLayout(1, 3,10,10));
		panelBereich.setBackground(GlobalSettings.colorBackground);
		panelBereich.setBorder(MyBorderFactory.createMyBorder("Bereich"));
		JPanel panelSubBereich1 = new JPanel(new GridLayout(2,1,10,10));
		JPanel panelSubBereich2 = new JPanel(new GridLayout(2,1,10,10));
		panelSubBereich1.setBackground(GlobalSettings.colorBackground);
		panelSubBereich2.setBackground(GlobalSettings.colorBackground);
		panelSubBereich1.add(new Label("Oben: "));
		panelSubBereich2.add(tfBereichOben);
		panelSubBereich1.add(new Label("Unten: "));
		panelSubBereich2.add(tfBereichUnten);
		panelBereich.add(panelSubBereich1);
		panelBereich.add(panelSubBereich2);
		panelBereich.add(btAutoBereich);
		
		cardBearbeiten.add(panelFiltern, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		cardBearbeiten.add(panelOffset, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		cardBearbeiten.add(panelBereich, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		panelBackground = new JPanel();
		panelBackground.setBackground(GlobalSettings.colorBackground);
		cardBearbeiten.add(panelBackground, new GridBagConstraints(0, 3, 1, 1, 1.0, 1.0,
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
			cardLayout.show(this, KEY_EINLESEN);
			break;
		case Controller.BEARBEITEN:
			cardLayout.show(this, KEY_BEARBEITEN);
			break;
		case Controller.BERECHNEN:
			cardLayout.show(this, KEY_BERECHNEN);
			break;
		case Controller.VERTIFIZIEREN:
			cardLayout.show(this, KEY_VERTIFIZIEREN);
			break;
		}
	}
}
