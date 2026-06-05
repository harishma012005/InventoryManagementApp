package com.inventorymanagement.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.inventorymanagement.dto.CreatePurchaseDTO;
import com.inventorymanagement.dto.PurchaseDTO;
import com.inventorymanagement.dto.PurchaseResponseDTO;
import com.inventorymanagement.service.PurchaseService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    // ================= CREATE PURCHASE =================
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createPurchase(
            @Valid @RequestBody CreatePurchaseDTO dto) {

        PurchaseResponseDTO responseDTO = purchaseService.createPurchase(dto);

        Map<String, Object> response = new HashMap<>();
        response.put("status", 201);
        response.put("message", responseDTO.getMessage());
        response.put("purchaseId", responseDTO.getPurchaseId());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // ================= GET ALL PURCHASES =================
    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllPurchases() {

        List<PurchaseDTO> purchases = purchaseService.getAllPurchases();

        Map<String, Object> response = new HashMap<>();
        response.put("status", 200);
        response.put("message", "Purchases fetched successfully");
        response.put("data", purchases);

        return ResponseEntity.ok(response);
    }

    // ================= GET PURCHASE BY ID =================
    @GetMapping("/get/{id}")
    public ResponseEntity<Map<String, Object>> getPurchaseById(
            @PathVariable Integer id) {

        PurchaseDTO purchase = purchaseService.getPurchaseById(id);

        Map<String, Object> response = new HashMap<>();
        response.put("status", 200);
        response.put("message", "Purchase fetched successfully");
        response.put("data", purchase);

        return ResponseEntity.ok(response);
    }

    // ================= DELETE PURCHASE =================
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deletePurchase(
            @PathVariable Integer id) {

        purchaseService.deletePurchase(id);

        Map<String, Object> response = new HashMap<>();
        response.put("status", 200);
        response.put("message", "Purchase deleted successfully");

        return ResponseEntity.ok(response);
    }
}