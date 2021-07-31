package com.donte.aluraflix.resource;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

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

import com.donte.aluraflix.model.Video;
import com.donte.aluraflix.projection.VideoDto;
import com.donte.aluraflix.service.VideoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/videos")
@RequiredArgsConstructor
public class VideoResource {
	
	private final VideoService service;

	@GetMapping	
	public ResponseEntity<?> list(@RequestParam(value = "search", required = false) String search) {
		Video videoFiltro = Video.builder().titulo(search).descricao(search).build();
		List<VideoDto> result = VideoDto.converter(service.listAll(videoFiltro));
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<?> fetchById(@PathVariable Long id) {		
		VideoDto dto = new VideoDto(service.fetchById(id));
		return ResponseEntity.ok(dto);
	}
	
	@PostMapping
	public ResponseEntity<?> save(@RequestBody @Valid VideoDto dto){
		Video video = service.save(new Video(dto));
		URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(video.getId()).toUri();
		return ResponseEntity.created(location).body(new VideoDto(video));
	}
	
	@PutMapping("{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody VideoDto dto) {
		Video video = service.update(id, new Video(dto));
		return ResponseEntity.ok(new VideoDto(video));
	}
	
	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}
	
}
