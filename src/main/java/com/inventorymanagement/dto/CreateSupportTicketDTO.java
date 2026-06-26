package com.inventorymanagement.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateSupportTicketDTO {

    @NotBlank
    private String subject;

    @NotBlank
    private String message;

    @NotBlank
    private String priority;

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

    public String getPriority() {
        return priority;
    }

    public void setPriority(
            String priority) {
        this.priority = priority;
    }
}