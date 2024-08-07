package com.baconbao.notification_service.controller;

import com.baconbao.notification_service.dto.NotificationDTO;
import com.baconbao.notification_service.services.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("notification")
@RestController
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
    @GetMapping("/findByUser")
    public ResponseEntity<List<NotificationDTO>> findByUser(@RequestParam Integer userId){
        return ResponseEntity.ok(notificationService.getNotificationsByIdUser(userId));
    }
}

