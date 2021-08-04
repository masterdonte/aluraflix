package com.donte.aluraflix.resource;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.donte.aluraflix.model.Categoria;
import com.donte.aluraflix.model.Video;
import com.donte.aluraflix.model.projection.VideoDto;
import com.donte.aluraflix.service.CategoriaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaResource {
	
	private final CategoriaService service;

	@GetMapping	
	public ResponseEntity<?> list(@RequestParam(required = false, defaultValue = "0", name = "page") int page,
	        					  @RequestParam(required = false, defaultValue = "5", name = "size") int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Categoria> result = service.listAll(pageable);		
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<?> fetchById(@PathVariable Long id){
		Categoria categoria = service.fetchById(id);
		return ResponseEntity.ok(categoria);
	}
	
	@GetMapping("{id}/videos")
	public ResponseEntity<?> fetchVideosByCategoria(@PathVariable Long id){
		List<Video> videos = service.fetchVideosByCategoria(id);
		return ResponseEntity.ok(VideoDto.converter(videos));
	}
	
	@PostMapping
	public ResponseEntity<?> save(@RequestBody @Valid Categoria video){
		Categoria newCategoria = service.save(video);
		URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(newCategoria.getId()).toUri();
		return ResponseEntity.created(location).body(newCategoria);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Categoria categoria) {
		Categoria newCategoria = service.update(id, categoria);
		return ResponseEntity.ok(newCategoria);
	}
	
	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}
	
}
