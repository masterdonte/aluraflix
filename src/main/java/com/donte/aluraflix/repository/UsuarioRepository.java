package com.donte.aluraflix.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.donte.aluraflix.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByEmail(String email);
	
}
