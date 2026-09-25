package exceptions;

public class AuthenticationException extends RuntimeException {
	public AuthenticationException() {
		super("Invalid Username or Password");
	}
}
