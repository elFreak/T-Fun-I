import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class MenuBar extends JMenuBar implements Observer, ActionListener {

	JMenu menu1, menu2, menu3;
	JMenuItem[] menu1Item = new JMenuItem[6];
	String[] menu1Itemtxt = { "Vollbild", "Vordergrund", "Feld2", "Feld3", "Feld4", "Löschen" };
	JMenuItem[] menu2Item = new JMenuItem[6];
	String[] menu2Itemtxt = { "Fenster 1", "Fenster 2", "Fenster 3", "Fenster 4", "Optionen" };
	JMenuItem[] menu3Item = new JMenuItem[6];
	String[] menu3Itemtxt = {"Ad Plot", "Ad Nullstellen", "Plot 3", "Plot4 ", "Plot 5", "Plot 6"};

	JFrame frame;
	Controller controller;

	public MenuBar(Controller controller, JFrame frame) {
		this.frame = frame;
		this.controller = controller;
		menu1 = new JMenu("Datei");
		menu2 = new JMenu("Fenster");
		menu3 = new JMenu("Plot ");

		// --------------------------------------------------------//
		// Datei

		for (int i = 0; i < 6; i++) {
			if (i != 2)
				menu1Item[i] = new JMenuItem(menu1Itemtxt[i]);
			else
				menu1Item[i] = new JMenu("Feld 2");

			if (i == 2)
				menu1.addSeparator();
			menu1.add(menu1Item[i]);
			menu1Item[i].addActionListener(this);
			menu1Item[i].setActionCommand(menu1Itemtxt[i]);
		}
		menu1Item[0].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.ALT_MASK));

		JMenuItem test = new JMenuItem("Test");
		test.addActionListener(this);
		test.setActionCommand("test");
		menu1Item[2].add(test);
		

		menu1.setMnemonic(KeyEvent.VK_D);
		add(menu1);

		// --------------------------------------------------------//
		// Fenster

		for (int i = 0; i < 5; i++) {
			if (i > 3)
				menu2Item[i] = new JMenuItem(menu2Itemtxt[i]);
			else
				menu2Item[i] = new JMenu(menu2Itemtxt[i]);

		}

		menu2.add(menu2Item[0]);
		menu2.add(menu2Item[1]);
		menu2.add(menu2Item[2]);
		menu2.add(menu2Item[3]);

		menu2.addSeparator();
		menu2.add(menu2Item[4]);
		menu2Item[4].addActionListener(this);
		menu2Item[4].setActionCommand(menu2Itemtxt[4]);

		JMenuItem[] sub2 = new JMenuItem[16];
		String[] sub2txt = { "Option 1", "Option 2", "Option 3", "Farbe", "Option 1", "Option 2", "Option 3", "Farbe",
				"Option 1", "Option 2", "Option 3", "Farbe", "Option 1", "Option 2", "Option 3", "Farbe" };

		for (int k = 0; k < 16; k++) {
			sub2[k] = new JMenuItem(sub2txt[k]);
			if (k < 4) {
				menu2Item[0].add(sub2[k]);
			} else if (k < 8) {
				menu2Item[1].add(sub2[k]);
			} else if (k < 12) {
				menu2Item[2].add(sub2[k]);
			} else {
				menu2Item[3].add(sub2[k]);
			}
			sub2[k].addActionListener(this);
			sub2[k].setActionCommand(sub2txt[k] + (k/4));
		}

		menu2.setMnemonic(KeyEvent.VK_F);
		add(menu2);

		// --------------------------------------------------------//
		// Plots
		
		for (int i = 0; i < 5; i++) {
			if (i > 3)
				menu3Item[i] = new JMenuItem(menu3Itemtxt[i]);
			else
				menu3Item[i] = new JMenu(menu3Itemtxt[i]);

		}

		menu3.add(menu3Item[0]);
		menu3.add(menu3Item[1]);
		menu3.add(menu3Item[2]);
		menu3.add(menu3Item[3]);

		menu3.addSeparator();
		menu3.add(menu3Item[4]);
		menu3Item[4].addActionListener(this);
		menu3Item[4].setActionCommand(menu3Itemtxt[4]);


		String[] sub3txt = { "Schritt", "Schrittantwort", "Funktion", "Fehler","Nullstellen", "Option 1", "Option 2", "Option 3", "Farbe",
				"Option 1", "Option 2", "Option 3", "Farbe", "Option 1", "Option 2", "Option 3", "Farbe","","","" };
		JMenuItem[] sub3 = new JMenuItem[sub3txt.length];
		
		for (int k = 0; k < sub3txt.length; k++) {
			sub3[k] = new JMenuItem(sub3txt[k]);
			if (k < 5) {
				menu3Item[0].add(sub3[k]);
			} else if (k < 9) {
				menu3Item[1].add(sub3[k]);
			} else if (k < 13) {
				menu3Item[2].add(sub3[k]);
			} else if (k < 17){
				menu3Item[3].add(sub3[k]);
			}
			sub3[k].addActionListener(this);
			sub3[k].setActionCommand(sub3txt[k] + (k/4));
		}

		menu3.setMnemonic(KeyEvent.VK_P);
		add(menu3);
	}
	/*-----------------Actionevents--------------------------*/

	public void update(Observable o, Object obj) {
	}

	public void actionPerformed(ActionEvent e) {
		
		StatusBar.showStatus(e.getActionCommand());

		if (e.getActionCommand().equals("Vollbild")) {
			StatusBar.showStatus("Vollbild");

			// MVCFramework.fullscreen = 1;
			// MVCFramework.main(menu1Itemtxt);

			menu1Item[0].setText("Teilbild");
			menu1Item[0].setActionCommand("Teilbild");
		}
		if (e.getActionCommand().equals("Teilbild")) {
			StatusBar.showStatus("Teilbild");

			// MVCFramework.fullscreen = 0;
			// MVCFramework.main(menu1Itemtxt);

			menu1Item[0].setText("Vollbild");
			menu1Item[0].setActionCommand("Vollbild");
		}

		/*
		 * if(e.getActionCommand().equals("Plot ausblenden")){
		 * StatusBar.showStatus("Plot ausblenden"); MVCJFramework.p1 = 1;
		 * /MVCJFramework.init(); menuItemPlot.setText("Plot einblenden");
		 * menuItemPlot.setActionCommand("Plot einblenden"); }
		 * if(e.getActionCommand().equals("Plot einblenden")){
		 * StatusBar.showStatus("Plot einblenden"); MVCJFramework.p1 = 0;
		 * menuItemPlot.setText("Plot ausblenden");
		 * menuItemPlot.setActionCommand("Plot ausblenden"); }
		 * 
		 * 
		 * if (e.getActionCommand().equals("What")) {
		 * StatusBar.showStatus("Pressed: What");
		 * menuItemwhat.setText("What the hell");
		 * menuItemwhat.setActionCommand("What the hell"); } if
		 * (e.getActionCommand().equals("What the hell")) {
		 * StatusBar.showStatus("Pressed: What the Hell");
		 * menuItemwhat.setText("What"); menuItemwhat.setActionCommand("What");
		 * }
		 * 
		 * if (e.getActionCommand().equals("Test")) { frame.setResizable(true);
		 * Dimension dim = frame.getSize(); dim.width -= 200;
		 * frame.setSize(dim); menuItemTest.setActionCommand("TestBack");
		 * menuItemTest.setText("Test Back"); } if
		 * (e.getActionCommand().equals("TestBack")) { frame.setResizable(true);
		 * Dimension dim = frame.getSize(); dim.width += 200;
		 * frame.setSize(dim); menuItemTest.setActionCommand("Test");
		 * menuItemTest.setText("Test"); }
		 */

		if (e.getActionCommand().equals("Vordergrund")) {
			StatusBar.showStatus(this, e, e.getActionCommand());
			/*
			 * if (((JFrame) this.getTopLevelAncestor()).isAlwaysOnTop()) {
			 * ((JFrame) this.getTopLevelAncestor()).setAlwaysOnTop(false);
			 * menu1Item[1].setText("Vordergrund"); } else { ((JFrame)
			 * this.getTopLevelAncestor()).setAlwaysOnTop(true);
			 * menu1Item[1].setText("Hintergrund"); }
			 */
		}

		if (e.getActionCommand().equals("Löschen")) {
			StatusBar.clear();
		}
	}
}
