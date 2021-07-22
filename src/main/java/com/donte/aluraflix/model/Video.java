package com.donte.aluraflix.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Video {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "titulo é obrigatório")
	@Length(min = 5, max = 50)
	private String titulo;
	
	@Length(min = 5, max = 500)
	@NotBlank(message = "descricao é obrigatório")
	private String descricao;
	
	@URL
	@NotBlank(message = "url é obrigatorio")
	private String url;

}
