package com.donte.aluraflix.model.projection;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.donte.aluraflix.model.Usuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto {
		
	private Long id;		
	
	@NotBlank(message = "nome é obrigatório") 
	@Length(min = 5, max = 100)
	private String nome;
	
	@Email
	@NotBlank(message = "e-mail é obrigatório")
	private String email;
		
	@NotBlank(message = "senha é obrigatorio")
	private String senha;
	
	private String senhaConfirm;
	
	public UsuarioDto(Usuario usuario) {
		this.id    = usuario.getId();
		this.nome  = usuario.getNome();
		this.email = usuario.getEmail();
		this.senha = usuario.getSenha();
	}
	
	public Usuario toUsuario() {
		String senhaEncodada = senha != null ? new BCryptPasswordEncoder().encode(senha) : senha;
		return Usuario.builder().id(id).nome(nome).email(email).senha(senhaEncodada).build();
	}
	
}
