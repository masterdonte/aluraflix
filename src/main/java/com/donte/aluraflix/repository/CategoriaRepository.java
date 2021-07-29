package com.donte.aluraflix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.donte.aluraflix.model.Categoria;
import com.donte.aluraflix.model.Video;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
	
	@Query("SELECT v FROM Video v JOIN FETCH v.categoria c WHERE c.id = :id")
    List<Video> findVideosByCategoria(@Param("id") Long id);	

}
