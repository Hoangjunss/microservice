package com.baconbao.profile_service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Document(collection = "profile")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Profile {
    @Id
    private Integer id;
    private String objective;
    private String education;
    private String workExperience;
    private String skills;
    private Contact contact; // Refers to Contact ID, if used
    private TypeProfile typeProfile; // Enum stored as String
    private Integer idImage; // Refers to Image ID, if used
    private String title;
}
