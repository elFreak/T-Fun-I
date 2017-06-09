package userInterface.IOCard;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import projectT_Fun_I.GlobalSettings;
import userInterface.StatusBar;

/**
 * Ein {@link JFrame} auf welchem die gewünschte Formel der Übertragungsfunktion dargestellt wird.
 * 
 * @author Team 1
 *
 */
public class FormelFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	public FormelFrame(int order) {
		setLayout(new GridLayout(1, 1, 20, 10));
		setBackground(GlobalSettings.colorBackgroundWhite);
		setVisible(true);
		setAlwaysOnTop(true);
		try {
			ImageIcon imageIcon = new ImageIcon(getClass().getClassLoader().getResource("bilder/Formel"+order+".png"));
			Image image = imageIcon.getImage(); // transform it 
			Image newimg = image.getScaledInstance(GlobalSettings.fontText.getSize()*40, GlobalSettings.fontText.getSize()*5,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
			imageIcon = new ImageIcon(newimg);  // transform it back
			JLabel picLabel = new JLabel(imageIcon);
			add(picLabel);
			setSize(getPreferredSize());
			setResizable(true);
			validate();
		} catch (Exception e) {
			StatusBar.showStatus("Formelbild konnte nicht geöffnet werden.", StatusBar.FEHLER);
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
		
		ImageIcon imageIcon = new ImageIcon(getClass().getClassLoader().getResource("bilder/logo.png"));
		setIconImage(imageIcon.getImage());
		setTitle("TfunI - Übertragungsfunktion");
		

	}
}
