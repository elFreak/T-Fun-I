package userInterface;

import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class MenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;

	// --------------------------------------------------------------------
	// General:
	JFrame frame;
	Controller controller;

	// --------------------------------------------------------------------
	// Menu Datei:
	JMenu menuDatei;

	// --------------------------------------------------------------------
	// Menu Window:
	JMenu menuWindow;

	// --------------------------------------------------------------------
	// Menu Info:
	JMenu menuInfo;

	public MenuBar(Controller controller, JFrame frame) {
		setLayout(new FlowLayout(0, 15, 0));
		this.frame = frame;
		this.controller = controller;
		menuDatei = new JMenu("Datei");
		menuDatei.addSeparator();
		menuWindow = new JMenu("Window");
		menuWindow.addSeparator();
		menuInfo = new JMenu("Info");
		menuInfo.addSeparator();

		add(menuDatei);
		add(menuWindow);
		add(menuInfo);

	}
}
