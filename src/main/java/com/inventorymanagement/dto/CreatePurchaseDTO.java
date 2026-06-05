package com.inventorymanagement.dto;

import java.util.List;

public class CreatePurchaseDTO {

    private Integer supplierId;

    private List<CreatePurchaseItemDTO> items;

    // getters and setters

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public List<CreatePurchaseItemDTO> getItems() {
        return items;
    }

    public void setItems(List<CreatePurchaseItemDTO> items) {
        this.items = items;
    }
}