package socialProExceptions;

import java.io.Serializable;

public class DAOException extends Exception implements Serializable{


	private static final long serialVersionUID = -2992346495798694698L;
	private String message;
	
	public DAOException() { }
	
	public DAOException(String message) {
		this.message = message;
	}
	
	public String getMessage() { return message; }
	public void setMessage(String message) { this. message = message; }
	public String toString() { return message ; }
}
