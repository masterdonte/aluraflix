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
	
	/*
	@Bean CommandLineRunner runner(VideoService service){
		return args -> {
			/*
			UsuarioDto dto = UsuarioDto.builder()
					.nome("Novo Usuario")
					.email("email@gmail.com")
					.senha("email").build();
			usuarioService.save(dto.toUsuario());			
			System.out.println("CommandLineRunner running in save user...");
		};
	}
	*/

}
