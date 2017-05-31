package userInterface.IOCard;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import model.Model;
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

	private JPanel panelOrdnung = new JPanel(new GridBagLayout());
	private JCheckBox[] cB = new JCheckBox[10];
	private JLabel[] lB = new JLabel[10];

	public InputCardVerifizieren(Controller controller) {
		this.controller = controller;

		panelOrdnung.setBackground(GlobalSettings.colorBackground);
		panelOrdnung.setBorder(MyBorderFactory.createMyBorder("Ordnung anzeigen"));

		for (int i = 0; i < cB.length; i++) {
			cB[i] = new JCheckBox();
			cB[i].setOpaque(false);
			cB[i].addActionListener(this);
			cB[i].setEnabled(false);
			cB[i].setOpaque(true);
			cB[i].setBackground(GlobalSettings.colorsTraceSolution[i]);
			lB[i] = new JLabel();
			lB[i].setText("" + (i + 1) + ": ");
			lB[i].setHorizontalAlignment(SwingConstants.RIGHT);
		}

		panelOrdnung.add(lB[0], new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
		panelOrdnung.add(lB[1], new GridBagConstraints(2, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
		panelOrdnung.add(lB[2], new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
		panelOrdnung.add(lB[3], new GridBagConstraints(2, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
		panelOrdnung.add(lB[4], new GridBagConstraints(0, 2, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
		panelOrdnung.add(lB[5], new GridBagConstraints(2, 2, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
		panelOrdnung.add(lB[6], new GridBagConstraints(0, 3, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
		panelOrdnung.add(lB[7], new GridBagConstraints(2, 3, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
		panelOrdnung.add(lB[8], new GridBagConstraints(0, 4, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
		panelOrdnung.add(lB[9], new GridBagConstraints(2, 4, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));

		panelOrdnung.add(cB[0], new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		panelOrdnung.add(cB[1], new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		panelOrdnung.add(cB[2], new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		panelOrdnung.add(cB[3], new GridBagConstraints(3, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		panelOrdnung.add(cB[4], new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		panelOrdnung.add(cB[5], new GridBagConstraints(3, 2, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		panelOrdnung.add(cB[6], new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		panelOrdnung.add(cB[7], new GridBagConstraints(3, 3, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		panelOrdnung.add(cB[8], new GridBagConstraints(1, 4, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		panelOrdnung.add(cB[9], new GridBagConstraints(3, 4, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

		// Cardpanel konfigurieren
		this.setBackground(GlobalSettings.colorBackground);

		// Cardpanel konfigurieren
		this.setLayout(new GridBagLayout());
		this.setBackground(GlobalSettings.colorBackground);

		this.add(panelOrdnung, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.NORTH,
				GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));

		JPanel panelBackground2 = new JPanel();
		panelBackground2.setBackground(GlobalSettings.colorBackground);
		this.add(panelBackground2, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0));
	}

	public void actionPerformed(ActionEvent e) {

		int order = 1;
		if (e.getSource().equals(cB[0])) {
			order = 1;
		}
		if (e.getSource().equals(cB[1])) {
			order = 2;
		}
		if (e.getSource().equals(cB[2])) {
			order = 3;
		}
		if (e.getSource().equals(cB[3])) {
			order = 4;
		}
		if (e.getSource().equals(cB[4])) {
			order = 5;
		}
		if (e.getSource().equals(cB[5])) {
			order = 6;
		}
		if (e.getSource().equals(cB[6])) {
			order = 7;
		}
		if (e.getSource().equals(cB[7])) {
			order = 8;
		}
		if (e.getSource().equals(cB[8])) {
			order = 9;
		}
		if (e.getSource().equals(cB[9])) {
			order = 10;
		}
		for (int i = 0; i < cB.length; i++) {
			cB[i].setSelected(false);
		}
		controller.setVerifizierenOrder(order);
		cB[order-1].setSelected(true);
	}

	public void update(Observable obs, Object obj) {
		Model model = (Model) obs;
		int reason = (int) obj;

		if (reason == Model.NOTIFY_REASON_APPROXIMATION_UPDATE) {
			for (int i = 0; i < cB.length; i++) {
				if (model.network.getApprox(i + 1) != null) {
					if (model.network.getApprox(i + 1).isDone()) {
						cB[i].setEnabled(true);
					} else {
						cB[i].setEnabled(false);
						cB[i].setSelected(false);
					}
				} else {
					cB[i].setEnabled(false);
					cB[i].setSelected(false);
				}
			}
		}
	}

	public void setOrder(int order) {
		for (int i = 0; i < cB.length; i++) {
			cB[i].setSelected(false);
		}
		controller.setVerifizierenOrder(order);
		cB[order-1].setSelected(true);	
	}

}
