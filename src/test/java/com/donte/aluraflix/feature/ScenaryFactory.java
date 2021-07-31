package com.donte.aluraflix.feature;

import java.util.Random;

import org.assertj.core.internal.bytebuddy.utility.RandomString;

import com.donte.aluraflix.model.Categoria;
import com.donte.aluraflix.model.Video;
import com.donte.aluraflix.projection.VideoDto;

public class ScenaryFactory {
	
	public static VideoDto criarVideoDto() {
		VideoDto dto = VideoDto.builder()
				.titulo("AnyTitle")
				.descricao("AnyDescription")
				.url("https://gohorseprocess.com.br/extreme-go-horse-xgh/")
				.categoriaId(Categoria.CATEGORIA_LIVRE).build();
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
		long generatedLong = new Random().nextLong();		
		Categoria categ = criarCategoria();
		categ.setId(generatedLong);		
		return categ;
	}

}
