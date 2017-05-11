package userInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;



import projectT_Fun_I.GlobalSettings;
import projectT_Fun_I.Utility;

public class StatusBar extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	public static final int INFO = 1;
	public static final int FEHLER = 2;
	private static javax.swing.text.Style styleInfo;
	private static javax.swing.text.Style styleFehler;

	private static JTextPane textPane;
	private static DefaultStyledDocument document = new DefaultStyledDocument();

	// MouseMenu:
	private static JPopupMenu popupMenue = new JPopupMenu();

	public StatusBar() {
		setBorder(MyBorderFactory.createMyBorder("  Info  "));
		this.setLayout(new BorderLayout());
		textPane = new JTextPane(document);
		JScrollPane scrollPane = new JScrollPane(textPane);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPane, BorderLayout.CENTER);
		textPane.setEditable(false);

		// Initzialise Styles:
		StyleContext contextInfo = new StyleContext();
		styleInfo = contextInfo.addStyle("info", null);
		StyleConstants.setForeground(styleInfo, GlobalSettings.colorTextInfo);
		StyleConstants.setFontFamily(styleInfo, GlobalSettings.fontText.getFamily());
		StyleConstants.setFontSize(styleInfo, GlobalSettings.fontText.getSize());
		
		StyleContext contextFehler = new StyleContext();
		styleFehler = contextFehler.addStyle("info", null);
		StyleConstants.setForeground(styleFehler, GlobalSettings.colorTextFehler);
		StyleConstants.setFontFamily(styleFehler, GlobalSettings.fontText.getFamily());
		StyleConstants.setFontSize(styleFehler, GlobalSettings.fontText.getSize());

		// MouseMenu:
		JMenuItem menuItem = new JMenuItem("Clear Status");
		menuItem.setActionCommand("clear");
		menuItem.addActionListener(this);
		popupMenue.add(menuItem);
		textPane.addMouseListener(new PopupListener());
		menuItem.setForeground(GlobalSettings.colorText);
		menuItem.setOpaque(true);
		menuItem.setBackground(GlobalSettings.colorBackgroundGrey);
		menuItem.setFont(GlobalSettings.fontText);

		Utility.setAllBackgrounds(this, GlobalSettings.colorBackgroundBlueBright);
		Utility.setAllBackgrounds(textPane, GlobalSettings.colorBackground);

	}

	public static void showStatus(String text, int type) {
		if (type == INFO) {
			try {
				document.insertString(0, " " + text + "\n", styleInfo);
			} catch (Exception e) {
			}
		}
		if (type == FEHLER) {
			try {
				document.insertString(0, "Fehler:  " + text + "\n", styleFehler);
			} catch (Exception e) {
			}
		}
		textPane.setCaretPosition(0);
	}

	public void clear() {
		textPane.setText("");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("clear")) {
			textPane.setText("");
		}
	}

	class PopupListener extends MouseAdapter {

		public void mousePressed(MouseEvent e) {
			maybeShowPopup(e);
		}

		public void mouseReleased(MouseEvent e) {
			maybeShowPopup(e);
		}

		private void maybeShowPopup(MouseEvent e) {
			if (e.isPopupTrigger()) {
				popupMenue.show(e.getComponent(), e.getX(), e.getY());
			}
		}
	}
}
