import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.AncestorListener;

public class Tabs extends JTabbedPane implements Observer, ActionListener {

	Controller controller;
	JPanel tabInput = new JPanel(new GridBagLayout());
	JTextField dateipfad = new JTextField("Dateipfad");

	JPanel tabEinstellungen = new JPanel(new GridBagLayout());
	JPanel tabFenster = new JPanel(new GridBagLayout());
	JPanel tabPlot = new JPanel(new GridBagLayout());

	public Tabs(Controller controller) {
		
		setTabPlacement(JTabbedPane.TOP);

		// --------------------------------------------------------//
		// Erstellen der Tabs

		addTab("   Einlesen   ", tabInput);
		addTab("   Bearbeiten ", tabPlot);		//Marc 2
		addTab("   Tab Adi    ", tabEinstellungen);
		addTab("  Fenster     ", tabFenster);
		
		
		// --------------------------------------------------------//
		// Input-Tab
		
		JButton datei = new JButton("Datei einfügen");
		datei.setActionCommand("Datei");
		datei.addActionListener(this);
		tabInput.add(datei, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		tabInput.add(dateipfad, new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		dateipfad.setPreferredSize(new Dimension(200,20));
		
		JLabel Ordnung = new JLabel("Ordnung:");
		tabInput.add(Ordnung, new GridBagConstraints(0, 2, 1, 1, 1.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

		JCheckBox[] box = new JCheckBox[10];
		for (int i = 0; i < 10; i++) {
			box[i] = new JCheckBox("" + (i + 1));
			if (i < 5)
				tabInput.add(box[i], new GridBagConstraints(0, 3 + i, 1, 1, 1.0, 0.0, GridBagConstraints.WEST,
						GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			else
				tabInput.add(box[i], new GridBagConstraints(1, 3 + i - 5, 1, 1, 1.0, 0.0, GridBagConstraints.WEST,
						GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			box[i].addActionListener(this);
			box[i].setActionCommand("box"+i);
		}

		JLabel[] inL = new JLabel[5];
		JTextField[] inTxt = new JTextField[5];
		String[] L = { "Eingabe1", "Eingabe2", "Eingabe3", "Eingabe4", "Eingabe5" };
		for (int i = 0; i < 5; i++) {
			inL[i] = new JLabel(L[i]);
			inTxt[i] = new JTextField();
			inTxt[i].addActionListener(this);
			inTxt[i].setActionCommand(L[i]);
			tabInput.add(inL[i], new GridBagConstraints(0, 8 + i, 1, 1, 1.0, 0.0, GridBagConstraints.WEST,
					GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
			tabInput.add(inTxt[i], new GridBagConstraints(1, 8 + i, 1, 1, 1.0, 0.0, GridBagConstraints.WEST,
					GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 100, 0));
		}
		
		tabInput.add(new JLabel(),new GridBagConstraints(1, 15, 1, 1, 1.0, 1.0, GridBagConstraints.WEST,
				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		
		// --------------------------------------------------------//
		//Einstellungen-Tab
		
		JLabel[] inEL = new JLabel[5];
		JSpinner[] inESpin = new JSpinner[5];
		String[] EL = { "Eingabe1", "Eingabe2", "Eingabe3", "Eingabe4", "Eingabe5" };
		for (int i = 0; i < 5; i++) {
			inEL[i] = new JLabel(L[i]);
			inESpin[i] = new JSpinner();
//			inESpin[i].addAncestorListener((AncestorListener) this);
			//inESpin[i].setActionCommand(L[i]);
			tabEinstellungen.add(inEL[i], new GridBagConstraints(0, i, 1, 1, 1.0, 0.0, GridBagConstraints.WEST,
					GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
			tabEinstellungen.add(inESpin[i], new GridBagConstraints(1, i, 1, 1, 1.0, 0.0, GridBagConstraints.WEST,
					GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 100, 0));
		}
		
		tabEinstellungen.add(new JLabel(),new GridBagConstraints(0, 5, 1, 1, 1.0, 1.0, GridBagConstraints.WEST,
				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 100, 0));
		
		
		// --------------------------------------------------------//
		//Fenster-Tab
		
		// --------------------------------------------------------//
		//Plot-Tab

		this.setSelectedComponent(tabInput);
	}

	public void update(Observable obs, Object obj) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		StatusBar.showStatus(e.getActionCommand());
		
		if(e.getActionCommand().equals("Datei")){
			StatusBar.showStatus("Wählen sie eine Datei aus.");
			
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
			int result = fileChooser.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
			    File selectedFile = fileChooser.getSelectedFile();
			    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
			    dateipfad.setText(selectedFile.getAbsolutePath());
			
			}
		}
	}

}
