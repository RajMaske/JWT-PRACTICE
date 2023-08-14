package com.jwtpractice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JWTRequest {
    @JsonProperty("email")     // Map JSON property "email" to the field email
    private String email;

    @JsonProperty("password")  // Map JSON property "password" to the field password
    private String password;

    // Getter method for email
    public String getEmail() {
        return email;
    }

    // Setter method for email
    public void setEmail(String email) {
        this.email = email;
    }

    // Getter method for password
    public String getPassword() {
        return password;
    }

    // Setter method for password
    public void setPassword(String password) {
        this.password = password;
    }
}
