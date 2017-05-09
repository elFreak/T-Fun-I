package pro2e.teamX.userinterface;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JToolBar;

public class ToolBar extends JToolBar implements Observer, ActionListener {

	private JButton btOk = new JButton("", Utility.loadResourceIcon("axialis-blue/16x16/Save.png"));
	private JButton btCancel = new JButton(null, Utility.loadResourceIcon("axialis-blue/16x16/Printer.png"));
	private Controller controller;

	public ToolBar(Controller controller) {
		setBorder(MyBorderFactory.createMyBorder(" ToolBar "));
		this.controller = controller;
		setLayout(new FlowLayout(FlowLayout.LEFT));
		add(btOk);
		btOk.addActionListener(this);
		add(btCancel);
		this.setFloatable(true);
	}

	public void update(Observable obs, Object obj) {}

	public void actionPerformed(ActionEvent e) {}
}
