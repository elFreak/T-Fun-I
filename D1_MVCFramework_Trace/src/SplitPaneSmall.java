import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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

import org.omg.CosNaming.IstringHelper;

public class SplitPaneSmall extends JSplitPane implements MouseListener{
	
	JPanel setTop = new JPanel(new GridBagLayout());
	JPanel setBottom = new JPanel(new GridBagLayout());
	
	private Controller controller;	
	private Model model = new Model();
	
	private JLabel UP = new JLabel("Oben");
	private JLabel Down = new JLabel("Unten");
	
	public SplitPaneSmall(Controller controller) {
		
		addMouseListener(this);
		
		setLayout(new GridBagLayout());
		setOrientation(JSplitPane.VERTICAL_SPLIT);
		
		this.controller = controller;

		setOneTouchExpandable(false); 	//false = keine pfeile mehr
		setResizeWeight(0.5);
		setDividerSize(7);
		setContinuousLayout(true);		//false = Schwarzer "Pseudo-Verschiebebalken"
		
//		jpUp.add(setTop, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST,
//				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
//		jpDown.add(setBottom, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST,
//				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
				
		setTop.setMinimumSize(new Dimension(100,100));
		setBottom.setMinimumSize(new Dimension(100,100));
		
		setTopComponent(setTop);
		setBottomComponent(setBottom);
		setDividerLocation(200);

		
	}
	

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		//StatusBar.showStatus("X: "+e.getX()+" Y: "+e.getY());
		if(e.getButton() == MouseEvent.BUTTON3){
			StatusBar.showStatus("RightMouseButton = Pressed");
			MenuSmall ms1 = new MenuSmall(controller, null);
			ms1.show(e.getComponent(), e.getX(), e.getY());
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}
}