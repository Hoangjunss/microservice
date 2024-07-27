package com.baconbao.profile_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Entity
@Table(name = "project")
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


    @OneToOne
    @JoinColumn(name = "idContact", referencedColumnName = "id")
    private Contact contact;

  @Enumerated(EnumType.STRING)
    private TypeProfile typeProfile;


}
