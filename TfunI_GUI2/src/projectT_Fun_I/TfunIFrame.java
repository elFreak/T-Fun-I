package projectT_Fun_I;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;

import model.Model;
import userInterface.Controller;
import userInterface.View;

public class TfunIFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private enum Mode {
		FIXED, PACKED, FIXEDRESIZABLE, PACKEDRESIZABLE
	};

	private Mode mode = Mode.FIXEDRESIZABLE;
	private int width = 1200, height = 800;
 
	private static enum LAF {
		METAL, OCEAN, SYSTEM, WINDOWS_TFUNI
	}

	private static LAF laf = LAF.WINDOWS_TFUNI;

	private Model model;
	private View view;
	private Controller controller;

	/**
	 * init
	 */
	public void init() {
		pack();

		GlobalSettings.init();

		model = new Model();
		controller = new Controller(model);
		view = new View(controller, this);
		model.addObserver(view);
		controller.setView(view);

		this.add(view);

		// Center the window
		switch (mode) {
		case FIXED:
			setMinimumSize(getPreferredSize());
			setSize(width, height);
			setResizable(false);
			validate();
			break;
		case FIXEDRESIZABLE:
			setMinimumSize(getPreferredSize());
			setSize(width, height);
			setResizable(true);
			validate();
			break;
		case PACKED:
			setMinimumSize(getPreferredSize());
			setResizable(false);
			break;
		case PACKEDRESIZABLE:
			setMinimumSize(getPreferredSize());
			setResizable(true);
			break;
		}

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = getSize();
		if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
		}
		setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

	}

	/**
	 * Main
	 * @param args
	 */
	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					switch (laf) {
					case METAL:
						UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
						break;
					case OCEAN:
						UIManager.setLookAndFeel("javax.swing.plaf.metal.OceanLookAndFeel");
						MetalLookAndFeel.setCurrentTheme(new OceanTheme());
						break;
					case SYSTEM:
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
						break;
					case WINDOWS_TFUNI:
						UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

						// Customise LAF for TappedPane:
						UIManager.put("TabbedPane.tabRunOverlay", 0);

						break;
					}
				} catch (Exception exception) {
					exception.printStackTrace();
				}
				TfunIFrame frame = new TfunIFrame();
				if (laf == LAF.METAL || laf == LAF.OCEAN) {
					frame.setUndecorated(true);
					frame.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
				}

				ImageIcon imageIcon = new ImageIcon(getClass().getClassLoader().getResource("bilder/logo2.png"));
				frame.setIconImage(imageIcon.getImage());
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setTitle("TfunI - Transfer Function Identifier");
				frame.init();
				frame.setVisible(true);
			}
		});
	}
}
