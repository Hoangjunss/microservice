package com.baconbao.notification_service.controller;

import com.baconbao.notification_service.dto.NotificationDTO;
import com.baconbao.notification_service.services.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notification")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @PostMapping("/create")
    public ResponseEntity<NotificationDTO> create(@RequestBody NotificationDTO notificationDTO){
        return ResponseEntity.ok(notificationService.create(notificationDTO));
    }
    @PostMapping("/update")
    public ResponseEntity<NotificationDTO> update(@RequestBody NotificationDTO notificationDTO){
        return ResponseEntity.ok(notificationService.update(notificationDTO));
    }
}

