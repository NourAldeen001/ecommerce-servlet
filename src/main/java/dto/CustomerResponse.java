package dto;

import entities.Role;

public class CustomerResponse {
	private String username;
	private long id;
	private String phone;
	private String name;
	private String email;
	private String address;

	public CustomerResponse(long id, String name, String email, String address, String phone, String username) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.address = address;
		this.phone = phone;
		this.username = username;
	}
	public String getUsername() {
		return username;
	}
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

}
