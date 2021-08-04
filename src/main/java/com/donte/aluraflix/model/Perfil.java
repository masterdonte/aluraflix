package com.donte.aluraflix.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

@Data
@Entity
public class Perfil implements GrantedAuthority {

	private static final long serialVersionUID = 3471168869482917312L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Long id;
	
	private String nome;

	@Override
	public String getAuthority() {
		return nome;
	}

}
