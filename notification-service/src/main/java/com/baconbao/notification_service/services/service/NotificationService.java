package com.baconbao.notification_service.services.service;

import com.baconbao.notification_service.dto.NotificationDTO;
import org.springframework.stereotype.Service;

@Service
public interface NotificationService {
    NotificationDTO create(NotificationDTO notificationDTO);
    NotificationDTO update(NotificationDTO notificationDTO);
    NotificationDTO findById(Integer id);
}
