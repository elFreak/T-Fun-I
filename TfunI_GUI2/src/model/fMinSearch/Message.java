package model.fMinSearch;

/**
 * Klasse Message:
 * Dient zur Übermittlung von Nachrichten an die StatusBar.
 * 
 * @author Team 1
 *
 */
public class Message {
	public String message;
	public boolean isError = false;
	
	public Message(String message, boolean isError) {
		this.message = message;
		this.isError = isError;
	}
}
