package pro2e.teamX.userinterface;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class MenuBar extends JMenuBar implements Observer, ActionListener {
	JMenu menu, submenu;
	JMenuItem menuItemOnTop, submenuItem;
	JFrame frame;
	Controller controller;

	public MenuBar(Controller controller, JFrame frame) {
		this.frame = frame;
		this.controller = controller;
		menu = new JMenu("Datei");
		menu.setMnemonic(KeyEvent.VK_D);

		menu.addSeparator();
		submenu = new JMenu("A submenu");
		submenu.setMnemonic(KeyEvent.VK_S);
		submenuItem = new JMenuItem("SubmenuItem");
		submenu.add(submenuItem);
		menu.add(submenu);

		menuItemOnTop = new JMenuItem("Allwas on Top", KeyEvent.VK_T);
		menuItemOnTop.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.ALT_MASK));
		menuItemOnTop.setActionCommand("OnTop");
		menuItemOnTop.addActionListener(this);
		menu.add(menuItemOnTop);

		JMenuItem menuItemResizable = new JMenuItem("Resizable", KeyEvent.VK_R);
		menuItemResizable.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.ALT_MASK));
		menuItemResizable.setActionCommand("Resizable");
		menuItemResizable.addActionListener(this);
		menu.add(menuItemResizable);

		JMenuItem menuItemNotResizable = new JMenuItem("Not Resizable", KeyEvent.VK_N);
		menuItemNotResizable.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.ALT_MASK));
		menuItemNotResizable.setActionCommand("NotResizable");
		menuItemNotResizable.addActionListener(this);
		menu.add(menuItemNotResizable);
		add(menu);
	}

	public void update(Observable o, Object obj) {}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Resizable")) {
			frame.setResizable(true);
			Dimension dim = frame.getSize();
			dim.width -= 100;
			frame.setSize(dim);
		}
		if (e.getActionCommand().equals("NotResizable")) {
			frame.setResizable(false);
			Dimension dim = frame.getSize();
			dim.width += 100;
			frame.setSize(dim);
		}
		if (e.getActionCommand().equals("OnTop")) {
			StatusBar.showStatus(this, e, e.getActionCommand());
			if (((JFrame) this.getTopLevelAncestor()).isAlwaysOnTop()) {
				((JFrame) this.getTopLevelAncestor()).setAlwaysOnTop(false);
				menuItemOnTop.setText("Allwas on Top");
			} else {
				((JFrame) this.getTopLevelAncestor()).setAlwaysOnTop(true);
				menuItemOnTop.setText("Not allwas on Top");
			}
		}
	}
}
