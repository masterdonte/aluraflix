package com.donte.aluraflix.resource;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.donte.aluraflix.model.projection.LoginDto;
import com.donte.aluraflix.model.projection.TokenDto;
import com.donte.aluraflix.service.LoginService;

@Profile("jwt-auth")
@RestController
public class LoginResource {

	@Autowired
	private  LoginService service;

	@PostMapping("/token")
	public ResponseEntity<?> login(@RequestBody @Valid LoginDto login){
		UsernamePasswordAuthenticationToken userPassAuthToken = new UsernamePasswordAuthenticationToken(login.getEmail(), login.getSenha());
		final String token = service.createAuthenticationToken(userPassAuthToken);
		return ResponseEntity.ok(new TokenDto(token));
	}

}
