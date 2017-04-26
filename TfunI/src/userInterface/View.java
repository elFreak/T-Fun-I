package userInterface;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class View extends JPanel {
	private static final long serialVersionUID = 1L;

	// --------------------------------------------------------------------
	// General:
	private JFrame frame;
	private Controller controller;

	// --------------------------------------------------------------------
	// Main-Components:
	MenuBar menuBar = new MenuBar(this.controller, this.frame);
	StatusBar statusBar = new StatusBar();
	InputPanel inputPanel = new InputPanel();

	public View(Controller controller, JFrame frame) {
		super(new GridBagLayout());
		this.controller = controller;
		this.frame = frame;

		// Add MenuBar:
		add(menuBar, new GridBagConstraints(0, 0, 2, 1, 1.0, 0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		// Add StatusBar:
		add(statusBar, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		// Add InputPanel:
		add(inputPanel, new GridBagConstraints(0, 1, 1, 2, 0.0, 1.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0), 0, 0));
		// Add OutputWindow:
		add(new JPanel(), new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0));

	}

}
