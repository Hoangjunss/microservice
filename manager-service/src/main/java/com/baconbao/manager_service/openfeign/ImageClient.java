package com.baconbao.manager_service.openfeign;

import com.baconbao.manager_service.dto.ApiResponse;
import com.baconbao.manager_service.dto.ImageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

@FeignClient("image-service")
public interface ImageClient {
   ApiResponse<ImageDTO> save(MultipartFile imageFile) ;
}
