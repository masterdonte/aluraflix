package com.donte.aluraflix.feature;

import org.assertj.core.internal.bytebuddy.utility.RandomString;

import com.donte.aluraflix.model.Categoria;
import com.donte.aluraflix.model.Video;
import com.donte.aluraflix.projection.VideoDto;

public class ScenaryFactory {
	
	public static VideoDto criarVideoDto() {
		long categoriaId = generateLongBetween(2, 25);	
		VideoDto dto = VideoDto.builder()
				.titulo("AnyTitle")
				.descricao("AnyDescription")
				.url("https://gohorseprocess.com.br/extreme-go-horse-xgh/")
				.categoriaId(categoriaId).build();
		return dto;
	}
	
	public static Video criarVideo() {
		Video video = Video.builder()
				.titulo("AnyTitle")
				.descricao("AnyDescription")
				.url("https://gohorseprocess.com.br/extreme-go-horse-xgh/")
				.categoria(criarCategoriaComId()).build();
		return video;
	}
	
	public static Categoria criarCategoria() {
		Categoria categ = Categoria.builder()
				.titulo(RandomString.make(10))
				.cor("AnyColor").build();
		return categ;
	}
	
	public static Categoria criarCategoriaComId() {
		long longId = generateLongBetween(100, 1000);		
		Categoria categ = criarCategoria();
		categ.setId(longId);		
		return categ;
	}

	public static Video criarVideoComId() {
		long longId = generateLongBetween(100, 1000);
		Video video = criarVideo();
		video.setId(longId);
		return video;
	}
	
	public static long generateLongBetween(long leftLimit, long rightLimit) {
		long generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
		return generatedLong;
	}

}
