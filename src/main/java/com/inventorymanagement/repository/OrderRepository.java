package com.inventorymanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inventorymanagement.entity.Order;

@Repository
public interface OrderRepository
        extends JpaRepository<Order, Integer> {

    List<Order> findByUser_UserId(
            Integer userId);

    List<Order> findByStatus(
            String status);

    List<Order> findByOrderType(
            String orderType);
}
