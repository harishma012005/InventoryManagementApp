package com.inventorymanagement.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.inventorymanagement.dto.CreateNotificationDTO;
import com.inventorymanagement.dto.NotificationDTO;
import com.inventorymanagement.dto.NotificationResponseDTO;
import com.inventorymanagement.entity.Notification;
import com.inventorymanagement.entity.Product;
import com.inventorymanagement.entity.User;
import com.inventorymanagement.exception.ResourceNotFoundException;
import com.inventorymanagement.repository.NotificationRepository;
import com.inventorymanagement.repository.ProductRepository;
import com.inventorymanagement.repository.UserRepository;
import com.inventorymanagement.service.NotificationService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class NotificationServiceImpl
        implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    // ================= LOGGED IN USER =================

    private User getLoggedInUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email =
                authentication.getName();

        return userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User Not Found"));
    }

    // ================= DTO CONVERSION =================

    private NotificationDTO convertToDTO(
            Notification notification) {

        NotificationDTO dto =
                new NotificationDTO();

        dto.setNotificationId(
                notification.getNotificationId());

        dto.setUserId(
                notification.getUser()
                        .getUserId());

        dto.setUserName(
                notification.getUser()
                        .getFullName());

        dto.setTitle(
                notification.getTitle());

        dto.setMessage(
                notification.getMessage());

        dto.setType(
                notification.getType());

        dto.setIsRead(
                notification.getIsRead());

        dto.setCreatedAt(
                notification.getCreatedAt());

        return dto;
    }

    // ================= CREATE NOTIFICATION =================

    @Override
    public NotificationResponseDTO createNotification(
            CreateNotificationDTO dto) {

        User user =
                userRepository.findById(
                        dto.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User Not Found"));

        Notification notification =
                new Notification();

        notification.setUser(user);
        notification.setTitle(dto.getTitle());
        notification.setMessage(dto.getMessage());
        notification.setType(dto.getType());
        notification.setIsRead(false);
        notification.setCreatedAt(
                LocalDateTime.now());

        Notification savedNotification =
                notificationRepository.save(
                        notification);

        NotificationResponseDTO response =
                new NotificationResponseDTO();

        response.setNotificationId(
                savedNotification.getNotificationId());

        response.setMessage(
                "Notification Created Successfully");

        return response;
    }

    // ================= MY NOTIFICATIONS =================

    @Override
    public List<NotificationDTO>
    getMyNotifications() {

        User user =
                getLoggedInUser();

        return notificationRepository
                .findByUser_UserId(
                        user.getUserId())
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    // ================= UNREAD NOTIFICATIONS =================

    @Override
    public List<NotificationDTO>
    getUnreadNotifications() {

        User user =
                getLoggedInUser();

        return notificationRepository
                .findByUser_UserIdAndIsRead(
                        user.getUserId(),
                        false)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    // ================= GET NOTIFICATION BY ID =================

    @Override
    public NotificationDTO getNotificationById(
            Integer notificationId) {

        User user =
                getLoggedInUser();

        Notification notification =
                notificationRepository.findById(
                        notificationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Notification Not Found"));

        if (!notification.getUser()
                .getUserId()
                .equals(user.getUserId())
                &&
                !"ADMIN".equalsIgnoreCase(
                        user.getRole())) {

            throw new RuntimeException(
                    "Access Denied");
        }

        return convertToDTO(notification);
    }

    // ================= MARK AS READ =================

    @Override
    public NotificationResponseDTO markAsRead(
            Integer notificationId) {

        Notification notification =
                notificationRepository.findById(
                        notificationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Notification Not Found"));

        notification.setIsRead(true);

        notificationRepository.save(
                notification);

        NotificationResponseDTO response =
                new NotificationResponseDTO();

        response.setNotificationId(
                notificationId);

        response.setMessage(
                "Notification Marked As Read");

        return response;
    }

    // ================= MARK ALL AS READ =================

    @Override
    public NotificationResponseDTO
    markAllAsRead() {

        User user =
                getLoggedInUser();

        List<Notification> notifications =
                notificationRepository
                        .findByUser_UserId(
                                user.getUserId());

        notifications.forEach(
                notification ->
                        notification.setIsRead(
                                true));

        notificationRepository.saveAll(
                notifications);

        NotificationResponseDTO response =
                new NotificationResponseDTO();

        response.setMessage(
                "All Notifications Marked As Read");

        return response;
    }

    // ================= ALL NOTIFICATIONS =================

    @Override
    public List<NotificationDTO>
    getAllNotifications() {

        return notificationRepository
                .findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    // ================= NOTIFICATIONS BY TYPE =================

    @Override
    public List<NotificationDTO>
    getNotificationsByType(
            String type) {

        return notificationRepository
                .findByType(type)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    // ================= UNREAD COUNT =================

    @Override
    public Long getUnreadCount() {

        User user =
                getLoggedInUser();

        return notificationRepository
                .countByUser_UserIdAndIsRead(
                        user.getUserId(),
                        false);
    }

    // ================= DELETE NOTIFICATION =================

    @Override
    public void deleteNotification(
            Integer notificationId) {

        Notification notification =
                notificationRepository.findById(
                        notificationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Notification Not Found"));

        notificationRepository.delete(
                notification);
    }

    // ================= DELETE MY NOTIFICATIONS =================

    @Override
    public NotificationResponseDTO
    deleteMyNotifications() {

        User user =
                getLoggedInUser();

        notificationRepository
                .deleteByUser_UserId(
                        user.getUserId());

        NotificationResponseDTO response =
                new NotificationResponseDTO();

        response.setMessage(
                "All Notifications Deleted");

        return response;
    }

    // ================= LOW STOCK ALERT =================

    public NotificationResponseDTO
    createLowStockNotification(
            Integer productId) {

        Product product =
                productRepository.findById(
                        productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product Not Found"));

        if(product.getQuantity() > 10) {

            throw new RuntimeException(
                    "Stock Is Not Low");
        }

        List<User> admins =
                userRepository.findByRole(
                        "ADMIN");

        for(User admin : admins) {

            Notification notification =
                    new Notification();

            notification.setUser(admin);

            notification.setTitle(
                    "LOW STOCK ALERT");

            notification.setMessage(
                    product.getProductName()
                    + " stock is low. Current quantity: "
                    + product.getQuantity());

            notification.setType(
                    "STOCK");

            notification.setIsRead(
                    false);

            notification.setCreatedAt(
                    LocalDateTime.now());

            notificationRepository.save(
                    notification);
        }

        NotificationResponseDTO response =
                new NotificationResponseDTO();

        response.setMessage(
                "Low Stock Notifications Sent");

        return response;
    }
}