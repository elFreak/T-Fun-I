package userInterface;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import projectTfunI.GlobalSettings;
import projectTfunI.Utility;

public class StatusBar extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	private static JTextArea textArea = new JTextArea(5, 10);

	// MouseMenu:
	private static JPopupMenu popupMenue = new JPopupMenu();

	public StatusBar() {
		setBorder(MyBorderFactory.createMyBorder("  Info  "));
		this.setLayout(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPane, BorderLayout.CENTER);
		textArea.setEditable(false);

		// MouseMenu:
		JMenuItem menuItem = new JMenuItem("Clear Status");
		menuItem.setActionCommand("clear");
		menuItem.addActionListener(this);
		popupMenue.add(menuItem);
		textArea.addMouseListener(new PopupListener());
		menuItem.setForeground(GlobalSettings.colorText);
		menuItem.setOpaque(true);
		menuItem.setBackground(GlobalSettings.colorBackgroundGrey);
		menuItem.setFont(GlobalSettings.fontText);
		
		Utility.setAllBackgrounds(this, GlobalSettings.colorBackgroundBlueBright);
		Utility.setAllBackgrounds(textArea, GlobalSettings.colorBackground);
		
		
	}

	public void showStatus(String text) {
		textArea.setText(textArea.getText() + "  " + text + "\n");
	}

	public void clear() {
		textArea.setText("");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("clear")) {
			textArea.setText("");
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
