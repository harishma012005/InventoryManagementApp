package com.inventorymanagement.dto;

import java.time.LocalDateTime;

public class SupportTicketDTO {

    private Integer ticketId;

    private Integer userId;

    private String customerName;

    private String subject;

    private String message;

    private String reply;

    private String status;

    private String priority;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(
            Integer ticketId) {
        this.ticketId = ticketId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(
            Integer userId) {
        this.userId = userId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(
            String customerName) {
        this.customerName = customerName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(
            String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(
            String message) {
        this.message = message;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(
            String reply) {
        this.reply = reply;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(
            String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(
            String priority) {
        this.priority = priority;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(
            LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(
            LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}