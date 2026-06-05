package com.inventorymanagement.dto;

import java.math.BigDecimal;
import java.util.List;

public class PurchaseDTO {

    private Integer purchaseId;

    private String supplierName;

    private BigDecimal totalAmount;

    private List<PurchaseItemDTO> items;

    // getters and setters

    public Integer getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<PurchaseItemDTO> getItems() {
        return items;
    }

    public void setItems(List<PurchaseItemDTO> items) {
        this.items = items;
    }
}