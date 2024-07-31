package com.baconbao.image_service.services.service;

import com.baconbao.image_service.dto.ImageDTO;
import com.baconbao.image_service.model.Image;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ImageService {

    ImageDTO saveImage(MultipartFile imageFile);

}