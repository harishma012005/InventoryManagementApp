package com.inventorymanagement.dto;

import jakarta.validation.constraints.NotBlank;

public class UpdateOrderStatusDTO {

    @NotBlank(message = "Order status is required")
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}