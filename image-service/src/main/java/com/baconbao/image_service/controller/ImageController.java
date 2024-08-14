package com.baconbao.image_service.controller;

import com.baconbao.image_service.dto.ApiResponse;
import com.baconbao.image_service.dto.ImageDTO;
import com.baconbao.image_service.services.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/image")
@RestController
@Slf4j
public class ImageController {
    @Autowired
    private ImageService imageService;

    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<ImageDTO>> save(@RequestPart(value="image",required = false)MultipartFile image) {
        if (image == null || image.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        // Xử lý lưu ảnh và trả về ImageDTO
        ImageDTO imageDTO = imageService.saveImage(image);
        log.info("image: {}",imageDTO);

        return ResponseEntity.ok(new ApiResponse<ImageDTO>(true, "Get all is successfully", imageDTO));
    }

    @GetMapping("getAll")
    public ResponseEntity<ApiResponse<String>> getAll() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Get all is successfully", "ok"));
    }
}
