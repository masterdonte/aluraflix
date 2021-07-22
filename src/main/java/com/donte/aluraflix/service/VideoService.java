package com.donte.aluraflix.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.donte.aluraflix.model.Video;
import com.donte.aluraflix.repository.VideoRepository;

@Service
public class VideoService {
	
	private VideoRepository repository;

	public VideoService(VideoRepository repository) {
		this.repository = repository;
	}

	@Transactional(readOnly = true)
	public List<Video> listAll() {
		return repository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Video fetchById(Long id) {		
		Optional<Video> optVideo = repository.findById(id);
		
		if(!optVideo.isPresent()) {			
			throw new EmptyResultDataAccessException(1);
		}
		
		return optVideo.get();
	}
	
	@Transactional
	public Video save(Video video) {
		return repository.save(video);
	}
	
	@Transactional
	public Video update(Long codigo, Video video) {
		Video videoSalvo = this.repository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		BeanUtils.copyProperties(video, videoSalvo, "id");
		return repository.save(videoSalvo);
	}

	@Transactional
	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	/*
	@GetMapping("{id}")
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity<?> fetchById(@PathVariable("id") Long id){
		return service.fetchById(id)
				.map( entity -> new ResponseEntity( converter(entity), HttpStatus.OK ) ) 
				.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lancamento nao encontrado na base de dados"));
	}
	
	@GetMapping("/{id}/temp")
	public ResponseEntity<?> buscarPeloCodigo(@PathVariable Long id) {
		Optional<Lancamento> lancamento = service.obterPorId(id);
		return lancamento.isPresent() ? ResponseEntity.ok(converter(lancamento.get())) : ResponseEntity.notFound().build();
	}
*/

}
