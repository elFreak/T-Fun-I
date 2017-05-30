package model.fMinSearch;

public class Message {
	public String message;
	public boolean isError = false;
	
	public Message(String message, boolean isError) {
		this.message = message;
		this.isError = isError;
	}
}
