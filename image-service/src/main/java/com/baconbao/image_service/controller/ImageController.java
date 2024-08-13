package com.baconbao.image_service.controller;

import com.baconbao.image_service.dto.ApiResponse;
import com.baconbao.image_service.dto.ImageDTO;
import com.baconbao.image_service.services.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/image")
@RestController
public class ImageController {
    @Autowired
    private ImageService imageService;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse<ImageDTO>> save(@RequestParam("imageFile") MultipartFile imageFile) {
        System.out.println("Received file: " + imageFile.getOriginalFilename());
        ImageDTO imageDTO = imageService.saveImage(imageFile);
        return ResponseEntity.ok(new ApiResponse<>(true, "Save is success", imageDTO));
    }

}
