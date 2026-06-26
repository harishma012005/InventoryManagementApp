package com.inventorymanagement.dto;

import java.util.List;

public class CartDTO {

    private Integer cartId;

    private List<CartItemDTO> items;

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public List<CartItemDTO> getItems() {
        return items;
    }

    public void setItems(List<CartItemDTO> items) {
        this.items = items;
    }
}