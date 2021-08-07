package com.donte.aluraflix.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

//@Component Se colocar essa annotation, nao é necessário colocar na AluraflixApplication @EnableConfigurationProperties
@Getter
@Setter
@ConfigurationProperties("system")
public class AluraflixProperty {
	
	private final Jwt jwt = new Jwt();
	
	@Getter @Setter
	public static class Jwt {
		private String secret;
		private Long expiration;
	}
	
	/*
	private final Mail mail = new Mail();
	@Getter @Setter
	public static class Mail {
		private String host;
		private Integer port;
		private String username;
		private String password;
	}*/

}
