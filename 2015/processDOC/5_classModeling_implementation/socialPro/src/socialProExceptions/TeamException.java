package socialProExceptions;

import java.io.Serializable;

public class TeamException extends Exception implements Serializable{

	private static final long serialVersionUID = -4501993359846341271L;
	private String message;
	
	public TeamException() { }
	
	public TeamException(String message) {
		this.message = message;
	}
	
	public String getMessage() { return message; }
	public void setMessage(String message) { this. message = message; }
	public String toString() { return message ; }
}
