package com.baconbao.comment_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comments {
    @Id
    private Integer id;
    private String content;
    private LocalDateTime createAt;
    private  Integer idProfile;
}

