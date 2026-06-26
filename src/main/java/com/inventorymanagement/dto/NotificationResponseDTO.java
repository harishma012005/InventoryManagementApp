package com.inventorymanagement.dto;

public class NotificationResponseDTO {

    private Integer notificationId;

    private String message;

    // ================= GETTERS & SETTERS =================

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(
            Integer notificationId) {
        this.notificationId = notificationId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(
            String message) {
        this.message = message;
    }
}