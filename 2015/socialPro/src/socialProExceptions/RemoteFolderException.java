package socialProExceptions;

import java.io.Serializable;

public class RemoteFolderException extends Exception implements Serializable{

	private String message;
	
	public RemoteFolderException() { }
	
	public RemoteFolderException(String message) {
		this.message = message;
	}
	
	public String getMessage() { return message; }
	public void setMessage(String message) { this. message = message; }
	public String toString() { return message ; }
}
