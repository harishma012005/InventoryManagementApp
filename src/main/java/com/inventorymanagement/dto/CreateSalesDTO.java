package com.inventorymanagement.dto;

import java.util.List;

public class CreateSalesDTO {

    private Integer userId;

    private List<CreateSalesItemDTO> items;

    // getters & setters

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<CreateSalesItemDTO> getItems() {
        return items;
    }

    public void setItems(List<CreateSalesItemDTO> items) {
        this.items = items;
    }
}