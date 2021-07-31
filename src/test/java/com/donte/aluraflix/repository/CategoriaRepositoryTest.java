package com.donte.aluraflix.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import com.donte.aluraflix.feature.ScenaryFactory;
import com.donte.aluraflix.model.Categoria;

@DataJpaTest
@ActiveProfiles("test")
class CategoriaRepositoryTest {
	
	@Autowired
	CategoriaRepository repository;
	
	@Autowired
	TestEntityManager manager;
	
	@Test
	public void deveSalvarUmaCategoria() {
		Categoria categoria = ScenaryFactory.criarCategoria();
		Categoria categ = repository.save(categoria);
		assertNotNull(categ.getId());
		assertTrue(repository.count() > 1);// A primeira categoria é criada pelo flyway quando a aplicacao inicia
	}
	
	@Test
	public void deveAtualizarUmaCategoria() {
		Categoria categoria = createCategoriaAndPersist();
		
		String novoTitulo = RandomString.make(20);
		String novaCor    = RandomString.make(10);
		
		categoria.setTitulo(novoTitulo);
		categoria.setCor(novaCor);
		
		repository.save(categoria);
		
		Categoria categoriaAtualizada = manager.find(Categoria.class, categoria.getId());
		
		assertEquals(categoriaAtualizada.getTitulo(), novoTitulo);
		assertEquals(categoriaAtualizada.getCor(), novaCor);
	}
	
	@Test
	public void deveBuscarUmaCategoriaPorId() {
		Categoria categoria = createCategoriaAndPersist();
		System.out.println("Buscar uma categoria que foi salva" + categoria.getId());
		Optional<Categoria> optCategoria = repository.findById(categoria.getId());
		assertTrue(optCategoria.isPresent());		
	}
	
	@Test
	public void deveDeletarUmaCategoria() {
		Categoria categoria = createCategoriaAndPersist();
		categoria = manager.find(Categoria.class, categoria.getId());
		repository.delete(categoria);
		Categoria notFoundCategoria = manager.find(Categoria.class, categoria.getId());
		assertNull(notFoundCategoria);		
	}
		
	private Categoria createCategoriaAndPersist() {
		Categoria categoria = ScenaryFactory.criarCategoria();
		return manager.persist(categoria);
	}

}
