package com.baconbao.profile_service.openFeign;

import com.baconbao.profile_service.config.FeignClientConfig;
import com.baconbao.profile_service.dto.ApiResponse;
import com.baconbao.profile_service.dto.ImageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "image-service", configuration = FeignClientConfig.class)

public interface ImageClient {
    @PostMapping(value ="/image/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ImageDTO save(@RequestParam(value = "imageFile" , required = false) MultipartFile imageFile) ;

    @GetMapping("/image/getAll")
    ApiResponse<String> getAll();
}
