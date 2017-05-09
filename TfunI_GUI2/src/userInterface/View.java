package userInterface;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import com.sun.management.jmx.Trace;
import com.sun.org.apache.xpath.internal.operations.Mod;

import javafx.beans.Observable;
import model.MeasurementData;
import model.Model;
import projectTfunI.GlobalSettings;
import projectTfunI.Utility;

public class View extends JPanel implements Observer{
	private static final long serialVersionUID = 1L;

	// --------------------------------------------------------------------
	// General:
	private JFrame frame;
	private Controller controller;

	// --------------------------------------------------------------------
	// Main-Components:
	public MenuBar menuBar;
	public StatusBar statusBar;
	public InputPanel inputPanel;
	public OutputPanel outputPanel;
	public ProgramFlow programFlow;

	// --------------------------------------------------------------------
	// Initialize:
	public View(Controller controller, JFrame frame) {
		super(new GridBagLayout());
		this.controller = controller;
		this.frame = frame;
		menuBar = new MenuBar(this.controller, this.frame);
		statusBar = new StatusBar();
		inputPanel = new InputPanel(this.controller);
		outputPanel = new OutputPanel(this.controller);
		programFlow = new ProgramFlow(this.controller);

		// Add MenuBar and ProgramFlow:
		add(menuBar, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

		JPanel panelFlow = new JPanel(new GridLayout(1, 1));
		panelFlow.setBackground(GlobalSettings.colorBackgroundBlueBright);
		// panelFlow.setBorder(MyBorderFactory.createMyBorder(" Programm Flow
		// "));
		panelFlow.add(programFlow);
		add(panelFlow, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		// Add the rest of the GUI (SplitPane-Area):
		add(new SplitPaneContainer(JSplitPane.HORIZONTAL_SPLIT, inputPanel,
				new SplitPaneContainer(JSplitPane.VERTICAL_SPLIT, outputPanel, statusBar, 1.0,
						0.0),
				0.0, 1.0),
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
		
		// Traces Aktualisieren
		JavaPlot.Trace traceStep = new JavaPlot.Trace();
		traceStep.data = ((Model)obs).measurementData.getstep();
		
		JavaPlot.Trace traceRaw = new JavaPlot.Trace();
		traceRaw.data = ((Model)obs).measurementData.getRawData();
		
		//Einlesen aktualisieren:
		

		revalidate();
		repaint();
		
//		Bearbeiten aktualisieren
		
		outputPanel.plotBearbeiten.addTrace(traceStep);
		outputPanel.plotBearbeiten.setRangeIdeal();
		
	}

}
