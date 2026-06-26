package com.inventorymanagement.dto;

public class CreateNotificationDTO {

    private Integer userId;

    private String title;

    private String message;

    private String type;

    // ================= GETTERS & SETTERS =================

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(
            Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(
            String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(
            String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(
            String type) {
        this.type = type;
    }
}