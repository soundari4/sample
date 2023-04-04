package app.customexception;

public class TransactionFailedException extends Exception {

	public TransactionFailedException() {
		super();
	}

	public TransactionFailedException(String message) {
		super(message);
	}

}
