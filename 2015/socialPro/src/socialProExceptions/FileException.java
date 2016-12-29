package socialProExceptions;

import java.io.Serializable;

public class FileException extends Exception implements Serializable{

	private static final long serialVersionUID = 7618517964699957561L;
	private String message;
	
	public FileException() { }
	
	public FileException(String message) {
		this.message = message;
	}
	
	public String getMessage() { return message; }
	public void setMessage(String message) { this. message = message; }
	public String toString() { return message ; }
}
