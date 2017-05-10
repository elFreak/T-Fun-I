package userInterface;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;

import JavaPlot.Plot;
import projectTfunI.GlobalSettings;
import projectTfunI.Utility;

public class OutputCardEinlesen extends WindowContainer {
	
	private WindowContainer windowContainerEinlesen = new WindowContainer();
	private Plot plotEinlesen = new Plot();
	
	public OutputCardEinlesen() {
		
		windowContainerEinlesen.addComponent(plotEinlesen);
		
		
		
//		private void cardEinlesenInit() {
//			cardEinlesen.addComponent(plotEinlesen);
//
//			this.plotEinlesen.addTrace(traceStep);
//			traceStep.dataValid = false;
//
//			this.plotEinlesen.addTrace(traceRaw);
//			traceRaw.dataValid = false;
//		}
		
		setBorder(MyBorderFactory.createMyBorder("  Ausgabe  "));
		Utility.setAllBackgrounds(this, GlobalSettings.colorBackground);
		setOpaque(true);
		setBackground(GlobalSettings.colorBackgroundBlueBright);
		
		
		
		
		
		// Cardpanel konfigurieren
		this.setLayout(new GridBagLayout());
		this.setBackground(GlobalSettings.colorBackground);
		this.setBorder(MyBorderFactory.createMyBorder("Signal bearbeiten"));
		
		

		this.add(panelFiltern, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.NORTH,
				GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		this.add(panelRahmen, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		this.add(new JPanel(), new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
}
