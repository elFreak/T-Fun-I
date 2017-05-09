package userInterface;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import projectTfunI.GlobalSettings;

public class InputCardEinlesen extends JPanel {
	
	private JPanel panelEinlesen = new JPanel(new GridBagLayout());
	private JButton btEinlesen = new JButton("Öffnen");
		
	
	public InputCardEinlesen(Controller controller) {
//		panelEinlesen.add(btEinlesen);
		btEinlesen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.setMesuredData(DataRead.csvread());				
			}
		});
		
		// Startwert		
		panelEinlesen.setBackground(GlobalSettings.colorBackground);
		panelEinlesen.setBorder(MyBorderFactory.createMyBorder("Aus CSV-Datei einlesen"));
										
		panelEinlesen.add(btEinlesen, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		
		// Cardpanel konfigurieren
		this.setLayout(new GridBagLayout());
		this.setBackground(GlobalSettings.colorBackground);
		this.setBorder(MyBorderFactory.createMyBorder("Gütebestimmung"));

		this.add(panelEinlesen, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.NORTH,
				GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		this.add(new JPanel(), new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
	}

}
