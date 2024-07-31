package com.baconbao.project_service.openFeign;

import com.baconbao.project_service.dto.ImageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "image-service")
public interface ImageClient {
    @PostMapping("/save")
    ImageDTO save(MultipartFile imageFile) ;
}
