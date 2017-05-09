package IOCard;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
import userInterface.Controller;
import userInterface.MyBorderFactory;

/**
 * 
 * @author Marc de Bever
 *
 */

public class InputCardBearbeiten extends JPanel {
	private static final long serialVersionUID = 1L;

	Controller controller;

	private JPanel panelFiltern = new JPanel(new GridBagLayout());
	private JTextField tfFilter = new JTextField("10");

	private JPanel panelRahmen = new JPanel(new GridBagLayout());
	private JTextField tfOffset = new JTextField("0");
	private JTextField tfBereichUnten = new JTextField("0");
	private JTextField tfBereichOben = new JTextField("0");
	private JButton btAutoRahmen = new JButton("Auto");

	/**
	 * Konstructor
	 * 
	 * @param controller
	 */
	public InputCardBearbeiten(Controller controller) {
		this.controller = controller;

		// Textfeld Filter konfigurieren
		// Anonymer MouseWheelListener für Testfeld
		tfFilter.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				int tempValue = (int) Double.parseDouble(tfFilter.getText());
				tempValue += (int) (e.getWheelRotation() * 1);
				if (tempValue < 0) {
					tempValue = 0;
				}
				tfFilter.setText(String.valueOf(tempValue));
				controller.filterChanged(tempValue);
			}
		});

		// Anonymer ActionListener für Textfeld bei Enter
		tfFilter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int tempValue = (int) Double.parseDouble(tfFilter.getText());
				if (tempValue < 0) {
					tempValue = 0;
				}
				tfFilter.setText(String.valueOf(tempValue));
				controller.filterChanged(tempValue);
			}
		});

		// Panel Filtern konfigurieren
		panelFiltern.setBackground(GlobalSettings.colorBackground);
		panelFiltern.setBorder(MyBorderFactory.createMyBorder("Mittelwertfilter"));

		panelFiltern.add(new JLabel("Intensit.:"), new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		panelFiltern.add(tfFilter, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));

		// Panel Rahmen konfigurieren
		panelRahmen.setBackground(GlobalSettings.colorBackground);
		panelRahmen.setBorder(MyBorderFactory.createMyBorder("Offset und Bereich"));

		panelRahmen.add(new JLabel("Offset:"), new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		panelRahmen.add(tfOffset, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 20, 0));

		panelRahmen.add(new Label("Beginn: "), new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		panelRahmen.add(tfBereichOben, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 20, 0));
		panelRahmen.add(new Label("Ende: "), new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		panelRahmen.add(tfBereichUnten, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 20, 0));

		panelRahmen.add(btAutoRahmen, new GridBagConstraints(2, 0, 1, 3, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));

		// Cardpanel konfigurieren
		this.setLayout(new GridBagLayout());
		this.setBackground(GlobalSettings.colorBackground);

		this.add(panelFiltern, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.NORTH,
				GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		this.add(panelRahmen, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		this.add(new JPanel(), new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));

	}

}
