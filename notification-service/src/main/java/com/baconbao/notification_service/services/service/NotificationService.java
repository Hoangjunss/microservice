package com.baconbao.notification_service.services.service;

import com.baconbao.notification_service.dto.MessageDTO;
import com.baconbao.notification_service.dto.NotificationDTO;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface NotificationService {
    NotificationDTO create(NotificationDTO notificationDTO);
    NotificationDTO update(NotificationDTO notificationDTO);
    NotificationDTO findById(Integer id);
    NotificationDTO seenNotification(Integer id);
    List <NotificationDTO> getNotificationsByIdUser(Integer userId);
    void send(MessageDTO messageDTO);
}
