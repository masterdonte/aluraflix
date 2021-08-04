package com.donte.aluraflix.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.donte.aluraflix.model.projection.VideoDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Video {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titulo;
	private String descricao;
	private String url;
		
	@ManyToOne 
	@JoinColumn(name = "categoriaid")
	private Categoria categoria;
	
	public Video(VideoDto dto) {
		this.id = dto.getId();
		this.titulo = dto.getTitulo();
		this.descricao = dto.getDescricao();
		this.url = dto.getUrl();
		this.categoria = new Categoria(dto.getCategoriaId());
	}
	
	public VideoDto toDto(){
		return new VideoDto(this);
	}
	
	 
    /*private LocalDateTime created;
    private LocalDateTime updated;
    @PreUpdate public void preUpdate() { updated = LocalDateTime.now(); }
    @PrePersist public void prePersist() { final LocalDateTime now = LocalDateTime.now(); created = updated = now;}
	*/
}
