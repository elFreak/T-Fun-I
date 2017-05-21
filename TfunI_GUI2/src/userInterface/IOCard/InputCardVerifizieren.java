package userInterface.IOCard;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import projectT_Fun_I.GlobalSettings;
import userInterface.Controller;
import userInterface.MyBorderFactory;

/**
 * 
 * @author Simon Zoller
 *
 */

public class InputCardVerifizieren extends JPanel {
	private static final long serialVersionUID = 1L;

	Controller controller;
	
	private JPanel panelFilter = new JPanel(new GridBagLayout());
	JCheckBox cbMitFilter = new JCheckBox("", true);
	
	private JPanel panelExponent = new JPanel(new GridBagLayout());
	private JCheckBox cb2 = new JCheckBox();
	private JCheckBox cb4 = new JCheckBox();
	private JCheckBox cb6 = new JCheckBox();
	
	public InputCardVerifizieren (Controller controller){
		this.controller = controller;

		// Textfeld Filter konfigurieren
		// Anonymer MouseWheelListener für Testfeld
//		tfFilter.addMouseWheelListener(new MouseWheelListener());
		// Anonymer ActionListener für Textfeld bei Enter
//		tfFilter.addActionListener(new ActionListener());
		
		// Gütebestimmung Filter		
		panelFilter.setBackground(GlobalSettings.colorBackground);
		panelFilter.setBorder(MyBorderFactory.createMyBorder("Filter"));
		cbMitFilter.setOpaque(false);
		
		
		panelFilter.add(new JLabel("Mit Filter"), new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		panelFilter.add(cbMitFilter, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
			
		
		// Gütebestimmung Fehlersumme		
		panelExponent.setBackground(GlobalSettings.colorBackground);
		panelExponent.setBorder(MyBorderFactory.createMyBorder("Exponent Fehlersumme"));
		cb2.setOpaque(false);
		cb4.setOpaque(false);
		cb6.setOpaque(false);
				
		panelExponent.add(new JLabel("2"), new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		panelExponent.add(new JLabel("4"), new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		panelExponent.add(new JLabel("6"), new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		panelExponent.add(cb2, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.FIRST_LINE_START,
					GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		panelExponent.add(cb4, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		panelExponent.add(cb6, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
	
		// Cardpanel konfigurieren
		this.setLayout(new GridBagLayout());
		this.setBackground(GlobalSettings.colorBackground);

		this.add(panelFilter, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.NORTH,
				GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		this.add(panelExponent, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		this.add(new JPanel(), new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));	
		
	}

}
