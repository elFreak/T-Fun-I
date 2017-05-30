package model.fMinSearch;

/**
 * Dient dem weiterleiten von Informationen aus einem Thread zum Benutzer.
 */
public interface SwingWorkerClient {
	public void swingAction(Message message);
}
