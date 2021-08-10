package com.donte.aluraflix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.donte.aluraflix.config.AluraflixProperty;

@SpringBootApplication
@EnableConfigurationProperties(AluraflixProperty.class)
public class AluraflixApplication {

	public static void main(String[] args) {
		SpringApplication.run(AluraflixApplication.class, args);
	}
	
}
