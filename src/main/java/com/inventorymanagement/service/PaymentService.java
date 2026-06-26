package com.inventorymanagement.service;

import java.util.List;

import com.inventorymanagement.dto.CreatePaymentDTO;
import com.inventorymanagement.dto.PaymentDTO;
import com.inventorymanagement.dto.PaymentResponseDTO;

public interface PaymentService {

    // Make Payment
    PaymentResponseDTO makePayment(
            CreatePaymentDTO dto);

    // View Payment By ID
    PaymentDTO getPaymentById(
            Integer paymentId);

    // View Payment By Order ID
    PaymentDTO getPaymentByOrderId(
            Integer orderId);

    // User Payment History
    List<PaymentDTO> getMyPayments();

    // Admin View All Payments
    List<PaymentDTO> getAllPayments();

    // Admin View Payments By Status
    List<PaymentDTO> getPaymentsByStatus(
            String paymentStatus);

    

    // Delete Payment
    void deletePayment(
            Integer paymentId);
}