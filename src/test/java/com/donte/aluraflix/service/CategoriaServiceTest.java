package com.donte.aluraflix.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;

import com.donte.aluraflix.exception.BusinessException;
import com.donte.aluraflix.feature.ScenaryFactory;
import com.donte.aluraflix.model.Categoria;
import com.donte.aluraflix.model.Video;
import com.donte.aluraflix.repository.CategoriaRepository;

class CategoriaServiceTest {	
	
	@Mock
	private CategoriaRepository repository;
	
	@InjectMocks
	private CategoriaService service;

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
	void deveSalvarUmaCategoria() {
		Mockito.when(repository.save(Mockito.any(Categoria.class))).thenReturn(ScenaryFactory.criarCategoriaComId());	
		Categoria savedCateg = service.save(new Categoria());	
		assertNotNull(savedCateg.getId());
	}

	@Test
	void buscarPorIdUmaCategoriaExistente() {
		Categoria modelo = ScenaryFactory.criarCategoriaComId();
		Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(modelo));
		Categoria categoria = service.fetchById(5L);
		assertNotNull(categoria.getId());
	}
	
	@Test
	void buscarPorIdUmaCategoriaIneExistente() {
		Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		assertThrows(EmptyResultDataAccessException.class,() -> service.fetchById(5L));		
	}

	@Test
	void deveAtualizarUmaCategoriaExistente() {
		Categoria modelo = ScenaryFactory.criarCategoriaComId();
		Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(modelo));
		
		Categoria categoriaParaAtualizar = ScenaryFactory.criarCategoria();
		service.update(5L, categoriaParaAtualizar);
		
		categoriaParaAtualizar.setId(modelo.getId());
		Mockito.verify(repository, Mockito.times(1)).save(categoriaParaAtualizar);
	}
	
	@Test
	void deveFalharAoAtualizarCategoriaLivre() {
		Categoria categLivre = ScenaryFactory.criarCategoria();
		Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(new Categoria()));
		assertThrows(BusinessException.class,() -> service.update(1L, categLivre));
		Mockito.verifyNoInteractions(repository);
	}
	
	@Test
	void deveFalharAoAtualizarUmaCategoriaInexistente() {
		Categoria categoria = ScenaryFactory.criarCategoria();
		Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		
		assertThrows(BusinessException.class, () -> service.update(5L, categoria));
		Mockito.verify(repository, Mockito.times(0)).save(Mockito.any(Categoria.class));
	}
	
	@Test
	void deveDeletarUmaCategoria() {
		Categoria categoria = ScenaryFactory.criarCategoriaComId();
		
		Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(categoria));
		
		List<Video> result = new ArrayList<Video>();
		Mockito.when(repository.findVideosByCategoria(Mockito.anyLong())).thenReturn(result);
		
		service.delete(categoria.getId());
		
		Mockito.verify(repository).deleteById(categoria.getId());
	}
	
	@Test
	void deveFalharDeletarUmaCategoriaInexistente() {
		Categoria categoria = ScenaryFactory.criarCategoriaComId();
		
		Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		
		assertThrows(BusinessException.class, () -> service.delete(categoria.getId()) );		
		Mockito.verify(repository, Mockito.times(0)).deleteById(Mockito.anyLong());
	}
	
	@Test
	void deveFalharAoDeletarCategoriaLivre() {
		Categoria categoria = ScenaryFactory.criarCategoria();
		categoria.setId(Categoria.CATEGORIA_LIVRE);
		
		BusinessException exception = assertThrows(BusinessException.class,	() -> service.delete(categoria.getId()));
		assertEquals(exception.getMessage(), "Não é atualizar/excluir esta categoria");
		Mockito.verifyNoInteractions(repository);
	}
	
	@Test
	void deveFalharAoDeletarCategoriaComVideosAssociados() {
		Categoria categoria = ScenaryFactory.criarCategoria();
		categoria.setId(2L);
		
		Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(categoria));
		
		List<Video> result = new ArrayList<Video>();
		result.add(ScenaryFactory.criarVideo());
		
		Mockito.when(repository.findVideosByCategoria(Mockito.anyLong())).thenReturn(result);
		
		BusinessException exception = assertThrows(BusinessException.class,	() -> service.delete(categoria.getId()));
		
		assertEquals(exception.getMessage(), "Existem vídeos relacionados a esta categoria");
		Mockito.verify(repository, Mockito.times(0)).deleteById(Mockito.anyLong());
	}
	
}
