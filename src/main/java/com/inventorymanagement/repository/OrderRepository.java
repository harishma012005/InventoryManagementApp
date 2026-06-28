package com.inventorymanagement.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inventorymanagement.entity.Order;

@Repository
public interface OrderRepository
        extends JpaRepository<Order, Integer> {

    // Orders of a particular user
    List<Order> findByUser_UserId(
            Integer userId);

    // Filter by status
    List<Order> findByStatus(
            String status);

    // Filter by order type
    List<Order> findByOrderType(
            String orderType);

    // ================= NEW METHOD =================

    // Filter orders between two dates
    List<Order> findByOrderDateBetween(
            LocalDateTime from,
            LocalDateTime to);
}