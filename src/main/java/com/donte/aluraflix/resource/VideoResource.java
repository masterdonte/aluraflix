package com.donte.aluraflix.resource;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.HttpStatus;
import com.donte.aluraflix.model.Video;
import com.donte.aluraflix.service.VideoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/videos")
@RequiredArgsConstructor
public class VideoResource {
	
	private final VideoService service;

	@GetMapping	
	public ResponseEntity<?> list() {		
		List<Video> result = service.listAll();		
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<?> fetchById(@PathVariable Long id){
		Video video = service.fetchById(id);
		return ResponseEntity.ok(video);
	}
	
	@PostMapping
	public ResponseEntity<?> save(@RequestBody @Valid Video video){
		Video newVideo = service.save(video);
		URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(newVideo.getId()).toUri();
		return ResponseEntity.created(location).body(newVideo);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Video video) {
		Video newVideo = service.update(id, video);
		return ResponseEntity.ok(newVideo);
	}
	
	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}
	
}
