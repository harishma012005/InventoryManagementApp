package com.inventorymanagement.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.inventorymanagement.entity.Product;
import com.inventorymanagement.dto.StockTransactionDTO;
import com.inventorymanagement.service.StockService;

@RestController
@RequestMapping("/stocks")
public class StockController {

    @Autowired
    private StockService stockService;

    // ---------------- STOCK IN ----------------
    @PostMapping("/stockin/{id}/{quantity}")
    public ResponseEntity<Map<String, Object>> stockIn(
            @PathVariable Integer id,
            @PathVariable Integer quantity) {

        StockTransactionDTO result = stockService.stockIn(id, quantity);

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.CREATED.value());
        response.put("message", "Stock Added Successfully");
        response.put("data", result);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // ---------------- STOCK OUT ----------------
    @PostMapping("/stockout/{id}/{quantity}")
    public ResponseEntity<Map<String, Object>> stockOut(
            @PathVariable Integer id,
            @PathVariable Integer quantity) {

        StockTransactionDTO result = stockService.stockOut(id, quantity);

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("message", "Stock Reduced Successfully");
        response.put("data", result);

        return ResponseEntity.ok(response);
    }

    // ---------------- TOTAL STOCK ----------------
    @GetMapping("/total-stock")
    public ResponseEntity<Map<String, Object>> getTotalStock() {

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("message", "Total Stock Retrieved Successfully");
        response.put("totalStock", stockService.getTotalStock());

        return ResponseEntity.ok(response);
    }

    // ---------------- STOCK STATUS ----------------
    @GetMapping("/status/{id}")
    public ResponseEntity<Map<String, Object>> getStockStatus(
            @PathVariable Integer id) {

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("message", "Stock Status Retrieved Successfully");
        response.put("data", stockService.getStockStatus(id));

        return ResponseEntity.ok(response);
    }

    // ---------------- LOW STOCK ----------------
    @GetMapping("/low-stock")
    public ResponseEntity<Map<String, Object>> getLowStockProducts() {

        List<Product> products = stockService.getLowStockProducts();

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("message", "Low Stock Products Retrieved Successfully");
        response.put("data", products);

        return ResponseEntity.ok(response);
    }

    // ---------------- OUT OF STOCK ----------------
    @GetMapping("/out-of-stock")
    public ResponseEntity<Map<String, Object>> getOutOfStockProducts() {

        List<Product> products = stockService.getOutOfStockProducts();

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("message", "Out of Stock Products Retrieved Successfully");
        response.put("data", products);

        return ResponseEntity.ok(response);
    }

    // ---------------- STOCK HISTORY ----------------
    @GetMapping("/history")
    public ResponseEntity<Map<String, Object>> getStockHistory() {

        List<StockTransactionDTO> history = stockService.getStockHistory();

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("message", "Stock History Retrieved Successfully");
        response.put("data", history);

        return ResponseEntity.ok(response);
    }

    // ---------------- HISTORY BY PRODUCT ----------------
    @GetMapping("/history/product/{id}")
    public ResponseEntity<Map<String, Object>> getStockHistoryByProduct(
            @PathVariable Integer id) {

        List<StockTransactionDTO> history =
                stockService.getStockHistoryByProduct(id);

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("message", "Product Stock History Retrieved Successfully");
        response.put("data", history);

        return ResponseEntity.ok(response);
    }

    // ---------------- INVENTORY VALUE ----------------
    @GetMapping("/inventory-value")
    public ResponseEntity<Map<String, Object>> getInventoryValue() {

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("message", "Inventory Value Retrieved Successfully");
        response.put("inventoryValue", stockService.getInventoryValue());

        return ResponseEntity.ok(response);
    }

    // ---------------- DASHBOARD ----------------
    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboardSummary() {

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("message", "Dashboard Summary Retrieved Successfully");
        response.put("data", stockService.getDashboardSummary());

        return ResponseEntity.ok(response);
    }
}
