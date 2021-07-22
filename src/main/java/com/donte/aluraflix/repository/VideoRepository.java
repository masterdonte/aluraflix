package com.donte.aluraflix.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.donte.aluraflix.model.Video;

public interface VideoRepository extends JpaRepository<Video, Long> {

}
