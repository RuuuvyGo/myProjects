package socialProExceptions;

import java.io.Serializable;

public class TagException extends Exception implements Serializable{

	private static final long serialVersionUID = -8989893022731882517L;
	private String message;
	
	public TagException() { }
	
	public TagException(String message) {
		this.message = message;
	}
	
	public String getMessage() { return message; }
	public void setMessage(String message) { this. message = message; }
	public String toString() { return message ; }
}
