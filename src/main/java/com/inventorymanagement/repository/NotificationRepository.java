package com.inventorymanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inventorymanagement.entity.Notification;

@Repository
public interface NotificationRepository
        extends JpaRepository<Notification, Integer> {

    // User notifications

    List<Notification> findByUser_UserId(
            Integer userId);

    // Unread notifications

    List<Notification> findByUser_UserIdAndIsRead(
            Integer userId,
            Boolean isRead);

    // Notification type

    List<Notification> findByType(
            String type);

    // Read status

    List<Notification> findByIsRead(
            Boolean isRead);

    // User + Type

    List<Notification> findByUser_UserIdAndType(
            Integer userId,
            String type);

    // Count unread notifications

    Long countByUser_UserIdAndIsRead(
            Integer userId,
            Boolean isRead);

    // Delete all notifications of user

    void deleteByUser_UserId(
            Integer userId);
}