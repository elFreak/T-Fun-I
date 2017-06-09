package userInterface.IOCard;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import model.Model;
import projectT_Fun_I.GlobalSettings;
import userInterface.Controller;
import userInterface.MyBorderFactory;
import userInterface.StatusBar;

/**
 * Oberfläche für Eingaben im Zustand Berechnen.
 * 
 * @author Team 1
 *
 */
public class InputCardBerechnen extends JPanel implements ActionListener, MouseListener {
	private static final long serialVersionUID = 1L;

	Controller controller;

	private JPanel panelOrdnung = new JPanel(new GridBagLayout());
	private JButton btAll = new JButton("Alle");
	private JButton btNone = new JButton("Keine");
	private JCheckBox[] cB = new JCheckBox[10];
	private JLabel[] lB = new JLabel[10];

	private JPanel panelButton = new JPanel(new GridBagLayout());
	private JButton btLoeschen = new JButton("Neustart");
	private JTextField tfThreshold = new JTextField(String.valueOf(GlobalSettings.startValueThreshold));
	private JTextField tfNorm = new JTextField(String.valueOf(GlobalSettings.startValueAnzahlWerte));

	/**
	 * Erzeugt und initialisiert das Objekt.
	 * 
	 * @param controller
	 */
	public InputCardBerechnen(Controller controller) {
		this.controller = controller;

		// Gütebestimmung Threshold
		panelOrdnung.setBackground(GlobalSettings.colorBackground);
		panelOrdnung.setBorder(MyBorderFactory.createMyBorder("Ordnung anzeigen"));

		for (int i = 0; i < cB.length; i++) {
			cB[i] = new JCheckBox();
			cB[i].setOpaque(false);
			cB[i].addActionListener(this);
			cB[i].setEnabled(false);
			cB[i].setOpaque(true);
			cB[i].setBackground(GlobalSettings.colorsTraceSolution[i]);
			cB[i].addMouseListener(this);
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
		panelOrdnung.add(btAll, new GridBagConstraints(0, 5, 4, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));
		panelOrdnung.add(btNone, new GridBagConstraints(0, 6, 4, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));

		// Panel Button
		panelButton.setBackground(GlobalSettings.colorBackground);
		panelButton.setBorder(MyBorderFactory.createMyBorder("Zurücksetzen"));
		panelButton.add(btLoeschen, new GridBagConstraints(0, 2, 2, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));
		panelButton.add(new JLabel(" Threshold: ", SwingConstants.LEFT), new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		panelButton.add(tfThreshold, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));
		panelButton.add(new JLabel(" Anzahl Werte: ", SwingConstants.LEFT), new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		panelButton.add(tfNorm, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));

		btLoeschen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				readTFNorm();
				readTFThreshold();
				controller.berechnungLoeschen();
			}
		});

		// Anonymer ActionListener für Textfeld Threshold und Textfeld Norm bei
		// Enter
		tfThreshold.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				readTFThreshold();
			}
		});

		tfNorm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				readTFNorm();
			}
		});

		// Cardpanel konfigurieren
		this.setBackground(GlobalSettings.colorBackground);

		// Cardpanel konfigurieren
		this.setLayout(new GridBagLayout());
		this.setBackground(GlobalSettings.colorBackground);

		this.add(panelOrdnung, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.NORTH,
				GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));
		this.add(panelButton, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		JPanel panelBackground2 = new JPanel();
		panelBackground2.setBackground(GlobalSettings.colorBackground);
		this.add(panelBackground2, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0));

		btAll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (cB[0].isEnabled() == false) {
					StatusBar.showStatus("Bitte warten, bis alle Startwerte berechnet sind.", StatusBar.FEHLER);
				} else {
					boolean[] active = new boolean[cB.length];
					for (int i = 0; i < cB.length; i++) {
						cB[i].setSelected(true);
						controller.calculateUTF(i + 1);
						active[i] = cB[i].isSelected();
					}
					controller.setBerechnenCBActive(active);
				}
			}
		});

		btNone.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (cB[0].isEnabled() == false) {
					StatusBar.showStatus("Bitte warten, bis alle Startwerte berechnet sind.", StatusBar.FEHLER);
				} else {
					boolean[] active = new boolean[cB.length];
					for (int i = 0; i < cB.length; i++) {
						cB[i].setSelected(false);
						active[i] = cB[i].isSelected();
					}
					controller.setBerechnenCBActive(active);
				}
			}
		});

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(cB[0])) {
			controller.calculateUTF(1);
		}
		if (e.getSource().equals(cB[1])) {
			controller.calculateUTF(2);
		}
		if (e.getSource().equals(cB[2])) {
			controller.calculateUTF(3);
		}
		if (e.getSource().equals(cB[3])) {
			controller.calculateUTF(4);
		}
		if (e.getSource().equals(cB[4])) {
			controller.calculateUTF(5);
		}
		if (e.getSource().equals(cB[5])) {
			controller.calculateUTF(6);
		}
		if (e.getSource().equals(cB[6])) {
			controller.calculateUTF(7);
		}
		if (e.getSource().equals(cB[7])) {
			controller.calculateUTF(8);
		}
		if (e.getSource().equals(cB[8])) {
			controller.calculateUTF(9);
		}
		if (e.getSource().equals(cB[9])) {
			controller.calculateUTF(10);
		}
		boolean[] active = new boolean[cB.length];
		for (int i = 0; i < cB.length; i++) {
			active[i] = cB[i].isSelected();
		}
		controller.setBerechnenCBActive(active);
	}

	private void readTFThreshold() {
		try {
			double tempValue = Double.parseDouble(tfThreshold.getText());
			if (tempValue <= 0) {
				tempValue = 1e-20;
				StatusBar.showStatus("Threshold muss grösser als 0 sein.", StatusBar.FEHLER);
			}
			controller.setThresholdAndNorm(tempValue, Integer.parseInt(tfNorm.getText()));
		} catch (NumberFormatException exp) {
			StatusBar.showStatus("Ungültige Eingabe:\nBitte nur Zahlen eingeben", StatusBar.FEHLER);
		}
	}

	private void readTFNorm() {
		try {
			int tempValue = Integer.parseInt(tfNorm.getText());
			if (tempValue < 10) {
				tempValue = 10;
				StatusBar.showStatus("Anzahl Werte muss midestens 10 betragen.", StatusBar.FEHLER);
			}
			controller.setThresholdAndNorm(Double.parseDouble(tfThreshold.getText()), tempValue);
		} catch (NumberFormatException exp) {
			StatusBar.showStatus("Ungültige Eingabe:\nBitte nur Zahlen eingeben", StatusBar.FEHLER);
		}
	}

	/**
	 * Updated das Objekt.
	 * 
	 * @see Observer
	 * 
	 * @param obs
	 * @param obj
	 */
	public void update(java.util.Observable obs, Object obj) {
		int reason = (int) obj;
		Model model = (Model) obs;

		switch (reason) {
		case Model.NOTIFY_REASON_NETWORK_START_VALUES:
			for (int i = 0; i < cB.length; i++) {
				cB[i].setEnabled(true);
			}
			break;
		case Model.NOTIFY_REASON_THRESHOLD_OR_NORM_CHANGED:
			tfThreshold.setText(String.valueOf(model.getNextThreshold()));
			tfNorm.setText(String.valueOf(model.measurementData.getNormNumberOfData()));

			break;
		case Model.NOTIFY_REASON_UPDATE_NETWORK:
			for (int i = 0; i < cB.length; i++) {
				cB[i].setSelected(false);
				cB[i].setEnabled(false);
			}
			break;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (((JCheckBox) e.getSource()).isEnabled() == false) {
			StatusBar.showStatus("Bitte warten, bis alle Startwerte berechnet sind.", StatusBar.FEHLER);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * Gibt zurück welche {@link JCheckBox} aktiv sind.
	 * 
	 * @return state[boolean]
	 */
	public boolean[] getCBState() {
		boolean[] state = new boolean[10];
		for (int i = 0; i < cB.length; i++) {
			state[i] = cB[i].isSelected();
		}
		return state;
	}

}
