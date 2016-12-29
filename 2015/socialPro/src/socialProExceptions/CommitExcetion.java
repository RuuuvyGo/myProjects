package socialProExceptions;

import java.io.Serializable;

public class CommitExcetion extends Exception implements Serializable{


	private static final long serialVersionUID = 8350078714136537484L;
	private String message;
	
	public CommitExcetion() { }
	
	public CommitExcetion(String message) {
		this.message = message;
	}
	
	public String getMessage() { return message; }
	public void setMessage(String message) { this. message = message; }
	public String toString() { return message ; }
}
