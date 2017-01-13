package socialProExceptions;

import java.io.Serializable;

public class FolderException extends Exception implements Serializable{


	private String message;
	
	public FolderException() { }
	
	public FolderException(String message) {
		this.message = message;
	}
	
	public String getMessage() { return message; }
	public void setMessage(String message) { this. message = message; }
	public String toString() { return message ; }
}
