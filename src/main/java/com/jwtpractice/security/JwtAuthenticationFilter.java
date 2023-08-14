package com.jwtpractice.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

	@Autowired
	private JwtHelper jwtHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// Extract the token from the Authorization header
		String requestHeader = request.getHeader("Authorization");
		String username = null;
		String token = null;

		if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
			token = requestHeader.substring(7); // Remove "Bearer "

			// Validate the token and extract the username
			try {
				username = this.jwtHelper.getUsernameFromToken(token);
			} catch (IllegalArgumentException e) {
				logger.info("Illegal Argument while fetching the username !!", e);
			} catch (ExpiredJwtException e) {
				logger.info("Given jwt token is expired !!", e);
			} catch (MalformedJwtException e) {
				logger.info("Some changes have been made in the token !! Invalid Token", e);
			} catch (Exception e) {
				logger.error("Error while parsing token", e);
			}
		} else {
			logger.info("Invalid Header Value !! ");
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

			// Validate the token with userDetails and set up the Authentication
			Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);

			if (validateToken) {
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} else {
				logger.info("Validation fails !!");
			}
		}

		// Continue the filter chain
		filterChain.doFilter(request, response);
	}
}
