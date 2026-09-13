package dto;

import util.Validator;

public class SignUpRequest {
	
	private String email;
	private String username;
	private String name;
	private String phone;
	private String address;
	private String password;
	
	public SignUpRequest(String email, String username, String name,
			String phone, String address, String password, String confirmPassword) {
		
		setEmail(email);
		setUsername(username);
		setName(name);
		setPhone(phone);
		setAddress(address);
		setPassword(password, confirmPassword);
	}
	

	public void setEmail(String email) {
		if(Validator.isValidEmail(email)) {
			this.email = email;
			return;
		}
		throw new IllegalArgumentException("Invalid Email");
	}
	
	public void setUsername(String username) {
		if(Validator.isNotBlank(username)) {
			this.username = username;
			return;
		}
		throw new IllegalArgumentException("Username must not be empty or blank");
	}
	
	public void setName(String name) {
		if(Validator.isNotBlank(name)) {
			this.name = name;
			return;
		}
		throw new IllegalArgumentException("Name must not be empty or blank");
		
	}
	
	public void setPhone(String phone) {
		if(Validator.isValidPhone(phone)) {
			this.phone = phone;
			return;
		}
		throw new IllegalArgumentException("Invaild Phone number");
	}
	
	public void setAddress(String address) {
		if(Validator.isNotBlank(address)) {
			this.address = address;
			return;
		}
		throw new IllegalArgumentException("Address must not be empty or blank");
	}
	
	public void setPassword(String password, String confirmPassword) {
		if(Validator.isStrongPassword(password)) {
			if(password.equals(confirmPassword)) {
				this.password = password;
				return;
			}
			throw new IllegalArgumentException("Password and Confirm Password must be equals");
		}
		throw new IllegalArgumentException("Password must have at least one uppercase,"
								+ "lowercase, and special char with letter length at least 8");
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getPassword() {
		return password;
	}

}
