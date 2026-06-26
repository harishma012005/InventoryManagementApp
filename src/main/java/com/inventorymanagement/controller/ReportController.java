package com.inventorymanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.inventorymanagement.dto.InventoryReportDTO;
import com.inventorymanagement.dto.PaymentReportDTO;
import com.inventorymanagement.dto.PurchaseReportDTO;
import com.inventorymanagement.dto.RefundReportDTO;
import com.inventorymanagement.dto.SalesReportDTO;
import com.inventorymanagement.service.ReportService;

@RestController
@RequestMapping("/reports")
public class ReportController {


@Autowired
private ReportService reportService;

// ================= INVENTORY REPORT =================

@GetMapping("/inventory")
public ResponseEntity<List<InventoryReportDTO>>
getInventoryReport() {

    return ResponseEntity.ok(
            reportService.getInventoryReport());
}

// ================= SALES REPORT =================

@GetMapping("/sales")
public ResponseEntity<List<SalesReportDTO>>
getSalesReport() {

    return ResponseEntity.ok(
            reportService.getSalesReport());
}

// ================= PAYMENT REPORT =================

@GetMapping("/payments")
public ResponseEntity<List<PaymentReportDTO>>
getPaymentReport() {

    return ResponseEntity.ok(
            reportService.getPaymentReport());
}

// ================= REFUND REPORT =================

@GetMapping("/refunds")
public ResponseEntity<List<RefundReportDTO>>
getRefundReport() {

    return ResponseEntity.ok(
            reportService.getRefundReport());
}

// ================= PURCHASE REPORT =================

@GetMapping("/purchases")
public ResponseEntity<List<PurchaseReportDTO>>
getPurchaseReport() {

    return ResponseEntity.ok(
            reportService.getPurchaseReport());
}


}
