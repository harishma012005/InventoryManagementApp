package com.inventorymanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.inventorymanagement.dto.CreatePaymentDTO;
import com.inventorymanagement.dto.PaymentDTO;
import com.inventorymanagement.dto.PaymentResponseDTO;
import com.inventorymanagement.service.PaymentService;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    // ================= MAKE PAYMENT =================

    @PostMapping("/pay")
    public ResponseEntity<PaymentResponseDTO> makePayment(
            @RequestBody CreatePaymentDTO dto) {

        return ResponseEntity.ok(
                paymentService.makePayment(dto));
    }

    // ================= GET PAYMENT BY ID =================

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentDTO> getPaymentById(
            @PathVariable Integer paymentId) {

        return ResponseEntity.ok(
                paymentService.getPaymentById(paymentId));
    }

    // ================= GET PAYMENT BY ORDER =================

    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentDTO> getPaymentByOrderId(
            @PathVariable Integer orderId) {

        return ResponseEntity.ok(
                paymentService.getPaymentByOrderId(orderId));
    }

    // ================= MY PAYMENTS =================

    @GetMapping("/my-payments")
    public ResponseEntity<List<PaymentDTO>> getMyPayments() {

        return ResponseEntity.ok(
                paymentService.getMyPayments());
    }

    // ================= ADMIN - ALL PAYMENTS =================

    @GetMapping("/all")
    public ResponseEntity<List<PaymentDTO>> getAllPayments() {

        return ResponseEntity.ok(
                paymentService.getAllPayments());
    }

    // ================= PAYMENTS BY STATUS =================

    @GetMapping("/status/{status}")
    public ResponseEntity<List<PaymentDTO>> getPaymentsByStatus(
            @PathVariable String status) {

        return ResponseEntity.ok(
                paymentService.getPaymentsByStatus(status));
    }

    // ================= REFUND PAYMENT =================

  

    // ================= DELETE PAYMENT =================

    @DeleteMapping("/{paymentId}")
    public ResponseEntity<String> deletePayment(
            @PathVariable Integer paymentId) {

        paymentService.deletePayment(paymentId);

        return ResponseEntity.ok(
                "Payment Deleted Successfully");
    }
}