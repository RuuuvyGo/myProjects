package socialProExceptions;

public class ProjectException extends Exception {

	private String message;
	
	public ProjectException() { }
	
	public ProjectException(String message) {
		this.message = message;
	}
	
	public String getMessage() { return message; }
	public void setMessage(String message) { this. message = message; }
	public String toString() { return message ; }
}
