package com.inventorymanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inventorymanagement.entity.Refund;

@Repository
public interface RefundRepository
        extends JpaRepository<Refund, Integer> {

    Optional<Refund> findByPayment_PaymentId(
            Integer paymentId);

    boolean existsByPayment_PaymentId(
            Integer paymentId);

    List<Refund> findByRefundStatus(
            String refundStatus);

    List<Refund> findByPayment_Order_User_UserId(
            Integer userId);
}