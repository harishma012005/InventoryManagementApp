package com.inventorymanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.inventorymanagement.dto.AdminRefundActionDTO;
import com.inventorymanagement.dto.CreateRefundDTO;
import com.inventorymanagement.dto.RefundDTO;
import com.inventorymanagement.dto.RefundResponseDTO;
import com.inventorymanagement.service.RefundService;

@RestController
@RequestMapping("/refunds")
public class RefundController {


@Autowired
private RefundService refundService;

// ================= REQUEST REFUND =================

@PostMapping("/request")
public ResponseEntity<RefundResponseDTO> requestRefund(
        @RequestBody CreateRefundDTO dto) {

    return ResponseEntity.ok(
            refundService.requestRefund(dto));
}

// ================= MY REFUNDS =================

@GetMapping("/my-refunds")
public ResponseEntity<List<RefundDTO>> getMyRefunds() {

    return ResponseEntity.ok(
            refundService.getMyRefunds());
}

// ================= GET REFUND BY ID =================

@GetMapping("/{refundId}")
public ResponseEntity<RefundDTO> getRefundById(
        @PathVariable Integer refundId) {

    return ResponseEntity.ok(
            refundService.getRefundById(refundId));
}

// ================= ADMIN - ALL REFUNDS =================

@GetMapping("/all")
public ResponseEntity<List<RefundDTO>> getAllRefunds() {

    return ResponseEntity.ok(
            refundService.getAllRefunds());
}

// ================= REFUNDS BY STATUS =================

@GetMapping("/status/{status}")
public ResponseEntity<List<RefundDTO>> getRefundsByStatus(
        @PathVariable String status) {

    return ResponseEntity.ok(
            refundService.getRefundsByStatus(status));
}

// ================= APPROVE REFUND =================

@PutMapping("/approve/{refundId}")
public ResponseEntity<RefundResponseDTO> approveRefund(
        @PathVariable Integer refundId,
        @RequestBody AdminRefundActionDTO dto) {

    return ResponseEntity.ok(
            refundService.approveRefund(
                    refundId,
                    dto));
}

// ================= REJECT REFUND =================

@PutMapping("/reject/{refundId}")
public ResponseEntity<RefundResponseDTO> rejectRefund(
        @PathVariable Integer refundId,
        @RequestBody AdminRefundActionDTO dto) {

    return ResponseEntity.ok(
            refundService.rejectRefund(
                    refundId,
                    dto));
}

// ================= DELETE REFUND =================

@DeleteMapping("/{refundId}")
public ResponseEntity<String> deleteRefund(
        @PathVariable Integer refundId) {

    refundService.deleteRefund(refundId);

    return ResponseEntity.ok(
            "Refund Deleted Successfully");
}


}
