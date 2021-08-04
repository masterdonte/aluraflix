package com.donte.aluraflix.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.donte.aluraflix.model.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {

	Optional<Perfil> findByNome(String string);

}
