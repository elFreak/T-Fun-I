public class MulticoreTest implements Runnable {

	public void run() {
		while (true)
			;
	}

	public static void main(String[] args) {
		new Thread(new MulticoreTest()).start();
		new Thread(new MulticoreTest()).start();
		new Thread(new MulticoreTest()).start();
		new Thread(new MulticoreTest()).start();
		new Thread(new MulticoreTest()).start();
		new Thread(new MulticoreTest()).start();
		new Thread(new MulticoreTest()).start();
		new Thread(new MulticoreTest()).start();
	}
}
