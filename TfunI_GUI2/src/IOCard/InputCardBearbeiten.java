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
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import projectT_Fun_I.GlobalSettings;
import userInterface.Controller;
import userInterface.MyBorderFactory;
import userInterface.StatusBar;

/**
 * 
 * @author Marc de Bever
 *
 */

public class InputCardBearbeiten extends JPanel {
	private static final long serialVersionUID = 1L;
	// Attributte
	// ------------------------------------------------------------------------------------------------------
	Controller controller;

	private JPanel panelFiltern = new JPanel(new GridBagLayout());
	private JTextField tfFilter = new JTextField("0");
	private JSlider sFilter = new JSlider();

	private JPanel panelRahmen = new JPanel(new GridBagLayout());
	private JTextField tfOffset = new JTextField("0");
	private JTextField tfTail = new JTextField("0");
	private JTextField tfDeadtime = new JTextField("0");
	private JButton btAutoRahmen = new JButton("Auto");

	private JPanel panelSprung = new JPanel(new GridBagLayout());
	private JTextField tfSprungzeit = new JTextField("10");
	private JTextField tfSprunghöhe = new JTextField("10");
	private JButton btSprung = new JButton("Sprung zurücksetzen");
	// ------------------------------------------------------------------------------------------------------

	// Konstruktor
	// ------------------------------------------------------------------------------------------------------
	/**
	 * Konstructor
	 * 
	 * @param controller
	 */
	public InputCardBearbeiten(Controller controller) {
		this.controller = controller;

		// Filtern
		// -----------------------------------------------------

		// Anonymer ActionListener für Textfeld Filtern bei Enter
		tfFilter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					int tempValue = (int) Double.parseDouble(tfFilter.getText());
					if (tempValue < 0) {
						tempValue = 0;
					}
					tfFilter.setText(String.valueOf(tempValue));
					if (tempValue > sFilter.getMaximum()) {
						ChangeListener[] changeListener = sFilter.getChangeListeners();
						sFilter.removeChangeListener(changeListener[0]);
						sFilter.setValue(tempValue);
						sFilter.addChangeListener(changeListener[0]);
					} else
						sFilter.setValue(tempValue);
					controller.filterChanged(tempValue);
				} catch (NumberFormatException exp) {
					StatusBar.showStatus("Ungültige Eingabe:\nBitte nur Zahlen eingeben",StatusBar.FEHLER);
				}
			}
		});

		// Schieberegler von Filtern konfigurieren
		sFilter.setMinimum(0);
		sFilter.setMaximum(30);
		sFilter.setValue(0);
		sFilter.setMinorTickSpacing(10);
		sFilter.setMajorTickSpacing(10);
		sFilter.setPaintTicks(true); // Striche werden angezeigt
		sFilter.setPaintLabels(true); // Zahlen werden angezeigt
		sFilter.setPaintTrack(true); // Balken wird angezeigt
		sFilter.setOpaque(false);

		// Anonymer ActionListener für Schieberegler
		sFilter.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int tempValue = sFilter.getValue();

				tfFilter.setText(String.valueOf(tempValue));
				controller.filterChanged(tempValue);
			}
		});

		// Panel Filtern Konfigurieren

		// Anonymer MouseWheelListener für tfFiltern
		panelFiltern.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if(tfFilter.getText().isEmpty())
					tfFilter.setText("0");
				
				int tempValue = (int) Double.parseDouble(tfFilter.getText());
				tempValue += (int) (e.getWheelRotation() * 1);
				if (tempValue < 0) {
					tempValue = 0;
				}
				tfFilter.setText(String.valueOf(tempValue));
				if (tempValue > sFilter.getMaximum()) {
					ChangeListener[] changeListener = sFilter.getChangeListeners();
					sFilter.removeChangeListener(changeListener[0]);
					sFilter.setValue(tempValue);
					sFilter.addChangeListener(changeListener[0]);
				} else
					sFilter.setValue(tempValue);
				controller.filterChanged(tempValue);
			}
		});

		panelFiltern.setBackground(GlobalSettings.colorBackground);
		panelFiltern.setBorder(MyBorderFactory.createMyBorder("Mittelwertfilter"));

		panelFiltern.add(new JLabel("Intensit.:"), new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		panelFiltern.add(tfFilter, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		panelFiltern.add(sFilter, new GridBagConstraints(0, 1, 2, 1, 1.0, 0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		// -----------------------------------------------------

		// Rahmen
		// -----------------------------------------------------
		// Anonymer MouseWheelListener für Textfeld Offset
		tfOffset.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if(tfOffset.getText().isEmpty())
					tfOffset.setText("0");

				double tempValue = Double.parseDouble(tfOffset.getText());
				tempValue += (e.getWheelRotation() * (0.05 * tempValue + 0.05));
				if (tempValue < 0) {
					tempValue = 0;
				}
				tfOffset.setText(String.valueOf(tempValue));
				controller.setRange(Double.parseDouble(tfDeadtime.getText()),
						Double.parseDouble(tfTail.getText()), Double.parseDouble(tfOffset.getText()));
			}
		});

		// Anonymer ActionListener für Textfeld bei Enter
		tfOffset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					double tempValue = Double.parseDouble(tfOffset.getText());
					if (tempValue < 0) {
						tempValue = 0;
						tfOffset.setText("" + tempValue);
					}

					controller.setRange(Double.parseDouble(tfDeadtime.getText()),
							Double.parseDouble(tfTail.getText()), Double.parseDouble(tfOffset.getText()));
				} catch (NumberFormatException exp) {
					StatusBar.showStatus("Ungültige Eingabe:\nBitte nur Zahlen eingeben",StatusBar.FEHLER);
				}
			}
		});

		// Anonymer MouseWheelListener für Textfeld BereichUnten
		tfTail.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if(tfTail.getText().isEmpty())
					tfTail.setText("0");

				double tempValue = Double.parseDouble(tfTail.getText());
				tempValue += (e.getWheelRotation() * (0.05 * tempValue + 0.05));
				if (tempValue < 0) {
					tempValue = 0;
				}
				tfTail.setText(String.valueOf(tempValue));
				controller.setRange(Double.parseDouble(tfDeadtime.getText()),
						Double.parseDouble(tfTail.getText()), Double.parseDouble(tfOffset.getText()));
			}
		});

		// Anonymer ActionListener für Textfeld bei Enter
		tfTail.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					double tempValue = Double.parseDouble(tfTail.getText());
					if (tempValue < 0) {
						tempValue = 0;
						tfTail.setText("" + tempValue);
					}

					controller.setRange(Double.parseDouble(tfDeadtime.getText()),
							Double.parseDouble(tfTail.getText()), Double.parseDouble(tfOffset.getText()));
				} catch (NumberFormatException exp) {
					StatusBar.showStatus("Ungültige Eingabe:\nBitte nur Zahlen eingeben",StatusBar.FEHLER);
				}
			}
		});

		// Anonymer MouseWheelListener für Textfeld BereichOben
		tfDeadtime.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if(tfDeadtime.getText().isEmpty())
					tfDeadtime.setText("0");

				double tempValue = Double.parseDouble(tfDeadtime.getText());
				tempValue += (e.getWheelRotation() * (0.05 * tempValue + 0.05));
				if (tempValue < 0) {
					tempValue = 0;
				}
				tfDeadtime.setText(String.valueOf(tempValue));
				controller.setRange(Double.parseDouble(tfDeadtime.getText()),
						Double.parseDouble(tfTail.getText()), Double.parseDouble(tfOffset.getText()));
			}
		});

		// Anonymer ActionListener für Textfeld bei Enter
		tfDeadtime.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					double tempValue = Double.parseDouble(tfDeadtime.getText());
					if (tempValue < 0) {
						tempValue = 0;
						tfDeadtime.setText("" + tempValue);
					}

					controller.setRange(Double.parseDouble(tfDeadtime.getText()),
							Double.parseDouble(tfTail.getText()), Double.parseDouble(tfOffset.getText()));
				} catch (NumberFormatException exp) {
					StatusBar.showStatus("Ungültige Eingabe:\nBitte nur Zahlen eingeben",StatusBar.FEHLER);
				}
			}
		});

		// Panel Rahmen konfigurieren
		panelRahmen.setBackground(GlobalSettings.colorBackground);
		panelRahmen.setBorder(MyBorderFactory.createMyBorder("Offset und Bereich"));

		panelRahmen.add(new JLabel("Offset:"), new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		panelRahmen.add(tfOffset, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 20, 0));
		panelRahmen.add(new JLabel("V"), new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));

		panelRahmen.add(new Label("Totzeit:"), new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		panelRahmen.add(tfDeadtime, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 20, 0));
		panelRahmen.add(new JLabel("s"), new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));

		panelRahmen.add(new Label("Ende:"), new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		panelRahmen.add(tfTail, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 20, 0));
		panelRahmen.add(new JLabel("s"), new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));

		panelRahmen.add(btAutoRahmen, new GridBagConstraints(0, 3, 3, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));

		// Sprung
		// --------------------------------------------------------
		// Anonymer ActionListener für Textfeld bei Enter
		tfSprungzeit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					double tempValue = Double.parseDouble(tfDeadtime.getText());
					if (tempValue < 0) {
						tempValue = 0;
						tfSprungzeit.setText("" + tempValue);
					}

					controller.setStep(Double.parseDouble(tfSprungzeit.getText()),
							Double.parseDouble(tfSprunghöhe.getText()));
				} catch (NumberFormatException exp) {
					StatusBar.showStatus("Ungültige Eingabe:\nBitte nur Zahlen eingeben",StatusBar.FEHLER);
				}

			}
		});

		// Anonymer ActionListener für Textfeld bei Enter
		tfSprunghöhe.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try{
				double tempValue = Double.parseDouble(tfSprunghöhe.getText());
				if (tempValue < 0) {
					tempValue = 0;
					tfSprunghöhe.setText("" + tempValue);
				}

				controller.setStep(Double.parseDouble(tfSprungzeit.getText()),
						Double.parseDouble(tfSprunghöhe.getText()));
				} catch (NumberFormatException exp) {
					StatusBar.showStatus("Ungültige Eingabe:\nBitte nur Zahlen eingeben",StatusBar.FEHLER);
				}
			}
		});

		// Panel Sprung konfigurieren
		panelSprung.setBackground(GlobalSettings.colorBackground);
		panelSprung.setBorder(MyBorderFactory.createMyBorder("Sprung"));

		panelSprung.add(new JLabel("Sprungzeit:"), new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		panelSprung.add(tfSprungzeit, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		panelSprung.add(new JLabel("s"), new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		panelSprung.add(new JLabel("Sprunghöhe:"), new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		panelSprung.add(tfSprunghöhe, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		panelSprung.add(new JLabel("V"), new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		panelSprung.add(btSprung, new GridBagConstraints(0, 2, 3, 1, 1.0, 0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 20, 0));

		// Cardpanel
		// -----------------------------------------------------------
		// Cardpanel konfigurieren
		this.setLayout(new GridBagLayout());
		this.setBackground(GlobalSettings.colorBackground);

		this.add(panelFiltern, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.NORTH,
				GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		this.add(panelRahmen, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		this.add(panelSprung, new GridBagConstraints(0, 2, 1, 1, 1.0, 0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		this.add(new JPanel(), new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));

	}

}
