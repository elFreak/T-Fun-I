package userInterface.IOCard;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import projectT_Fun_I.GlobalSettings;
import userInterface.Controller;
import userInterface.MyBorderFactory;

public class InputCardBerechnen extends JPanel {
	private static final long serialVersionUID = 1L;

	Controller controller;

	private JPanel panelOrdnung = new JPanel(new GridBagLayout());
	private JCheckBox cb1 = new JCheckBox();
	private JCheckBox cb2 = new JCheckBox();
	private JCheckBox cb3 = new JCheckBox();
	private JCheckBox cb4 = new JCheckBox();
	private JCheckBox cb5 = new JCheckBox();
	private JCheckBox cb6 = new JCheckBox();
	private JCheckBox cb7 = new JCheckBox();
	private JCheckBox cb8 = new JCheckBox();
	private JCheckBox cb9 = new JCheckBox();
	private JCheckBox cb10 = new JCheckBox();

	private JPanel panelButton = new JPanel(new GridBagLayout());
	private JButton btBerechnen = new JButton("Berechnen");
	private JButton btAbbrechen = new JButton("Abbrechen");

	public InputCardBerechnen(Controller controller) {

		// Gütebestimmung Filter
		panelOrdnung.setBackground(GlobalSettings.colorBackground);
		panelOrdnung.setBorder(MyBorderFactory.createMyBorder("Ordnung anzeigen"));
		cb1.setOpaque(false);
		cb2.setOpaque(false);
		cb3.setOpaque(false);
		cb4.setOpaque(false);
		cb5.setOpaque(false);
		cb6.setOpaque(false);
		cb7.setOpaque(false);
		cb8.setOpaque(false);
		cb9.setOpaque(false);
		cb10.setOpaque(false);

		panelOrdnung.add(new JLabel("1"), new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(20, 0, 10, 10), 0, 0));
		panelOrdnung.add(new JLabel("2"), new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(20, 0, 10, 10), 0, 0));
		panelOrdnung.add(new JLabel("3"), new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(20, 0, 10, 10), 0, 0));
		panelOrdnung.add(new JLabel("4"), new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(20, 0, 10, 10), 0, 0));
		panelOrdnung.add(new JLabel("5"), new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(20, 0, 10, 10), 0, 0));
		panelOrdnung.add(new JLabel("6"), new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(20, 0, 10, 10), 0, 0));
		panelOrdnung.add(new JLabel("7"), new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
				GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(20, 0, 10, 10), 0, 0));
		panelOrdnung.add(new JLabel("8"), new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0,
				GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(20, 0, 10, 10), 0, 0));
		panelOrdnung.add(new JLabel("9"), new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
				GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(20, 0, 10, 10), 0, 0));
		panelOrdnung.add(new JLabel("10"), new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0,
				GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(20, 0, 10, 10), 0, 0));
		panelOrdnung.add(cb1, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(20, 0, 10, 40), 0, 0));
		panelOrdnung.add(cb2, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(20, 0, 10, 40), 0, 0));
		panelOrdnung.add(cb3, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(20, 0, 10, 40), 0, 0));
		panelOrdnung.add(cb4, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(20, 0, 10, 40), 0, 0));
		panelOrdnung.add(cb5, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(20, 0, 10, 40), 0, 0));
		panelOrdnung.add(cb6, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(20, 0, 10, 40), 0, 0));
		panelOrdnung.add(cb7, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(20, 0, 10, 40), 0, 0));
		panelOrdnung.add(cb8, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(20, 0, 10, 40), 0, 0));
		panelOrdnung.add(cb9, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(20, 0, 10, 40), 0, 0));
		panelOrdnung.add(cb10, new GridBagConstraints(3, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(20, 0, 10, 40), 0, 0));

		// Button
		panelButton.setBackground(GlobalSettings.colorBackground);
		panelButton.setBorder(MyBorderFactory.createMyBorder("Cockpit"));

		panelButton.add(btBerechnen, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		panelButton.add(btAbbrechen, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));

		// Cardpanel konfigurieren
		this.setBackground(GlobalSettings.colorBackground);

		// Anonymer ActionListener für Berechnen
		btBerechnen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.calculateUTF();

			}
		});

		// Cardpanel konfigurieren
		this.setLayout(new GridBagLayout());
		this.setBackground(GlobalSettings.colorBackground);

		this.add(panelOrdnung, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0, GridBagConstraints.NORTH,
				GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		this.add(panelButton, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.NORTH,
				GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		JPanel panelBackground2 = new JPanel();
		panelBackground2.setBackground(GlobalSettings.colorBackground);
		this.add(panelBackground2, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.BOTH, new Insets(20, 10, 10, 10), 0, 0));

	}

}
