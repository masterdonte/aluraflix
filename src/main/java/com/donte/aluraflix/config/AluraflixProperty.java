package com.donte.aluraflix.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

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

}

//Se colocar essa annotation @Component nesta classe, é necessário colocar na AluraflixApplication @EnableConfigurationProperties