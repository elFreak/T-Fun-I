package userInterface.IOCard;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;

import projectT_Fun_I.GlobalSettings;
import userInterface.Controller;
import userInterface.MyBorderFactory;

/**
 * 
 * @author Simon Zoller
 *
 */

public class InputCardVerifizieren extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	Controller controller;

	private JPanel panelOrder = new JPanel(new GridLayout(5, 2));

	JRadioButton rbOrder[] = new JRadioButton[9];

	ButtonGroup bgOrder = new ButtonGroup();

	public InputCardVerifizieren(Controller controller) {
		this.controller = controller;

		// panelOrder
		panelOrder.setBackground(GlobalSettings.colorBackground);
		panelOrder.setBorder(MyBorderFactory.createMyBorder("Ordnung"));

		panelOrder.add(new JLabel(""));
		for (int i = 0; i < rbOrder.length; i++) {
			rbOrder[i] = new JRadioButton("" + (i + 2));
			rbOrder[i].setBackground(GlobalSettings.colorBackground);
			rbOrder[i].setActionCommand("" + i);
			rbOrder[i].addActionListener(this);
			bgOrder.add(rbOrder[i]);
			panelOrder.add(rbOrder[i]);
		}

		// Cardpanel konfigurieren
		this.setLayout(new GridBagLayout());
		this.setBackground(GlobalSettings.colorBackground);

		this.add(panelOrder, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.NORTH,
				GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));
		this.add(new JLabel(""), new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0));

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		controller.setVerifizierenOrder(Integer.parseInt(e.getActionCommand()));
	}

}
