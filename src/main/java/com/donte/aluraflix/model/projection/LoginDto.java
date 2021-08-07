package com.donte.aluraflix.model.projection;

import javax.validation.constraints.NotBlank;

public class LoginDto {
	
	@NotBlank(message = "email é obrigatório") 
	private String email;
	
	@NotBlank(message = "senha é obrigatória") 
	private String senha;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
}
