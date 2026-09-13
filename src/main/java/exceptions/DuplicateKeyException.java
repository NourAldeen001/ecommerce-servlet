package exceptions;

public class DuplicateKeyException extends DataAccessException {

	public DuplicateKeyException(String message, Throwable reason) {
		super(message, reason);
	}

}
