package com.baconbao.project_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "project")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Project {
    @Id
    private Integer id;
    private String title;
    private String description;
    private LocalDateTime createAt;
    private Integer idImage;
    private boolean isDisplay;
    @PrePersist
    protected void onCreate() {
        createAt = LocalDateTime.now();

    }
    private String url;
    private Integer idProfile;

}

