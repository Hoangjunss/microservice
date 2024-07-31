package com.baconbao.image_service.controller;

import com.baconbao.image_service.dto.ImageDTO;
import com.baconbao.image_service.services.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class ImageController {
    @Autowired
    private ImageService imageService;
    @PostMapping("/save")
    public ResponseEntity<ImageDTO> save(MultipartFile imageFile) {
        return ResponseEntity.ok(imageService.saveImage(imageFile));
    }
}
