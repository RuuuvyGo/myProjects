package socialProExceptions;

import java.io.Serializable;

public class MemberException extends Exception implements Serializable{

	private static final long serialVersionUID = 8933750699069069011L;
	private String message;
	
	public MemberException() { }
	
	public MemberException(String message) {
		this.message = message;
	}
	
	public String getMessage() { return message; }
	public void setMessage(String message) { this. message = message; }
	public String toString() { return message ; }
}
