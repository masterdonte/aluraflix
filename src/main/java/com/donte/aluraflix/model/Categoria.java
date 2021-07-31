package com.donte.aluraflix.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {
	
	public final static Long CATEGORIA_LIVRE = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "titulo é obrigatório")	
	private String titulo;
	
	@Length(min = 2, max = 10)
	@NotBlank(message = "cor é obrigatório")
	private String cor;
	
	public Categoria(Long id) {
		this.id = id;
	}
	
	/*
	 * @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToMany(mappedBy = "categoria")
    @JsonIgnoreProperties(value = "categoria")
    private List<Video> videos = new ArrayList<>();*/

}
