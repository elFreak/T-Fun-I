package model;

/**
 * 
 * Interface um Information durch geschachtelte SwingWorker zu transportieren.
 * 
 * @author Team 1
 *
 */
public interface SwingWorkerClient {
	
	/**
	 * Dient dazu Informationen aus einem SwingWorker zu empfangen.
	 * 
	 * @param message
	 */
	public void swingAction(Message message);
}
