package userInterface;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import model.Model;
import projectT_Fun_I.GlobalSettings;
import projectT_Fun_I.Utility;
import userInterface.IOCard.InputPanel;
import userInterface.IOCard.OutputPanel;

/**
 * Dient als View nach dem MVC-Pattern. Verwalted in erster Line die
 * Hauptkomponenten {@link #statusBar}, {@link #inputPanel},
 * {@link #outputPanel} und {@link View#programFlow}. Ausserdem ist die View ein
 * {@link Observer} vom {@link Model} und hat die Aufgabe, die Informationen vom
 * Model an seine Hauptkomponenten weiterzuleiten.
 * 
 * @author Team 1
 *
 */
public class View extends JPanel implements Observer {
	private static final long serialVersionUID = 1L;

	// --------------------------------------------------------------------
	// General:
	private Controller controller;

	// --------------------------------------------------------------------
	// Main-Components:
	public StatusBar statusBar;
	public InputPanel inputPanel;
	public OutputPanel outputPanel;
	public ProgramFlow programFlow;

	// --------------------------------------------------------------------
	// Initialize:
	/**
	 * Erzeugt das Objekt und seine Hauptkomponenten ({@link #statusBar},
	 * {@link #inputPanel}, {@link #outputPanel} und {@link View#programFlow}).
	 * 
	 * @param controller
	 */
	public View(Controller controller) {
		super(new GridBagLayout());
		this.controller = controller;

		statusBar = new StatusBar();
		inputPanel = new InputPanel(this.controller);
		outputPanel = new OutputPanel(this.controller);
		programFlow = new ProgramFlow(this.controller);

		// Add MenuBar and ProgramFlow:

		JPanel panelFlow = new JPanel(new GridLayout(1, 1));
		panelFlow.setBackground(GlobalSettings.colorBackgroundBlueBright);
		panelFlow.add(programFlow);
		add(panelFlow, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		// Add the rest of the GUI (SplitPane-Area):
		add(new SplitPaneContainer(JSplitPane.HORIZONTAL_SPLIT, inputPanel,
				new SplitPaneContainer(JSplitPane.VERTICAL_SPLIT, outputPanel, statusBar, 1.0, 0.0), 0.0, 1.0),
				new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.FIRST_LINE_START,
						GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		// Set Fonts and Colors:
		Utility.setAllFonts(this, GlobalSettings.fontText);
		Utility.setAllFonts(statusBar, GlobalSettings.fontTextSmall);

		Utility.setAllForegrounds(this, GlobalSettings.colorText);
		Utility.setAllForegrounds(statusBar, GlobalSettings.colorTextGrey);

	}

	@Override
	public void update(java.util.Observable obs, Object obj) {

		inputPanel.update(obs, obj);
		outputPanel.update(obs, obj);

		// Update:
		revalidate();
		repaint();
	}

}
