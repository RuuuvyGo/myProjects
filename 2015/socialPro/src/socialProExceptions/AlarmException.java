package socialProExceptions;

import java.io.Serializable;

public class AlarmException extends Exception implements Serializable{

	private static final long serialVersionUID = 2111377406255740487L;
	private String message;
	
	public AlarmException() { }
	
	public AlarmException(String message) {
		this.message = message;
	}
	
	public String getMessage() { return message; }
	public void setMessage(String message) { this. message = message; }
	public String toString() { return message ; }

}
