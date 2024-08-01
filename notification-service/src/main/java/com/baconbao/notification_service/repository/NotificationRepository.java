package com.baconbao.notification_service.repository;

import com.baconbao.notification_service.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;
import java.util.List;


public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    Optional<Notification> findById(Integer integer);
    @Query("SELECT n FROM Notification n WHERE n.idUser = :userId")
    List<Notification> getNotificationsByIdUser(Integer userId);
}
