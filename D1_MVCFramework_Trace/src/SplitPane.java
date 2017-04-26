import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;

public class SplitPane extends JSplitPane{

	private Controller controller;
//	private SplitPaneSmall jpLeft = new SplitPaneSmall(controller);
//	private SplitPaneSmall jpRight = new SplitPaneSmall(controller);
	
	private JPanel jpLeft = new JPanel();
	private JPanel jpRight = new JPanel();
	
	
//	private Model model = new Model();
				
	public SplitPane(Controller controller) {
		
		setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		
		setBorder(MyBorderFactory.createMyBorder(" Plots "));
		this.controller = controller;

		setOneTouchExpandable(false); 	//false = keine pfeile mehr
		setResizeWeight(0.5);
		setDividerSize(7);
		setContinuousLayout(true);		//false = Schwarzer "Pseudo-Verschiebebalken"
		
		
		// Setting left and right component.
		setLeftComponent(jpLeft);
		setRightComponent(jpRight);
		setDividerLocation(225);		
	}
}
