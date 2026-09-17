package dto;

import entities.Role;

public class CustomerResponse {
	
	public CustomerResponse(long id, String name, String email, String address, String phone, String username) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.address = address;
		this.phone = phone;
		this.username = username;
	}
	private String username;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	private long id;
	public long getId() {
		return id;
	}
	public String getPhone() {
		return phone;
	}
	public String getName() {
		return name;
	}
	public String getEmail() {
		return email;
	}
	public String getAddress() {
		return address;
	}
	private String phone;
	private String name;
	private String email;
	private String address;
}
