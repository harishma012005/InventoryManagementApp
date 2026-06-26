package com.inventorymanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.inventorymanagement.dto.CreateNotificationDTO;
import com.inventorymanagement.dto.NotificationDTO;
import com.inventorymanagement.dto.NotificationResponseDTO;
import com.inventorymanagement.service.NotificationService;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    // ================= CREATE NOTIFICATION =================

    @PostMapping
    public ResponseEntity<NotificationResponseDTO>
    createNotification(
            @RequestBody
            CreateNotificationDTO dto) {

        return ResponseEntity.ok(
                notificationService
                        .createNotification(dto));
    }

    // ================= MY NOTIFICATIONS =================

    @GetMapping("/my-notifications")
    public ResponseEntity<List<NotificationDTO>>
    getMyNotifications() {

        return ResponseEntity.ok(
                notificationService
                        .getMyNotifications());
    }

    // ================= UNREAD NOTIFICATIONS =================

    @GetMapping("/unread")
    public ResponseEntity<List<NotificationDTO>>
    getUnreadNotifications() {

        return ResponseEntity.ok(
                notificationService
                        .getUnreadNotifications());
    }

    // ================= GET NOTIFICATION BY ID =================

    @GetMapping("/{notificationId}")
    public ResponseEntity<NotificationDTO>
    getNotificationById(
            @PathVariable
            Integer notificationId) {

        return ResponseEntity.ok(
                notificationService
                        .getNotificationById(
                                notificationId));
    }

    // ================= MARK AS READ =================

    @PutMapping("/read/{notificationId}")
    public ResponseEntity<NotificationResponseDTO>
    markAsRead(
            @PathVariable
            Integer notificationId) {

        return ResponseEntity.ok(
                notificationService
                        .markAsRead(
                                notificationId));
    }

    // ================= MARK ALL AS READ =================

    @PutMapping("/read-all")
    public ResponseEntity<NotificationResponseDTO>
    markAllAsRead() {

        return ResponseEntity.ok(
                notificationService
                        .markAllAsRead());
    }

    // ================= GET ALL NOTIFICATIONS =================

    @GetMapping("/all")
    public ResponseEntity<List<NotificationDTO>>
    getAllNotifications() {

        return ResponseEntity.ok(
                notificationService
                        .getAllNotifications());
    }

    // ================= GET BY TYPE =================

    @GetMapping("/type/{type}")
    public ResponseEntity<List<NotificationDTO>>
    getNotificationsByType(
            @PathVariable
            String type) {

        return ResponseEntity.ok(
                notificationService
                        .getNotificationsByType(
                                type));
    }

    // ================= UNREAD COUNT =================

    @GetMapping("/unread-count")
    public ResponseEntity<Long>
    getUnreadCount() {

        return ResponseEntity.ok(
                notificationService
                        .getUnreadCount());
    }

    // ================= LOW STOCK ALERT =================

    @PostMapping("/low-stock/{productId}")
    public ResponseEntity<NotificationResponseDTO>
    createLowStockNotification(
            @PathVariable
            Integer productId) {

        return ResponseEntity.ok(
                notificationService
                        .createLowStockNotification(
                                productId));
    }

    // ================= DELETE NOTIFICATION =================

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<String>
    deleteNotification(
            @PathVariable
            Integer notificationId) {

        notificationService
                .deleteNotification(
                        notificationId);

        return ResponseEntity.ok(
                "Notification Deleted Successfully");
    }

    // ================= DELETE MY NOTIFICATIONS =================

    @DeleteMapping("/my-notifications")
    public ResponseEntity<NotificationResponseDTO>
    deleteMyNotifications() {

        return ResponseEntity.ok(
                notificationService
                        .deleteMyNotifications());
    }
}