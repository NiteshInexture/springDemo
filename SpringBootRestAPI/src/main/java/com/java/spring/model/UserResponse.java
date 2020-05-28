package com.java.spring.model;

public class UserResponse {

	private final String jwt;

	public String getJwt() {
		return jwt;
	}

	public UserResponse(String jwt) {
		this.jwt = jwt;
	}
}
