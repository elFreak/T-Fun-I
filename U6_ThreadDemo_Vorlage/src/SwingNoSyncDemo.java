import javax.swing.*;

public class SwingNoSyncDemo {
	public static void main(String[] args) {
		final DefaultListModel<String> model = new DefaultListModel<String>();
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		JFrame frame = new JFrame();
		frame.add(new JList<String>(model));
		frame.setSize(200, 100);
		frame.setVisible(true);

		new Thread() {
			@Override
			public void run() {
				setPriority(Thread.MIN_PRIORITY);
				while (true) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					model.addElement("Dumm gelaufen");
				}
			}
		}.start();

		new Thread() {
			@Override
			public void run() {
				setPriority(Thread.MIN_PRIORITY);
				while (true) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					model.removeElement("Dumm gelaufen");
				}
			}
		}.start();
	}
}