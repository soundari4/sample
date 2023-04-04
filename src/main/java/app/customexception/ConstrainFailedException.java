package app.customexception;

public class ConstrainFailedException extends Exception {
	public ConstrainFailedException() {
		super();
	}

	public ConstrainFailedException(String message) {
		super(message);
	}
}
