package com.donte.aluraflix.projection;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;
import com.donte.aluraflix.model.Video;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VideoDto {

	private Long id;
	
	private Long categoriaId;
	
	@NotBlank(message = "titulo é obrigatório") 
	@Length(min = 5, max = 50)
	private String titulo;
	
	@Length(min = 5, max = 500)
	@NotBlank(message = "descricao é obrigatório")
	private String descricao;
	
	@URL
	@NotBlank(message = "url é obrigatorio")
	private String url;
	
	public VideoDto(Video video) {
		this.id 		 = video.getId();
		this.titulo 	 = video.getTitulo();
		this.url 		 = video.getUrl();
		this.descricao   = video.getDescricao();
		this.categoriaId = video.getCategoria().getId();
	}
	
	public static List<VideoDto> converter(List<Video> videos) {
		return videos.stream().map(VideoDto::new).collect(Collectors.toList());
	}

}
