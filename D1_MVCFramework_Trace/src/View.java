import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;

public class View extends JPanel implements Observer, ActionListener{
	Trace trace = new Trace(this);
	private static final long serialVersionUID = 1L;
	public TextField tf1 = new TextField(40), tf2 = new TextField(40);
	private Controller controller;
	
	public TextField label = new TextField();

	public View(Controller controller) {
		
		trace.constructorCall();

		this.controller = controller;
		setLayout(new BorderLayout());

		// ---------------------------------------------------------//
		// Vorlagen zum Kopieren
		/*
		 * bcp0.add(tabs,new
		 * GridBagConstraints(0,0,1,1,0.0,1.0,GridBagConstraints.WEST,
		 * GridBagConstraints.BOTH, new Insets(0,0,0,0), 0,0)); Button[] bt1 =
		 * new Button[5];
		 * 
		 */
		
		Speicher speicher = new Speicher();

		// ---------------------------------------------------------//
		// Hauptaufteilung
		// MenuBar
		// Center
		// StatusBar

		JPanel pHaupt = new JPanel(new GridBagLayout());
		MenuBar pMenu = new MenuBar(controller, null);
		StatusBar pStatus = new StatusBar();

		add(pMenu, BorderLayout.NORTH);
		add(pHaupt, BorderLayout.CENTER);
		add(pStatus, BorderLayout.SOUTH);

		// ---------------------------------------------------------//
		// Inhalt Center

		Tabs pTabs = new Tabs(controller);
		SplitPane pSplit = new SplitPane(controller);
		
		pHaupt.add(pTabs, new GridBagConstraints(0, 0, 1, 1, 0.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
				new Insets(0, 0, 0, 0), 50, 0));
		pHaupt.add(pSplit, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST,
				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		// ----------------------------------------------------------//
		// Inhalt pSplit

		SplitPaneSmall splitLeft = new SplitPaneSmall(controller);
		SplitPaneSmall splitRight = new SplitPaneSmall(controller);

		pSplit.setLeftComponent(splitLeft);
		pSplit.setRightComponent(splitRight);

		// ----------------------------------------------------------//
		// Inhalt splitLeft
		Plot plotLT = new Plot(controller);
		Plot plotLB = new Plot(controller);

		splitLeft.setTop.add(plotLT, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST,
				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		splitLeft.setBottom.add(plotLB, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST,
				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		// ----------------------------------------------------------//
		// Inhalt splitRight
		Plot plotRT = new Plot(controller);
		Plot plotRB = new Plot(controller);

		splitRight.setTop.add(plotRT, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST,
				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		splitRight.setBottom.add(plotRB, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST,
				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		
		plotRB.add(new Graph(controller));

		// btOK.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		trace.eventCall();
		controller.btAction(tf1.getText());
	}

	public void update(Observable obs, Object obj) {
		trace.methodeCall();
		Model model = (Model) obs;
		String textInModel = model.getData();
		tf2.setText(textInModel);
	}

}
