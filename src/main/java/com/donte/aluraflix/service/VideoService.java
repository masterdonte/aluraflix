package com.donte.aluraflix.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.donte.aluraflix.exception.BusinessException;
import com.donte.aluraflix.model.Categoria;
import com.donte.aluraflix.model.Video;
import com.donte.aluraflix.repository.CategoriaRepository;
import com.donte.aluraflix.repository.VideoRepository;

@Service
public class VideoService {
	
	private CategoriaRepository categoriaRepository;
	private VideoRepository repository;

	public VideoService(VideoRepository repository, CategoriaRepository categoriaRepository) {
		this.repository = repository;
		this.categoriaRepository = categoriaRepository;
	}

	@Transactional(readOnly = true)
	public List<Video> listAll(Video videoFiltro) {
		Example<Video> example = Example.of(videoFiltro, ExampleMatcher.matchingAny().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING));
		return repository.findAll(example);		
	}
	
	@Transactional(readOnly = true)
	public Video fetchById(Long id) {		
		Optional<Video> optVideo = repository.findById(id);
		
		if(!optVideo.isPresent()) {			
			throw new BusinessException("Video inexistente");
		}
		
		return optVideo.get();
	}
	
	@Transactional
	public Video save(Video video) {
		validar(video);		
		return repository.save(video);		
	}
	
	@Transactional
	public Video update(Long codigo, Video video) {
		validar(video);
		Video videoSalvo = this.repository.findById(codigo).orElseThrow(() -> new BusinessException("Vídeo não encontrado"));
		BeanUtils.copyProperties(video, videoSalvo, "id");
		return repository.save(videoSalvo);	
	}

	@Transactional
	public void delete(Long id) {
		repository.findById(id).orElseThrow(() -> new BusinessException("Vídeo inexistente"));
		repository.deleteById(id);
	}	
	
	private void validar(Video video) {
		Long categoriaId = video.getCategoria() == null ? Categoria.CATEGORIA_LIVRE : video.getCategoria().getId();
		categoriaRepository.findById(categoriaId).orElseThrow(() -> new BusinessException("Categoria não cadastrada"));		
	}

}
