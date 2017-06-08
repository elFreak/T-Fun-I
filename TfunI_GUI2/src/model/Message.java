package model;

/**
 * Definiert eine Datenstrucktur, welche vom Interface {@link SwingWorkerClient}
 * genuzt wird um Daten zu transportieren.
 * 
 * @author Team 1
 *
 */
public class Message {
	public String message;
	public boolean isError = false;

	/**
	 * Baut das Objekt anhand der übergebenen Argumenten.
	 * 
	 * @param message
	 * @param isError
	 */
	public Message(String message, boolean isError) {
		this.message = message;
		this.isError = isError;
	}
}
