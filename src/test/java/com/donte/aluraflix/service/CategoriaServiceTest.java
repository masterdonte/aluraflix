package com.donte.aluraflix.service;

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
import org.springframework.dao.EmptyResultDataAccessException;

import com.donte.aluraflix.feature.ScenaryFactory;
import com.donte.aluraflix.model.Categoria;
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
	void testSave() {
		Mockito.when(repository.save(Mockito.any(Categoria.class))).thenReturn(ScenaryFactory.criarCategoriaComId());	
		Categoria savedCateg = service.save(new Categoria());	
		System.out.println(savedCateg);
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
/*
	@Test
	void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	void testFetchVideosByCategoria() {
		fail("Not yet implemented");
	}
	*/
	
}
