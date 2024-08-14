package com.baconbao.profile_service.openFeign;

import com.baconbao.profile_service.config.FeignClientConfig;
import com.baconbao.profile_service.dto.ApiResponse;
import com.baconbao.profile_service.dto.ImageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "image-service", configuration = FeignClientConfig.class)

public interface ImageClient {
    @PostMapping(value = "/image/save", consumes = "multipart/form-data")
    ApiResponse<ImageDTO> save(@RequestPart(value="image",required = false)MultipartFile image) ;

    @GetMapping("/image/getAll")
    ApiResponse<String> getAll();
}
