package userInterface.IOCard;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.Model;
import projectT_Fun_I.GlobalSettings;
import userInterface.Controller;
import userInterface.MyBorderFactory;
import userInterface.Numbers;
import userInterface.StatusBar;

/**
 * Oberfl�che f�r Eingaben im Zustand Bearbeiten.
 * 
 * @author Team 1
 *
 */
public class InputCardBearbeiten extends JPanel {
	private static final long serialVersionUID = 1L;
	// Attributte
	// ------------------------------------------------------------------------------------------------------

	private JPanel panelFiltern = new JPanel(new GridBagLayout());
	private JTextField tfFilter = new JTextField("0");
	private JSlider sFilter = new JSlider();

	private JPanel panelRahmen = new JPanel(new GridBagLayout());
	private JTextField tfOffset = new JTextField("0");
	private JTextField tfTail = new JTextField("0");
	private JButton btAutoRahmen = new JButton("Auto");

	private JPanel panelSprung = new JPanel(new GridBagLayout());
	private JTextField tfSprungzeit = new JTextField("10");
	private JTextField tfSprunghoehe = new JTextField("10");
	private JButton btSprung = new JButton("Sprung zur�cksetzen");
	// ------------------------------------------------------------------------------------------------------

	// Konstruktor
	// ------------------------------------------------------------------------------------------------------
	/**
	 * Erzeugt und initialisiert das Objekt.
	 * 
	 * @param controller
	 */
	public InputCardBearbeiten(Controller controller) {

		// Filtern
		// -----------------------------------------------------

		// Anonymer ActionListener f�r Textfeld Filtern bei Enter
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
					StatusBar.showStatus("Ung�ltige Eingabe:\nBitte nur Zahlen eingeben", StatusBar.FEHLER);
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

		// Anonymer ActionListener f�r Schieberegler
		sFilter.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int tempValue = sFilter.getValue();

				tfFilter.setText(String.valueOf(tempValue));
				controller.filterChanged(tempValue);
			}
		});

		// Panel Filtern Konfigurieren

		// Anonymer MouseWheelListener f�r tfFiltern
		panelFiltern.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if (tfFilter.getText().isEmpty())
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
		// Anonymer MouseWheelListener f�r Textfeld Offset
		tfOffset.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if (tfOffset.getText().isEmpty())
					tfOffset.setText("0.0");

				double tempValue = Double.parseDouble(tfOffset.getText());
				tempValue = tempValue * Math.pow(1.1, e.getWheelRotation()) + 0.000001 * e.getWheelRotation();

				controller.setRange(Double.parseDouble(tfTail.getText()), tempValue);
			}
		});

		// Anonymer ActionListener f�r Textfeld bei Enter
		tfOffset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					double tempValue = Double.parseDouble(tfOffset.getText());

					controller.setRange(Double.parseDouble(tfTail.getText()), tempValue);
				} catch (NumberFormatException exp) {
					StatusBar.showStatus("Ung�ltige Eingabe:\nBitte nur Zahlen eingeben", StatusBar.FEHLER);
				}
			}
		});

		// Anonymer MouseWheelListener f�r Textfeld BereichUnten
		tfTail.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if (tfTail.getText().isEmpty())
					tfTail.setText("0");

				double tempValue = Double.parseDouble(tfTail.getText());
				tempValue = tempValue * Math.pow(1.1, e.getWheelRotation()) + 0.000001 * e.getWheelRotation();
				if (tempValue < 0) {
					tempValue = 0;
				}

				controller.setRange(tempValue, Double.parseDouble(tfOffset.getText()));
			}
		});

		// Anonymer ActionListener f�r Textfeld bei Enter
		tfTail.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					double tempValue = Double.parseDouble(tfTail.getText());
					if (tempValue < 0) {
						tempValue = 0;
					}

					controller.setRange(tempValue, Double.parseDouble(tfOffset.getText()));
				} catch (NumberFormatException exp) {
					StatusBar.showStatus("Ung�ltige Eingabe:\nBitte nur Zahlen eingeben", StatusBar.FEHLER);
				}
			}
		});

		// Anonymer ActionListener f�r Auto Button
		btAutoRahmen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.autoLimmits();
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

		panelRahmen.add(new Label("Ende:"), new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		panelRahmen.add(tfTail, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 20, 0));
		panelRahmen.add(new JLabel("s"), new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));

		panelRahmen.add(btAutoRahmen, new GridBagConstraints(0, 2, 3, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));

		// --------------------------------------------------------
		// Sprung

		// Anonymer MouseWheelListener f�r Textfeld Sprungzeit
		tfSprungzeit.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if (tfSprungzeit.getText().isEmpty())
					tfSprungzeit.setText("0");

				double tempValue = Double.parseDouble(tfSprungzeit.getText());
				tempValue = tempValue * Math.pow(1.1, e.getWheelRotation()) + 0.000001 * e.getWheelRotation();
				if (tempValue < 0) {
					tempValue = 0;
				}

				controller.setStep(tempValue, Double.parseDouble(tfSprunghoehe.getText()));
			}
		});

		// Anonymer ActionListener f�r Textfeld bei Enter
		tfSprungzeit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					double tempValue = Double.parseDouble(tfSprungzeit.getText());
					if (tempValue < 0) {
						tempValue = 0;
					}

					controller.setStep(tempValue, Double.parseDouble(tfSprunghoehe.getText()));
				} catch (NumberFormatException exp) {
					StatusBar.showStatus("Ung�ltige Eingabe:\nBitte nur Zahlen eingeben", StatusBar.FEHLER);
				}

			}
		});

		// Anonymer MouseWheelListener f�r Textfeld Sprunghoehe
		tfSprunghoehe.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if (tfSprunghoehe.getText().isEmpty())
					tfSprunghoehe.setText("0");

				double tempValue = Double.parseDouble(tfSprunghoehe.getText());
				tempValue = tempValue * Math.pow(1.1, e.getWheelRotation()) + 0.000001 * e.getWheelRotation();
				if (tempValue < 0) {
					tempValue = 0;
				}

				controller.setStep(Double.parseDouble(tfSprungzeit.getText()), tempValue);
			}
		});

		// Anonymer ActionListener f�r Textfeld bei Enter
		tfSprunghoehe.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					double tempValue = Double.parseDouble(tfSprunghoehe.getText());
					if (tempValue < 0) {
						tempValue = 0;
					}

					controller.setStep(Double.parseDouble(tfSprungzeit.getText()), tempValue);
				} catch (NumberFormatException exp) {
					StatusBar.showStatus("Ung�ltige Eingabe:\nBitte nur Zahlen eingeben", StatusBar.FEHLER);
				}
			}
		});

		btSprung.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.setOriginalStep();
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
		panelSprung.add(new JLabel("Sprunghoehe:"), new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		panelSprung.add(tfSprunghoehe, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.FIRST_LINE_START,
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
		JPanel panelBackground = new JPanel();
		panelBackground.setBackground(GlobalSettings.colorBackground);
		this.add(panelBackground, new GridBagConstraints(0, 3, 1, 1, 1.0, 1.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.BOTH, new Insets(20, 10, 10, 10), 0, 0));

	}

	/**
	 * Updated das Objekt. 
	 * @see Observer
	 * 
	 * @param obs
	 * @param obj
	 */
	public void update(java.util.Observable obs, Object obj) {
		int reason = (int) obj;

		if (reason == Model.NOTIFY_REASON_MEASUREMENT_CHANGED) {
			Numbers offset = new Numbers(((Model) obs).measurementData.getOffset(), 3);
			tfOffset.setText(String.valueOf(offset.number) + offset.unit);
			Numbers tail = new Numbers(((Model) obs).measurementData.getTail(), 3);
			tfTail.setText(String.valueOf(tail.number) + tail.unit);
			Numbers stepHeight = new Numbers(((Model) obs).measurementData.getstepHeight(), 3);
			tfSprunghoehe.setText(String.valueOf(stepHeight.number) + stepHeight.unit);
			Numbers stepTime = new Numbers(((Model) obs).measurementData.getstepTime(), 3);
			tfSprungzeit.setText(String.valueOf(stepTime.number) + stepTime.unit);
			tfFilter.setText(String.valueOf(((Model) obs).measurementData.getFilter()));
			sFilter.setValue(((Model) obs).measurementData.getFilter());
		}

	}

}
