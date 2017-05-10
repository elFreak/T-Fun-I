package pro2e.teamX;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;

import pro2e.teamX.model.Model;
import pro2e.teamX.userinterface.Controller;
import pro2e.teamX.userinterface.MenuBar;
import pro2e.teamX.userinterface.StatusBar;
import pro2e.teamX.userinterface.ToolBar;
import pro2e.teamX.userinterface.TopView;

public class MVCFramework extends JFrame {

	private enum Mode {
		FIXED, PACKED, FIXEDRESIZABLE, PACKEDRESIZABLE
	};

	private Mode mode = Mode.PACKED;
	private int width = 1200, height = 800;
	private Model model = new Model();
	private Controller controller = new Controller(model, this);
	private TopView view = new TopView(controller);
	private MenuBar menuBar = new MenuBar(controller, this);
	private ToolBar toolBar = new ToolBar(controller);
	private StatusBar statusBar = new StatusBar();

	private static enum LAF {
		METAL, OCEAN, SYSTEM, NIMROD, NAPKIN
	}

	private static LAF laf = LAF.SYSTEM;

	public void init() {
		model.addObserver(view);
		model.addObserver(toolBar);
		model.addObserver(menuBar);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(toolBar, BorderLayout.PAGE_START);
		getContentPane().add(view, BorderLayout.CENTER);
		getContentPane().add(statusBar, BorderLayout.SOUTH);
		setJMenuBar(menuBar);

		DPIFixV3.scaleSwingFonts();
		SwingUtilities.updateComponentTreeUI(this);
		pack();

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
						case NIMROD:
							UIManager.setLookAndFeel(new MyNimRODLookAndFeel("DarkGray.theme"));
							break;
						case NAPKIN:
							UIManager.setLookAndFeel(new net.sourceforge.napkinlaf.NapkinLookAndFeel());
							break;
					}
				} catch (Exception exception) {
					exception.printStackTrace();
				}
				MVCFramework frame = new MVCFramework();
				if (laf != LAF.SYSTEM) {
					frame.setUndecorated(true);
					frame.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
				}
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setTitle("MVCFramework");
				frame.init();
				frame.setVisible(true);
			}
		});
	}
}
