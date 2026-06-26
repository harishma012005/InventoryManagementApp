package com.inventorymanagement.service;

import java.util.List;

import com.inventorymanagement.dto.CreateNotificationDTO;
import com.inventorymanagement.dto.NotificationDTO;
import com.inventorymanagement.dto.NotificationResponseDTO;

public interface NotificationService {

    // ================= CREATE NOTIFICATION =================

    NotificationResponseDTO createNotification(
            CreateNotificationDTO dto);
    NotificationResponseDTO createLowStockNotification(
            Integer productId);

    // ================= MY NOTIFICATIONS =================

    List<NotificationDTO> getMyNotifications();

    // ================= UNREAD NOTIFICATIONS =================

    List<NotificationDTO> getUnreadNotifications();

    // ================= GET NOTIFICATION BY ID =================

    NotificationDTO getNotificationById(
            Integer notificationId);

    // ================= MARK AS READ =================

    NotificationResponseDTO markAsRead(
            Integer notificationId);

    // ================= MARK ALL AS READ =================

    NotificationResponseDTO markAllAsRead();

    // ================= GET ALL NOTIFICATIONS =================

    List<NotificationDTO> getAllNotifications();

    // ================= GET NOTIFICATIONS BY TYPE =================

    List<NotificationDTO> getNotificationsByType(
            String type);

    // ================= GET UNREAD COUNT =================

    Long getUnreadCount();

    // ================= DELETE NOTIFICATION =================

    void deleteNotification(
            Integer notificationId);

    // ================= DELETE ALL MY NOTIFICATIONS =================

    NotificationResponseDTO deleteMyNotifications();
}