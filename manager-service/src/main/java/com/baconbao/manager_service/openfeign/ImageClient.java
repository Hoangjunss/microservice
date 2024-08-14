package com.baconbao.manager_service.openfeign;

import com.baconbao.manager_service.dto.ApiResponse;
import com.baconbao.manager_service.dto.ImageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@FeignClient("image-service")
public interface ImageClient {
   @PostMapping("/save")
   ApiResponse<ImageDTO> save(MultipartFile imageFile) ;
}
