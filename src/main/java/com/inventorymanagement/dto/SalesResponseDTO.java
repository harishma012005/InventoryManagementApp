package com.inventorymanagement.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class SalesResponseDTO {

    private Integer salesId;

    private String userName;

    private BigDecimal totalAmount;

    private LocalDateTime salesDate;

    private List<SalesItemDTO> items;

    // ================= GETTERS & SETTERS =================

    public Integer getSalesId() {
        return salesId;
    }

    public void setSalesId(Integer salesId) {
        this.salesId = salesId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getSalesDate() {
        return salesDate;
    }

    public void setSalesDate(LocalDateTime salesDate) {
        this.salesDate = salesDate;
    }

    public List<SalesItemDTO> getItems() {
        return items;
    }

    public void setItems(List<SalesItemDTO> items) {
        this.items = items;
    }
}