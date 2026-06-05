package com.inventorymanagement.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.inventorymanagement.dto.LowStockDTO;
import com.inventorymanagement.dto.StockDTO;
import com.inventorymanagement.service.StockService;

@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    private StockService stockService;

    // ================= GET ALL STOCK =================
    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllStock() {

        List<StockDTO> stockList = stockService.getAllStock();

        Map<String, Object> response = new HashMap<>();

        response.put("status", 200);
        response.put("message", "Stock fetched successfully");
        response.put("data", stockList);

        return ResponseEntity.ok(response);
    }

    // ================= GET STOCK BY PRODUCT ID =================
    @GetMapping("/get/{productId}")
    public ResponseEntity<Map<String, Object>> getStockByProductId(
            @PathVariable Integer productId) {

        StockDTO stock = stockService.getStockByProductId(productId);

        Map<String, Object> response = new HashMap<>();

        response.put("status", 200);
        response.put("message", "Product stock fetched successfully");
        response.put("data", stock);

        return ResponseEntity.ok(response);
    }

    // ================= LOW STOCK ALERT =================
    @GetMapping("/low-stock/{threshold}")
    public ResponseEntity<Map<String, Object>> getLowStock(
            @PathVariable Integer threshold) {

        List<LowStockDTO> lowStockList =
                stockService.getLowStock(threshold);

        Map<String, Object> response = new HashMap<>();

        response.put("status", 200);
        response.put("message", "Low stock products fetched successfully");
        response.put("data", lowStockList);

        return ResponseEntity.ok(response);
    }
}
