package com.inventorymanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inventorymanagement.entity.Payment;

@Repository
public interface PaymentRepository
        extends JpaRepository<Payment, Integer> {

    Optional<Payment> findByOrder_OrderId(
            Integer orderId);

    List<Payment> findByPaymentStatus(
            String paymentStatus);

    List<Payment> findByPaymentMethod(
            String paymentMethod);

    boolean existsByOrder_OrderId(
            Integer orderId);

    void deleteByOrder_OrderId(
            Integer orderId);
}