package com.ai.model;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;


public class Login implements Serializable{
	@NotEmpty
	private String name;
	@NotEmpty
	private String password;
	@NotEmpty
	private String email;
	
	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public Login() {}
	

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
