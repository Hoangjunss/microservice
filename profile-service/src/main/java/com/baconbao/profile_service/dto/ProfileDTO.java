package com.baconbao.profile_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import com.baconbao.profile_service.model.Contact;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDTO {
    private Integer id;
    private String objective;
    private String education;
    private String workExperience;
    private String skills;
    private Contact contact;
    private String typeProfile;
    private Integer userId;
    private String url;
    private MultipartFile imageFile;

}
