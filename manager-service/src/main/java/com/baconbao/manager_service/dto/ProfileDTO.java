package com.baconbao.manager_service.dto;

import com.baconbao.manager_service.models.Contact;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Integer idUser;
    private String url;

    private String title;
}
