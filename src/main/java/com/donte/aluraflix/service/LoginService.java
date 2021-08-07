package com.donte.aluraflix.service;

import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.donte.aluraflix.config.jwtauth.JwtTokenService;

import lombok.RequiredArgsConstructor;

@Service
@Profile("jwt-auth")
@RequiredArgsConstructor
public class LoginService {
	private final AuthenticationManager authenticationManager;
	private final JwtTokenService tokenService;
	
	public String createAuthenticationToken(Authentication auth) {
		Authentication authenticate = authenticationManager.authenticate(auth);
		return tokenService.generateToken(authenticate);
	}
}
