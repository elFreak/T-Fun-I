package userInterface.IOCard;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import projectT_Fun_I.GlobalSettings;
import userInterface.Controller;
import userInterface.DataRead;
import userInterface.MyBorderFactory;

public class InputCardEinlesen extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JPanel panelEinlesen = new JPanel(new GridBagLayout());
	private JButton btEinlesen = new JButton("Öffnen");

	public InputCardEinlesen(Controller controller) {
		btEinlesen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.setMesuredData(DataRead.csvread());
			}
		});

		// Startwert
		panelEinlesen.setBackground(GlobalSettings.colorBackground);
		panelEinlesen.setBorder(MyBorderFactory.createMyBorder("Aus CSV-Datei einlesen"));

		panelEinlesen.add(btEinlesen, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));

		// Cardpanel konfigurieren
		this.setLayout(new GridBagLayout());
		this.setBackground(GlobalSettings.colorBackground);

		this.add(panelEinlesen, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.NORTH,
				GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		JPanel panelBackground = new JPanel();
		panelBackground.setBackground(GlobalSettings.colorBackground);
		this.add(panelBackground, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.BOTH, new Insets(20, 10, 10, 10), 0, 0));
	}

}
