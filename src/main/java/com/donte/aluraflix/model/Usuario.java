package com.donte.aluraflix.model;

import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.donte.aluraflix.model.projection.UsuarioDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements UserDetails{

	private static final long serialVersionUID = -1978860666733815203L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String email;
	private String senha;	
		
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuarioperfil", joinColumns = @JoinColumn(name = "usuarioid"), inverseJoinColumns = @JoinColumn(name = "perfilid"))
	private List<Perfil> perfis;
	
	public UsuarioDto toDto() {
		return UsuarioDto.builder().id(id).nome(nome).email(email).senha(senha).build();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {		
		return this.perfis;
	}

	@Override
	public String getPassword() {
		return this.senha;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
   
}
