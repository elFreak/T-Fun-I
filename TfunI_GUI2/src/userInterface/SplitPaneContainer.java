package userInterface;

import java.awt.Component;

import javax.swing.JSplitPane;

public class SplitPaneContainer extends JSplitPane {
	private static final long serialVersionUID = 1L;

	public SplitPaneContainer(int orientation, Component componentNorthWest, Component componentSouthEast,double weightNorthWest, double weightSouthEast) {
		setOrientation(orientation);
		setLeftComponent(componentNorthWest);
		setRightComponent(componentSouthEast);
		setResizeWeight((weightNorthWest+(1-weightSouthEast))/2);
		this.setContinuousLayout(true);
	}

}
