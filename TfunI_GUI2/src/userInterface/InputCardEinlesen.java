package userInterface;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import projectTfunI.GlobalSettings;

public class InputCardEinlesen extends JPanel {
	
	private JButton btEinlesen = new JButton("Öffnen");
	
	
	
	public InputCardEinlesen(Controller controller) {
		this.setBackground(GlobalSettings.colorBackground);
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
		this.add(panelEinlesen, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		JPanel panelBackground = new JPanel();
		panelBackground.setBackground(GlobalSettings.colorBackground);
		this.add(panelBackground, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.BOTH, new Insets(00, 00, 00, 00), 0, 0));
	}

}
