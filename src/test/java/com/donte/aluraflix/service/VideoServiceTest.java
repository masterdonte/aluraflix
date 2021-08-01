package com.donte.aluraflix.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.donte.aluraflix.exception.BusinessException;
import com.donte.aluraflix.feature.ScenaryFactory;
import com.donte.aluraflix.model.Categoria;
import com.donte.aluraflix.model.Video;
import com.donte.aluraflix.repository.CategoriaRepository;
import com.donte.aluraflix.repository.VideoRepository;

class VideoServiceTest {
	
	@InjectMocks
	private VideoService service;
	@Mock
	private VideoRepository repository;
	@Mock
	private CategoriaRepository categoriaRepository;
	private AutoCloseable closeable;
	
	@BeforeEach
    void initService() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void closeService() throws Exception {
        closeable.close();
    }
    
    @Test
	void deveSalvarUmaVideo() {
    	Categoria categoria = ScenaryFactory.criarCategoriaComId();
    	Mockito.when(categoriaRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(categoria));
    	
    	service.save(ScenaryFactory.criarVideo());
    	
    	Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(Video.class));
	}
    
    @Test
	void deveFalharAoSalvarVideoComCategoriaInexistente() {
    	Video video = ScenaryFactory.criarVideoComId();
    	Mockito.when(categoriaRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
    	assertThrows(BusinessException.class, () -> service.save(video) );
    	Mockito.verifyNoInteractions(repository);
	}
    
    @Test
	void buscarPorIdUmVideoExistente() {
		Video modelo = ScenaryFactory.criarVideoComId();
		Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(modelo));
		Video video = service.fetchById(Mockito.anyLong());
		assertNotNull(video.getId());
	}
	
	@Test
	void buscarPorIdUmVideoInexistente() {
		Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		assertThrows(BusinessException.class,() -> service.fetchById(5L));		
	}
	
	@Test
	void deveAtualizarUmVideo(){
		Categoria categoria = ScenaryFactory.criarCategoriaComId();
		Video savedVideo = ScenaryFactory.criarVideoComId();

		Mockito.when(categoriaRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(categoria));
    	Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(savedVideo));	
    	
    	service.update(savedVideo.getId(), ScenaryFactory.criarVideo());
    	
    	Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(Video.class));
	}

	@Test
	void deveFalharAoAtualizarUmVideoInexistente(){
		long videoId = ScenaryFactory.generateLongBetween(1000, 10000);	
		Categoria categoria = ScenaryFactory.criarCategoriaComId();

		Mockito.when(categoriaRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(categoria));
    	Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
    	
    	String message = assertThrows(BusinessException.class,() -> service.update(videoId, ScenaryFactory.criarVideo())).getMessage();	
    	assertEquals(message, "Vídeo não encontrado");
	}
	
	@Test
	void deveFalharAoAtualizarUmVideoComCategoriaInexistente(){
		long videoId = ScenaryFactory.generateLongBetween(1000, 10000);	
		Mockito.when(categoriaRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		
		String message = assertThrows(BusinessException.class,() -> service.update(videoId, ScenaryFactory.criarVideo())).getMessage();
		
		assertEquals(message, "Categoria não cadastrada");
		Mockito.verifyNoInteractions(repository);
	}
    
	@Test
	void deveDeletarUmVideo() {
		Video video = ScenaryFactory.criarVideoComId();
		
		Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(video));
		service.delete(video.getId());
		
		Mockito.verify(repository).deleteById(video.getId());
	}
	
	@Test
	void deveFalharAoDeletarUmVideoInexistente() {
		Video video = ScenaryFactory.criarVideoComId();
		
		Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		
		assertThrows(BusinessException.class, () -> service.delete(video.getId()) );
		Mockito.verify(repository, Mockito.times(0)).deleteById(Mockito.anyLong());
	}


}
