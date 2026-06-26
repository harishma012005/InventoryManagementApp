package com.inventorymanagement.dto;

public class RefundResponseDTO {

    private Integer refundId;

    private String message;

    // ================= GETTERS & SETTERS =================

    public Integer getRefundId() {
        return refundId;
    }

    public void setRefundId(Integer refundId) {
        this.refundId = refundId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}