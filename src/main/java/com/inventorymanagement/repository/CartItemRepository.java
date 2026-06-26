package com.inventorymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventorymanagement.entity.CartItem;

public interface CartItemRepository
        extends JpaRepository<CartItem, Integer> {

}