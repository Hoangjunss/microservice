package com.baconbao.project_service.openFeign;

import com.baconbao.project_service.dto.ApiResponse;
import com.baconbao.project_service.dto.ImageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "image-service")
public interface ImageClient {
    @PostMapping(value = "/image/save", consumes = "multipart/form-data")
    ApiResponse<ImageDTO> save(@RequestPart(value="image",required = false)MultipartFile image) ;
    @GetMapping("/image/getAll")
    ApiResponse<String> getAll() ;
}
