package userInterface;

import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import projectTfunI.GlobalSettings;

public class OutputPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private Component[] components = new Component[4];
	private boolean[] componentsValid = new boolean[4];
	private int componentsnumber = 0;

	public OutputPanel() {
		setOpaque(true);
		setBorder(MyBorderFactory.createMyBorder("  Ausgabe  "));
		
		setLayout(new GridLayout(1, 1));
		// Initialize:
		for (int i = 0; i < componentsValid.length; i++) {
			componentsValid[i] = false;
		}
		reorganisePanel();
	}

	public int addComponent(Component component) {
		for (int i = 0; i < componentsValid.length; i++) {
			if (componentsValid[i] == false) {
				this.components[i] = component;
				componentsValid[i] = true;
				componentsnumber++;
				reorganisePanel();
				return i;
			}
		}
		return -1;
	}

	public void clearComponent(int index) {
		componentsValid[index] = false;
		componentsnumber--;
		reorganisePanel();
	}

	private void reorganisePanel() {
		removeAll();
		Component[] validComps = new Component[4];
		int j = 0;
		switch (componentsnumber) {
		case 0:
			JPanel jPanel = new JPanel();
			jPanel.setBackground(GlobalSettings.colorBackgroundGrey);
			add(jPanel);
			repaint();
			break;
		case 1:
			for (int i = 0; i < componentsValid.length; i++) {
				if (componentsValid[i] == true) {
					add(components[i]);
				}
			}
			break;
		case 2:
			for (int i = 0; i < componentsValid.length; i++) {
				if (componentsValid[i] == true) {
					validComps[j]=components[i];
					j++;
				}
			}
			add(new SplitPaneContainer(JSplitPane.HORIZONTAL_SPLIT, validComps[0], validComps[1], 1.0, 1.0));
			break;
		case 3:
			for (int i = 0; i < componentsValid.length; i++) {
				if (componentsValid[i] == true) {
					validComps[j]=components[i];
					j++;
				}
			}
			add(new SplitPaneContainer(JSplitPane.HORIZONTAL_SPLIT, new SplitPaneContainer(JSplitPane.VERTICAL_SPLIT, validComps[1], validComps[2], 1.0, 1.0), validComps[0], 1.0, 1.0));
			break;
		case 4:
			for (int i = 0; i < componentsValid.length; i++) {
				if (componentsValid[i] == true) {
					validComps[j]=components[i];
					j++;
				}
			}
			add(new SplitPaneContainer(JSplitPane.HORIZONTAL_SPLIT, new SplitPaneContainer(JSplitPane.VERTICAL_SPLIT, validComps[1], validComps[2], 1.0, 1.0), new SplitPaneContainer(JSplitPane.VERTICAL_SPLIT, validComps[0], validComps[3], 1.0, 1.0), 1.0, 1.0));
			break;

		}
	}
}
