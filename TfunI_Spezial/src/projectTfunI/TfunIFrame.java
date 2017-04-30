package projectTfunI;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;

import userInterface.Controller;
import userInterface.View;

public class TfunIFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private enum Mode {
		FIXED, PACKED, FIXEDRESIZABLE, PACKEDRESIZABLE
	};

	private Mode mode = Mode.PACKEDRESIZABLE;
	private int width = 1200, height = 800;

	private static enum LAF {
		METAL, OCEAN, SYSTEM
	}

	private static LAF laf = LAF.SYSTEM;

	private View view;
	private Controller controller;

	public void init() {
		pack();

		controller = new Controller();
		view = new View(controller, this);
		controller.setView(view);

		this.add(view);

		// Fonts:
		Font systemFont = UIManager.getDefaults().getFont("TextPane.font");
		Font frameFont = systemFont;
		Font applicationFont = new Font(systemFont.getName(), Font.PLAIN, (int)(systemFont.getSize()*1.2));
		setAllFonts(this, frameFont);
		setAllFonts(view, applicationFont);
		
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

	public void setAllFonts(Component[] comps, Font font) {
		for (int i = 0; i < comps.length; i++) {
			comps[i].setFont(font);
			if (comps[i] instanceof Container) {
				setAllFonts(((Container) comps[i]).getComponents(), font);
			}
		}
	}

	public void setAllFonts(Component comp, Font font) {
		comp.setFont(font);
		if (comp instanceof Container) {
			setAllFonts(((Container) comp).getComponents(), font);
		}

	}

	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				try {
					switch (laf) {
					case METAL:
						UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
						break;
					case OCEAN:
						UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
						MetalLookAndFeel.setCurrentTheme(new OceanTheme());
						break;
					case SYSTEM:
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
						break;
					}
				} catch (Exception exception) {
					exception.printStackTrace();
				}
				TfunIFrame frame = new TfunIFrame();
				if (laf != LAF.SYSTEM) {
					frame.setUndecorated(true);
					frame.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
				}

				ImageIcon imageIcon = new ImageIcon(getClass().getClassLoader().getResource("bilder/logo2.png"));
				frame.setIconImage(imageIcon.getImage());
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setTitle("Transfer Function Identifier");
				frame.init();
				frame.setVisible(true);
			}
		});
	}
}
