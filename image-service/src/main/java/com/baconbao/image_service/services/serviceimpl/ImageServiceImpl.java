package com.baconbao.image_service.services.serviceimpl;

import com.baconbao.image_service.model.Image;
import com.baconbao.image_service.repository.ImageRepository;
import com.baconbao.image_service.services.CloudinaryService;
import com.baconbao.image_service.services.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private CloudinaryService cloudinaryService;

    @Override
    public Image saveImage(MultipartFile imageFile) {
        Map<String, Object> resultMap = cloudinaryService.upload(imageFile);
        String imageUrl = (String) resultMap.get("url");
        Image image= Image.builder()
                .url(imageUrl)
                .id(getGenerationId())
                .build();
        return imageRepository.save(image);

    }
    public Integer getGenerationId() {
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }
}
