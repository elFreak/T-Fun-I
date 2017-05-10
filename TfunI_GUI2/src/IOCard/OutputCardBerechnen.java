package IOCard;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;

import JavaPlot.Plot;
import projectT_Fun_I.GlobalSettings;
import userInterface.MyBorderFactory;
import userInterface.OutputPanel;
import userInterface.WindowContainer;

public class OutputCardBerechnen extends WindowContainer {
	private static final long serialVersionUID = 1L;
	
	public Plot plotBerechnen = new Plot();
	public Plot plotNullstellen = new Plot();
	private JPanel panelBerechnen = new JPanel(new GridBagLayout());

	public OutputCardBerechnen(OutputPanel outputPanel) {
		
		addComponent(plotNullstellen);
		addComponent(plotBerechnen);
		addComponent(panelBerechnen);

		// Funktion
		panelBerechnen.setBackground(GlobalSettings.colorBackground);
		panelBerechnen.setBorder(MyBorderFactory.createMyBorder("Funktion"));
		
		panelBerechnen.add(new JLabel("Formel: "), new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		panelBerechnen.add(new JLabel("Qp: "), new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		panelBerechnen.add(new JLabel("Wp: "), new GridBagConstraints(0, 2, 1, 1, 1.0, 0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		panelBerechnen.add(new JLabel("K: "), new GridBagConstraints(0, 3, 1, 1, 1.0, 0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		panelBerechnen.add(new JLabel("K: "), new GridBagConstraints(0, 4, 1, 1, 1.0, 0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
		panelBerechnen.add(new JLabel("K: "), new GridBagConstraints(0, 5, 1, 1, 1.0, 0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(20, 10, 10, 10), 0, 0));
	
		// Plot
		plotBerechnen.addTrace(outputPanel.traceStep);
		plotBerechnen.addTrace(outputPanel.traceRaw);
		outputPanel.traceStep.dataValid = false;
		outputPanel.traceRaw.dataValid = false;
		
		// Nullstellen
		plotBerechnen.addTrace(outputPanel.traceStep);
		plotBerechnen.addTrace(outputPanel.traceRaw);
		outputPanel.traceStep.dataValid = false;
		outputPanel.traceRaw.dataValid = false;
		

	}
}
