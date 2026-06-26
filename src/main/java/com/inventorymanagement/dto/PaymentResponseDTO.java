package com.inventorymanagement.dto;

public class PaymentResponseDTO {

    private Integer paymentId;

    private String message;

    // GETTERS & SETTERS

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}