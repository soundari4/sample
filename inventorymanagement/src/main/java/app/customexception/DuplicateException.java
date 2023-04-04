package app.customexception;

public class DuplicateException extends Exception{

	public DuplicateException() {
		super();
	}
	
	public DuplicateException(String message) {
		super(message);
	}
}
