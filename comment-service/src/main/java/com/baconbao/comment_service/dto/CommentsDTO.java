package com.baconbao.comment_service.dto;

import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentsDTO {
    private Integer id;
    private String content;
    private LocalDateTime createAt;
    private Integer idProfile;
}
