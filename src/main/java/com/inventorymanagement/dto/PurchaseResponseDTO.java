package com.inventorymanagement.dto;

public class PurchaseResponseDTO {

    private String message;

    private Integer purchaseId;

    // getters and setters

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }
}
