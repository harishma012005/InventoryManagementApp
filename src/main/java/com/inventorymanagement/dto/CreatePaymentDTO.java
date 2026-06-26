package com.inventorymanagement.dto;

public class CreatePaymentDTO {

    private Integer orderId;

    private String paymentMethod;

    // GETTERS & SETTERS

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}