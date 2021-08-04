package com.donte.aluraflix.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import com.donte.aluraflix.feature.ScenaryFactory;
import com.donte.aluraflix.model.Video;
import com.donte.aluraflix.model.projection.VideoDto;

@DataJpaTest // cria uma transacao em cada metodo de teste
@ActiveProfiles("test")
class VideoRepositoryTest {
	//Apenas a categoria 1 é criada ao rodar o projeto pelo flyway
	
	@Autowired
	VideoRepository repository;
	
	@Autowired
	TestEntityManager manager;
	
	@Test
	public void deveSalvarUmVideo() {
		VideoDto dto = ScenaryFactory.criarVideoDto();
		Video video = repository.save( new Video(dto));
		assertNotNull(video.getId());		
	}
	
	@Test
	public void deveAtualizarUmVideo() {
		Video video = createVideoAndPersist();
		
		String novoTitulo    = RandomString.make(20);
		String novaDescricao = RandomString.make(20);
		String novaUrl       = "https:/novaurl.com";
		
		video.setTitulo(novoTitulo);
		video.setDescricao(novaDescricao);
		video.setUrl(novaUrl);
		
		repository.save(video);
		Video videoAtualizado = manager.find(Video.class, video.getId());
		
		assertEquals(videoAtualizado.getTitulo(), novoTitulo);
		assertEquals(videoAtualizado.getDescricao(), novaDescricao);
		assertEquals(videoAtualizado.getUrl(), novaUrl);
	}
	
	@Test
	public void deveBuscarUmVideoPorId() {
		Video video = createVideoAndPersist();
		Optional<Video> optVideo = repository.findById(video.getId());
		assertTrue(optVideo.isPresent());		
	}
	
	@Test
	public void deveDeletarUmVideo() {
		Video video = createVideoAndPersist();
		video = manager.find(Video.class, video.getId());
		repository.delete(video);
		Video notFoundVideo = manager.find(Video.class, video.getId());
		assertNull(notFoundVideo);		
	}
	
	@Test	
	public void deveFalharAoSalvarUmVideoComCategoriaInexistente() {
		VideoDto dto = ScenaryFactory.criarVideoDto();
		dto.setCategoriaId(1000L);		
		assertThrows(DataIntegrityViolationException.class,() -> repository.save(new Video(dto)) );
	}
		
	private Video createVideoAndPersist() {
		VideoDto dto = ScenaryFactory.criarVideoDto();
		return manager.persist(new Video(dto));
	}

}
