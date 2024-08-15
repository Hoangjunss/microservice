package com.baconbao.notification_service.services.serviceimpl;

import com.baconbao.notification_service.dto.MessageDTO;
import com.baconbao.notification_service.dto.NotificationDTO;
import com.baconbao.notification_service.exception.CustomException;
import com.baconbao.notification_service.exception.Error;
import com.baconbao.notification_service.model.Notification;
import com.baconbao.notification_service.openFeign.UserClient;
import com.baconbao.notification_service.repository.NotificationRepository;
import com.baconbao.notification_service.services.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserClient userClient;

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
        try{
            log.info("Saving Notification");
            Notification notification = Notification.builder()
                    .id(getGenerationId())
                    .message(notificationDTO.getMessage())
                    .createAt(LocalDateTime.now())
                    .isRead(notificationDTO.isRead())
                    .url(notificationDTO.getUrl())
                    .idUser(notificationDTO.getIdUser())
                    .build();
            return notificationRepository.save(notification);
        } catch (DataIntegrityViolationException e){
            throw new CustomException(Error.NOTIFICATION_UNABLE_TO_SAVE);
        } catch (DataAccessException e){
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }

    @Override
    public NotificationDTO create(NotificationDTO notificationDTO) {
        log.info("Creating notification");
        return convertToDTO(save(notificationDTO));
    }

    @Override
    public NotificationDTO update(NotificationDTO notificationDTO) {
        try {
            log.info("Updating notification id: {}", notificationDTO.getId());
            return convertToDTO(notificationRepository.save(convertToModel(notificationDTO)));
        } catch (DataIntegrityViolationException e){
            throw new CustomException(Error.NOTIFICATION_UNABLE_TO_UPDATE);
        } catch (DataAccessException e){
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }

    @Override
    public NotificationDTO findById(Integer id) {
        log.info("Find notification by id: {}", id);
        return convertToDTO(notificationRepository.findById(id)
                .orElseThrow(()-> new CustomException(Error.NOTIFICATION_NOT_FOUND)));
    }

    @Override
    public NotificationDTO seenNotification(Integer id) {
        try {
            log.info("Update seen notification by id: {}", id);
            Notification notification =notificationRepository.findById(id).orElseThrow();
            notification.setRead(true);
            return convertToDTO(notificationRepository.save(notification));
        } catch (DataIntegrityViolationException e){
            throw new CustomException(Error.NOTIFICATION_UNABLE_TO_UPDATE);
        } catch (DataAccessException e){
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }
    @Override
    public List<NotificationDTO> getNotificationsByIdUser(Integer userId) {
        try {
            log.info("Get notifications by user id: {}", userId);
            Boolean checkUser= userClient.checkId(userId).getData();
            if(!checkUser){
                throw new CustomException(Error.DATABASE_ACCESS_ERROR);
            }
            return convertToDTOList(notificationRepository.getNotificationsByIdUser(userId));
        } catch (DataAccessException e){
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }

    private List<NotificationDTO> convertToDTOList(List<Notification> notifications){
        return notifications.stream()
                .map(notification -> modelMapper.map(notification, NotificationDTO.class))
                .collect(Collectors.toList());
    }
 @Override
    public  void send(MessageDTO messageDTO){
        log.info("send notification"  );
       NotificationDTO notificationDTO=NotificationDTO.builder()
               .message(messageDTO.getMessage())
               .isRead(false)
               .idUser(messageDTO.getId())
               .build();
       create(notificationDTO);
    }

}
