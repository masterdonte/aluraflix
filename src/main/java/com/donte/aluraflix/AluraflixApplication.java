package com.donte.aluraflix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AluraflixApplication {

	public static void main(String[] args) {
		SpringApplication.run(AluraflixApplication.class, args);
	}
	
	/*
	@Bean CommandLineRunner runner(VideoService service){
		return args -> {
			/*
			UsuarioDto dto = UsuarioDto.builder()
					.nome("Jonathas Campos Pimenta")
					.email("donte.master@gmail.com")
					.senha("Abc123").build();
			usuarioService.save(dto.toUsuario());			
			System.out.println("CommandLineRunner running in save user...");
		};
	}
	*/

}
