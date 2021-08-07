package com.donte.aluraflix.config.jwtauth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.donte.aluraflix.exception.CustomError;
import io.jsonwebtoken.JwtException;

@Component
@Profile("jwt-auth")
public class JwtTokenFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenService tokenService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
		final String headerAuth = request.getHeader("Authorization");

		String token = ( headerAuth != null && headerAuth.startsWith("Bearer ") ) ? headerAuth.substring(7) : null;
		
		try {
			if (token != null && tokenService.validateToken(token)) {
				Authentication auth = tokenService.getAuthentication(token);
				if (auth != null) {
					SecurityContextHolder.getContext().setAuthentication(auth);
				}			
			}
			chain.doFilter(request, response);
		} catch (JwtException e) {
			sendErrorFilter(response, e.getMessage());			
		}
	}
	
	private void sendErrorFilter(HttpServletResponse response, String message) throws IOException {
		CustomError error = new CustomError(message, HttpStatus.UNAUTHORIZED.value());
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getOutputStream().println(error.toJson());
	}
}