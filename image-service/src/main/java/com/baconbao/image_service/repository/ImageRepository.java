package com.baconbao.image_service.repository;

import com.baconbao.image_service.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    Optional<Image> findById(Integer integer);
}
