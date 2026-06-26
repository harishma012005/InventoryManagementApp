package com.inventorymanagement.dto;

public class SupportResponseDTO {

    private Integer ticketId;

    private String message;

    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(
            Integer ticketId) {
        this.ticketId = ticketId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(
            String message) {
        this.message = message;
    }
}