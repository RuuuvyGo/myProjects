package socialProExceptions;

public class LogExcetion extends Exception {


	private String message;
	
	public LogExcetion() { }
	
	public LogExcetion(String message) {
		this.message = message;
	}
	
	public String getMessage() { return message; }
	public void setMessage(String message) { this. message = message; }
	public String toString() { return message ; }
}
