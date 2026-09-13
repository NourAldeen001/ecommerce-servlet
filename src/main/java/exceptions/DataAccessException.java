package exceptions;

public class DataAccessException extends RuntimeException {
	public DataAccessException(String message, Throwable reason) {
		super(message, reason);
	}
}
