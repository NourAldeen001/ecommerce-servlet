package dto;

import util.Validator;

public class LoginRequest {
	
	private String username;
	private String password;
	
	public LoginRequest(String username, String password) {
		setUsername(username);
		setPassword(password);
	}
	
	public void setUsername(String username) {
		if(Validator.isNotBlank(username)) {
			this.username = username;
			return;
		}
		throw new IllegalArgumentException("Invalid Email");
	}
	
	public void setPassword(String password) {
		if(Validator.isStrongPassword(password)) {
			this.password = password;
			return;
		}
		throw new IllegalArgumentException("Password must have at least one uppercase,"
								+ "lowercase, and special char with letter length at least 8");
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}

}
