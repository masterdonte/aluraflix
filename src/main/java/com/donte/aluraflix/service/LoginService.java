package com.donte.aluraflix.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.donte.aluraflix.config.jwtauth.JwtTokenService;
import com.donte.aluraflix.event.EnviarEmailEvent;
import com.donte.aluraflix.model.projection.UsuarioDto;

import lombok.RequiredArgsConstructor;

@Service
@Profile("jwt-auth")
@RequiredArgsConstructor
public class LoginService {
	
	private final AuthenticationManager authenticationManager;
	private final JwtTokenService tokenService;
	private final ApplicationEventPublisher publisher;
		
	public String createAuthenticationToken(Authentication auth) {
		Authentication authenticate = authenticationManager.authenticate(auth);
		return tokenService.generateToken(authenticate);
	}
	
	public void testeEnvioEmail(String email) throws Exception {
		System.out.println("inicio envio e-mail....");
		long inicio = System.currentTimeMillis();
		UsuarioDto dto = UsuarioDto.builder().nome("john.doe").email(email).build();
		
        String assunto = "Assunto de teste email freemarker";
        String template = "email.ftlh";
        Map<String, Object> model = new HashMap<>();
        List<String> recips = Arrays.asList(email);

        model.put("user", dto);
        publisher.publishEvent(new EnviarEmailEvent(this, assunto, template, model, recips));
		
		long tempo  = System.currentTimeMillis() - inicio;
		System.out.println("Fim envio e-mail.... " + ((int)tempo/1000));
	}
	
}
