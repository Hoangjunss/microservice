package com.baconbao.notification_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    @Id
    private Integer id;
    private String message;
    private LocalDateTime createAt;
    private  Integer idUser;
    private String url;
    private boolean isRead;
}