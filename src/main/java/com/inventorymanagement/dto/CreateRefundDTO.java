package com.inventorymanagement.dto;

public class CreateRefundDTO {

    private Integer paymentId;

    private String reason;

    // ================= GETTERS & SETTERS =================

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}