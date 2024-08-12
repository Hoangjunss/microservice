package com.baconbao.image_service.services;

import com.baconbao.image_service.exception.CloudinaryException;
import com.baconbao.image_service.exception.Error;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class CloudinaryService {
    public Cloudinary cloudinary;

    // contructor
    public CloudinaryService() {
        Map<String, String> valuesMap = new HashMap<>();
        valuesMap.put("cloud_name", "dgts7tmnb");
        valuesMap.put("api_key", "397588242457691");
        valuesMap.put("api_secret", "oWxqnyZyL43qKZx93bYgQpGfNfI");
        cloudinary = new Cloudinary(valuesMap);
    }

    // tai hinh anh len cloud
    public Map upload(MultipartFile multipartFile) {
        try {
            log.info("Uploading photo to clound: {}", multipartFile.getOriginalFilename());
            File file = convert(multipartFile);
            Map result = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
            if (!Files.deleteIfExists(file.toPath())) {
                log.error("Unable to upload file: {}", file.getAbsolutePath());
                throw new IOException("Unable to upload temporary file: " + file.getAbsolutePath());
            }
            return result;
        } catch (IOException e) {
            log.error("Unable to upload file: {}", e.getMessage());
            throw new CloudinaryException(Error.UPLOAD_FAILED);
        }
    }

    // xoa anh tren cloud
    public Map delete(String id) throws IOException {
        try {
            log.info("Deleting photo from cloud: {}", id);
            return cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
        } catch (IOException e) {
            log.error("Unable to delete file: {}", e.getMessage());
            throw new CloudinaryException(Error.DELETE_FAILED);
        }
    }

    // chuyen anh thanh file
    private File convert(MultipartFile multipartFile) throws IOException {
        try {
            File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            FileOutputStream fo = new FileOutputStream(file);
            fo.write(multipartFile.getBytes());
            fo.close();
            return file;
        } catch (IOException e) {
            log.error("Unable to convert file: {}", e.getMessage());
            throw new CloudinaryException(Error.CONVERSION_FAILED);
        }
    }

    public String getImageUrl(String publicId) {
        return cloudinary.url().generate(publicId);
    }

    public String getPublicId(String url) {
        // URL mẫu:
        // http://res.cloudinary.com/dgts7tmnb/image/upload/v1718723087/oax0ufrlkzdyjslbxv0c.png
        String[] parts = url.split("/");
        String publicIdWithExtension = parts[parts.length - 1];
        String publicId = publicIdWithExtension.split("\\.")[0];
        // tra ve oax0ufrlkzdyjslbxv0c
        return publicId;
    }
}
