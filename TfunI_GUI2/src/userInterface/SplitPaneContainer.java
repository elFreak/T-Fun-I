package userInterface;

import java.awt.Component;

import javax.swing.JSplitPane;

/**
 * Ermöglicht die einfache Benutzung von {@link JSplitPane}.
 * 
 * @author Team 1
 *
 */
public class SplitPaneContainer extends JSplitPane {
	private static final long serialVersionUID = 1L;

	/**
	 * Erzeugt ein {@link JSplitPane} anhand der übergebenden Argumente.
	 * 
	 * @param orientation
	 * @param componentNorthWest
	 * @param componentSouthEast
	 * @param weightNorthWest
	 * @param weightSouthEast
	 */
	public SplitPaneContainer(int orientation, Component componentNorthWest, Component componentSouthEast,
			double weightNorthWest, double weightSouthEast) {
		setOrientation(orientation);
		setLeftComponent(componentNorthWest);
		setRightComponent(componentSouthEast);
		setResizeWeight((weightNorthWest + (1 - weightSouthEast)) / 2);
		this.setContinuousLayout(true);
	}

}
