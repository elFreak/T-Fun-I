package userInterface;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import projectTfunI.GlobalSettings;

public class InputCardVertifizieren extends JPanel {
	private JTextField tfKorrelationskoeffizient = new JTextField();
	private JTextField tfAbweichung = new JTextField();
	
	private JPanel panelFill = new JPanel();

	
	public InputCardVertifizieren (Controller controller){
		this.setLayout(new GridBagLayout());
		this.setBackground(GlobalSettings.colorBackground);
		
		JPanel panelKorrelationskoeffizient = new JPanel(new GridLayout(1, 3, 10, 10));
		panelKorrelationskoeffizient.setBackground(GlobalSettings.colorBackground);
		panelKorrelationskoeffizient.setBorder(MyBorderFactory.createMyBorder("Korrelationskoeffizient"));
		panelKorrelationskoeffizient.add(new JLabel("Koeffizient:"));
		panelKorrelationskoeffizient.add(tfKorrelationskoeffizient);
				
		JPanel panelAbweichung = new JPanel(new GridLayout(1, 3, 10, 10));
		panelAbweichung.setBackground(GlobalSettings.colorBackground);
		panelAbweichung.setBorder(MyBorderFactory.createMyBorder("relative Abweichung"));
		panelAbweichung.add(new JLabel("Abweichung:"));
		panelAbweichung.add(tfAbweichung);
		
		panelFill.setBackground(GlobalSettings.colorBackground);
		
		
		this.add(panelKorrelationskoeffizient, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		this.add(panelAbweichung, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		this.add(panelFill, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		
	}

}
