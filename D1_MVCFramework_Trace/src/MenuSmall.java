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
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;

public class MenuSmall extends JPopupMenu implements Observer, ActionListener {

	JMenu menu1, menu2, menu3;
	JMenuItem[] menuI = new JMenuItem[6];
	String[] menuItxt = { "Plot hinzufügen", "Fenster hinzufügen", "Feld 2", "Feld3", "Feld4", "Löschen" };

	JFrame frame;
	Controller controller;

	public MenuSmall(Controller controller, JFrame frame) {
		this.frame = frame;
		this.controller = controller;
		menu1 = new JMenu("Graph hinzufügen");

		// --------------------------------------------------------//
		// MenuSmall
		
		JMenuItem[] sub1 = new JMenuItem[16];
		String[] sub1txt = { "Graph 1", "Graph 2", "Graph 3", "Graph 4"};

		for (int k = 0; k < 4; k++) {
			sub1[k] = new JMenuItem(sub1txt[k]);
			if (k < 4) {
				menu1.add(sub1[k]);
/*			} else if (k < 8) {
				menu2.add(sub1[k]);
			} else if (k < 12) {
				menu3.add(sub1[k]);
*/			}
			sub1[k].addActionListener(this);
			sub1[k].setActionCommand(sub1txt[k] + (k/4));
		}
		
		add(menu1);
//		add(menu2);
//		add(menu3);
		for(int i=0; i<6; i++){
			menuI[i] = new JMenuItem(menuItxt[i]); 
			menuI[i].setActionCommand(menuItxt[i]);
			add(menuI[i]);
		}		
		


	}
	/*-----------------Actionevents--------------------------*/

	public void update(Observable o, Object obj) {
	}

	public void actionPerformed(ActionEvent e) {
		
		StatusBar.showStatus(e.getActionCommand());


	}
}
