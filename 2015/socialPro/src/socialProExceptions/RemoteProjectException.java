package socialProExceptions;

import java.io.Serializable;

public class RemoteProjectException extends Exception implements Serializable{

	private String message;
	
	public RemoteProjectException() { }
	
	public RemoteProjectException(String message) {
		this.message = message;
	}
	
	public String getMessage() { return message; }
	public void setMessage(String message) { this. message = message; }
	public String toString() { return message ; }
}
