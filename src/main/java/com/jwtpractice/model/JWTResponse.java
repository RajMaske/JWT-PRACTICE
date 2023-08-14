package com.jwtpractice.model;

public class JWTResponse {

	// Private fields to hold the JWT token and the username
	private String jwtToken;
	private String username;

	// Constructor that takes the token and username as parameters
	public JWTResponse(String token, String username) {
		jwtToken = token;
		this.username = username;
	}

	// Getter method for retrieving the JWT token
	public String getJwtToken() {
		return jwtToken;
	}

	// Setter method for setting the JWT token
	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}

	// Getter method for retrieving the username
	public String getUsername() {
		return username;
	}

	// Setter method for setting the username
	public void setUsername(String username) {
		this.username = username;
	}
}
