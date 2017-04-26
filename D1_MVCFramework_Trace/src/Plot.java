import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Plot extends JPanel implements Observer, ActionListener {
	
	Controller controller;
	JPanel plotfunktion = new JPanel(new GridBagLayout());
	JLabel funktion = new JLabel(" Funktion ");
	
	public Plot(Controller controller) {

		setLayout(new GridBagLayout());
		
		this.controller = controller;
		
		plotfunktion.setBackground(Color.WHITE);

		plotfunktion.add(new JLabel("Graph"), new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0,
				GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		//plotfunktion.add(new Graph(controller), new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0,
		//		GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		add(plotfunktion, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
				new Insets(10, 10, 0, 0), 0, 0));
		
		add(funktion, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.BOTH, new Insets(10, 0, 0, 0), 0, 0));
		
		add(new JLabel(), new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 10, 0));
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub

	}
}
