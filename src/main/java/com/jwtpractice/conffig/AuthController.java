package com.jwtpractice.conffig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwtpractice.model.JWTRequest;
import com.jwtpractice.model.JWTResponse;
import com.jwtpractice.security.JwtHelper;

@RestController
public class AuthController {

	// Autowire the UserDetailsService, AuthenticationManager, and JwtHelper beans
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager manager;

	@Autowired
	private JwtHelper helper;

	private Logger logger = LoggerFactory.getLogger(AuthController.class);

	// Handle POST requests to "/auth/login" endpoint
	@PostMapping("/auth/login")
	public ResponseEntity<JWTResponse> login(@RequestBody JWTRequest request) {
		System.out.println(request.getEmail()); // Print the provided email for debugging

		// Authenticate user credentials
		this.doAuthenticate(request.getEmail(), request.getPassword());

		// Load user details from the UserDetailsService
		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

		// Generate JWT token
		String token = this.helper.generateToken(userDetails);

		// Create a new instance of JWTResponse using constructor
		JWTResponse response = new JWTResponse(token, userDetails.getUsername());

		// Return JWT token and username in the response
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Authenticate user credentials
	private void doAuthenticate(String email, String password) {
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);

		try {
			manager.authenticate(authentication);
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Invalid Username or Password!!");
		}
	}

	// Exception handler for BadCredentialsException
	@ExceptionHandler(BadCredentialsException.class)
	public String exceptionHandler() {
		return "Credentials Invalid!!";
	}
}
