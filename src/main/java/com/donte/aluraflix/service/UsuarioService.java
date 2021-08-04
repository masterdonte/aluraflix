package com.donte.aluraflix.service;

import java.util.Arrays;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.donte.aluraflix.exception.BusinessException;
import com.donte.aluraflix.model.Perfil;
import com.donte.aluraflix.model.Usuario;
import com.donte.aluraflix.repository.PerfilRepository;
import com.donte.aluraflix.repository.UsuarioRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UsuarioService implements UserDetailsService {
	
	private UsuarioRepository repository;
	private PerfilRepository perfilRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = repository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Wrong username or password"));
		return usuario;
	}
	
	@Transactional
	public Usuario save(Usuario usuario) {
		repository.findByEmail(usuario.getEmail()).ifPresent(p -> {
			throw new BusinessException("Usuário já cadastrado");
		});
		
		Perfil perfil = perfilRepository.findByNome("ADMIN").orElseThrow(() -> new BusinessException("Perfil não encontrado"));
		usuario.setPerfis(Arrays.asList(perfil));
		
		return repository.save(usuario);
	}
	
	@Transactional
	public void resetPassword(Usuario usuario) {
		//TODO reiniciar senha
	}	

}
