package com.donte.aluraflix.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.donte.aluraflix.exception.BusinessException;
import com.donte.aluraflix.model.Categoria;
import com.donte.aluraflix.model.Video;
import com.donte.aluraflix.repository.CategoriaRepository;

@Service
public class CategoriaService {
	
	private CategoriaRepository repository;

	public CategoriaService(CategoriaRepository repository) {
		this.repository = repository;
	}

	@Transactional(readOnly = true)
	public List<Categoria> listAll() {
		return repository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Categoria fetchById(Long id) {		
		Optional<Categoria> optCategoria = repository.findById(id);
		
		if(!optCategoria.isPresent()) {			
			throw new EmptyResultDataAccessException(1);
		}
		
		return optCategoria.get();
	}
	
	@Transactional
	public Categoria save(Categoria categoria) {
		return repository.save(categoria);
	}
	
	@Transactional
	public Categoria update(Long id, Categoria categoria) {
		if(id.equals(Categoria.CATEGORIA_LIVRE))
			throw new BusinessException("Não é atualizar/excluir esta categoria");
		
		Categoria categoriaSalva = this.repository.findById(id).orElseThrow(() -> new BusinessException("Categoria Inexistente"));
		BeanUtils.copyProperties(categoria, categoriaSalva, "id");
		return repository.save(categoriaSalva);
	}

	@Transactional
	public void delete(Long id) {
		if(id.equals(Categoria.CATEGORIA_LIVRE))
			throw new BusinessException("Não é atualizar/excluir esta categoria");
		
		repository.findById(id).orElseThrow(() -> new BusinessException("Categoria inexistente"));
		
		List<Video> videos = repository.findVideosByCategoria(id);
		
		if(!videos.isEmpty())
			throw new BusinessException("Existem vídeos relacionados a esta categoria");
		repository.deleteById(id);
	}

	@Transactional(readOnly = true)
	public List<Video> fetchVideosByCategoria(Long id) {
		return repository.findVideosByCategoria(id);		
	}
	

}
