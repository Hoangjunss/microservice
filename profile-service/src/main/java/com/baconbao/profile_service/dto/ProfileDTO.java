package com.baconbao.profile_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
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
    private MultipartFile imageFile;

}
