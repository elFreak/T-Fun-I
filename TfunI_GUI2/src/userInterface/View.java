package userInterface;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import JavaPlot.Plot;
import projectTfunI.GlobalSettings;
import projectTfunI.Utility;

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
	OutputPanel outputPanel = new OutputPanel();

	public View(Controller controller, JFrame frame) {
		super(new GridBagLayout());
		this.controller = controller;
		this.frame = frame;

		// Add MenuBar:
		add(menuBar, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		// Add the rest of the GUI (SplitPane-Area):
		add(new SplitPaneContainer(JSplitPane.HORIZONTAL_SPLIT, inputPanel,
				new SplitPaneContainer(JSplitPane.VERTICAL_SPLIT, outputPanel, statusBar, 1.0, 0.0), 0.0, 1.0),
				new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.FIRST_LINE_START,
						GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		// Set Fonts and Colors:
		Utility.setAllFonts(this, GlobalSettings.fontText);
		Utility.setAllFonts(statusBar, GlobalSettings.fontTextSmall);

		Utility.setAllForegrounds(this, GlobalSettings.colorText);
		Utility.setAllForegrounds(statusBar, GlobalSettings.colorTextGrey);

		Utility.setAllBackgrounds(this, GlobalSettings.colorBackground);
		statusBar.setBackground(GlobalSettings.colorBackgroundBlueBright);
		inputPanel.setBackground(GlobalSettings.colorBackgroundBlueBright);
		outputPanel.setBackground(GlobalSettings.colorBackgroundBlueBright);

	}

}
