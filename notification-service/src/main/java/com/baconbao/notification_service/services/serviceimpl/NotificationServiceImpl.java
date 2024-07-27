package com.baconbao.notification_service.services.serviceimpl;

import com.baconbao.notification_service.dto.NotificationDTO;
import com.baconbao.notification_service.model.Notification;
import com.baconbao.notification_service.repository.NotificationRepository;
import com.baconbao.notification_service.services.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private ModelMapper modelMapper;

    private Integer getGenerationId() {
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }

    private Notification convertToModel(NotificationDTO notificationDTO){
        return modelMapper.map(notificationDTO, Notification.class);
    }
    private NotificationDTO convertToDTO(Notification notification){
        return modelMapper.map(notification, NotificationDTO.class);
    }

    private Notification save(NotificationDTO notificationDTO){
        Notification notification = Notification.builder()
                .id(getGenerationId())
                .message(notificationDTO.getMessage())
                .createAt(notificationDTO.getCreateAt())
                .isRead(notificationDTO.isRead())
                .url(notificationDTO.getUrl())
                .build();
        return notificationRepository.save(notification);
    }

    @Override
    public NotificationDTO create(NotificationDTO notificationDTO) {
        return convertToDTO(save(notificationDTO));
    }

    @Override
    public NotificationDTO update(NotificationDTO notificationDTO) {
        return convertToDTO(notificationRepository.save(convertToModel(notificationDTO)));
    }

    @Override
    public NotificationDTO findById(Integer id) {
        return convertToDTO(notificationRepository.findById(id).orElseThrow());
    }
}
