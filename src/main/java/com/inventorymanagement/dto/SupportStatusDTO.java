package com.inventorymanagement.dto;

import jakarta.validation.constraints.NotBlank;

public class SupportStatusDTO {

    @NotBlank
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(
            String status) {
        this.status = status;
    }
}