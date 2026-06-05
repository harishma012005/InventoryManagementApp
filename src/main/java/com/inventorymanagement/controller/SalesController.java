package com.inventorymanagement.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.inventorymanagement.dto.CreateSalesDTO;
import com.inventorymanagement.dto.SalesResponseDTO;
import com.inventorymanagement.service.SalesService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/sales")
public class SalesController {

    @Autowired
    private SalesService salesService;
    
    // ================= CREATE SALES =================
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createSales(
            @Valid @RequestBody CreateSalesDTO dto) {

        SalesResponseDTO responseDTO = salesService.createSales(dto);

        Map<String, Object> response = new HashMap<>();
        response.put("status", 201);
        response.put("message", "Sales Created Successfully");
        response.put("data", responseDTO);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // ================= GET ALL SALES =================
    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllSales() {

        List<SalesResponseDTO> salesList = salesService.getAllSales();

        Map<String, Object> response = new HashMap<>();
        response.put("status", 200);
        
        response.put("message", "Sales fetched successfully");
        
        response.put("data", salesList);

        return ResponseEntity.ok(response);
    }

    // ================= GET SALES BY ID =================
    @GetMapping("/get/{id}")
    public ResponseEntity<Map<String, Object>> getSalesById(
            @PathVariable Integer id) {

        SalesResponseDTO sales = salesService.getSalesById(id);

        Map<String, Object> response = new HashMap<>();
        response.put("status", 200);
        response.put("message", "Sales fetched successfully");
        response.put("data", sales);

        return ResponseEntity.ok(response);
    }

    // ================= DELETE SALES =================
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteSales(
            @PathVariable Integer id) {

        salesService.deleteSales(id);

        Map<String, Object> response = new HashMap<>();
        response.put("status", 200);
        response.put("message", "Sales deleted successfully");

        return ResponseEntity.ok(response);
    }
}