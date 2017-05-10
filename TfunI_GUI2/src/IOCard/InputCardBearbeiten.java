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

import projectT_Fun_I.GlobalSettings;
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
	private JSlider sFilter = new JSlider();


	private JPanel panelRahmen = new JPanel(new GridBagLayout());
	private JTextField tfOffset = new JTextField("0");
	private JTextField tfBereichUnten = new JTextField("0");
	private JTextField tfBereichOben = new JTextField("0");
	private JButton btAutoRahmen = new JButton("Auto");
	
	private JPanel panelSprung = new JPanel(new GridBagLayout());
	private JTextField tfSprungzeit = new JTextField("10");
	private JTextField tfSprunghöhe = new JTextField("10");
	private JButton btSprung = new JButton("Sprung zurücksetzen");



	/**
	 * Konstructor
	 * 
	 * @param controller
	 */
	public InputCardBearbeiten(Controller controller) {
		this.controller = controller;

		// Textfelder werden konfiguriert:
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

		// Anonymer MouseWheelListener für Testfeld
		tfOffset.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				double tempValue = Double.parseDouble(tfOffset.getText());
				tempValue += (e.getWheelRotation() * (0.05 * tempValue + 0.05));
				if (tempValue < 0) {
					tempValue = 0;
				}
				tfOffset.setText(String.valueOf(tempValue));
				controller.setRange(Double.parseDouble(tfBereichOben.getText()),
						Double.parseDouble(tfBereichUnten.getText()), Double.parseDouble(tfOffset.getText()));
			}
		});

		// Anonymer MouseWheelListener für Testfeld
		tfBereichUnten.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				double tempValue = Double.parseDouble(tfBereichUnten.getText());
				tempValue += (e.getWheelRotation() * (0.05 * tempValue + 0.05));
				if (tempValue < 0) {
					tempValue = 0;
				}
				tfBereichUnten.setText(String.valueOf(tempValue));
				controller.setRange(Double.parseDouble(tfBereichOben.getText()),
						Double.parseDouble(tfBereichUnten.getText()), Double.parseDouble(tfOffset.getText()));
			}
		});

		// Anonymer MouseWheelListener für Testfeld
		tfBereichOben.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				double tempValue = Double.parseDouble(tfBereichOben.getText());
				tempValue += (e.getWheelRotation() * (0.05 * tempValue + 0.05));
				if (tempValue < 0) {
					tempValue = 0;
				}
				tfBereichOben.setText(String.valueOf(tempValue));
				controller.setRange(Double.parseDouble(tfBereichOben.getText()),
						Double.parseDouble(tfBereichUnten.getText()), Double.parseDouble(tfOffset.getText()));
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

		// Anonymer ActionListener für Textfeld bei Enter
		tfOffset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				double tempValue = Double.parseDouble(tfOffset.getText());
				if (tempValue < 0) {
					tempValue = 0;
					tfOffset.setText("" + tempValue);
				}

				controller.setRange(Double.parseDouble(tfBereichUnten.getText()),
						Double.parseDouble(tfBereichOben.getText()), Double.parseDouble(tfOffset.getText()));
			}
		});

		// Anonymer ActionListener für Textfeld bei Enter
		tfBereichUnten.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				double tempValue = Double.parseDouble(tfBereichUnten.getText());
				if (tempValue < 0) {
					tempValue = 0;
					tfBereichUnten.setText("" + tempValue);
				}

				controller.setRange(Double.parseDouble(tfBereichUnten.getText()),
						Double.parseDouble(tfBereichOben.getText()), Double.parseDouble(tfOffset.getText()));
			}
		});

		// Anonymer ActionListener für Textfeld bei Enter
		tfBereichOben.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				double tempValue = Double.parseDouble(tfBereichOben.getText());
				if (tempValue < 0) {
					tempValue = 0;
					tfBereichOben.setText("" + tempValue);
				}

				controller.setRange(Double.parseDouble(tfBereichUnten.getText()),
						Double.parseDouble(tfBereichOben.getText()), Double.parseDouble(tfOffset.getText()));
			}
		});

		// Panel Filtern konfigurieren
		panelFiltern.setBackground(GlobalSettings.colorBackground);
		panelFiltern.setBorder(MyBorderFactory.createMyBorder("Mittelwertfilter"));
		sFilter.setMinimum(0);
		sFilter.setMaximum(30);
		sFilter.setValue(0);
		sFilter.setMinorTickSpacing(10);
		sFilter.setMajorTickSpacing(10);
		sFilter.setPaintTicks(true);    //Striche werden angezeigt
		sFilter.setPaintLabels(true);   //Zahlen werden angezeigt
		sFilter.setPaintTrack(true);    //Balken wird angezeigt
		sFilter.setOpaque(false);

		panelFiltern.add(new JLabel("Intensit.:"), new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		panelFiltern.add(tfFilter, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		panelFiltern.add(sFilter, new GridBagConstraints(0, 1, 2, 1, 1.0, 0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		

		// Panel Rahmen konfigurieren
		panelRahmen.setBackground(GlobalSettings.colorBackground);
		panelRahmen.setBorder(MyBorderFactory.createMyBorder("Offset und Bereich"));

		panelRahmen.add(new JLabel("Offset:"), new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		panelRahmen.add(tfOffset, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 20, 0));
		panelRahmen.add(new JLabel("V"), new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));

		panelRahmen.add(new Label("Beginn:"), new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		panelRahmen.add(tfBereichOben, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 20, 0));
		panelRahmen.add(new JLabel("s"), new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		
		panelRahmen.add(new Label("Ende:"), new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		panelRahmen.add(tfBereichUnten, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 20, 0));
		panelRahmen.add(new JLabel("s"), new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));

		panelRahmen.add(btAutoRahmen, new GridBagConstraints(0, 3, 3, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		
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
		panelSprung.add(btSprung, new GridBagConstraints(0, 2, 3, 1, 1.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 20, 0));
		
		
		

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
