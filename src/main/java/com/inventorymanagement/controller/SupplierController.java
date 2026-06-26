package com.inventorymanagement.controller;

import java.util.HashMap;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.inventorymanagement.dto.SupplierDTO;
import com.inventorymanagement.service.SupplierService;

@RestController
@RequestMapping("/supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    // SAVE
    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> saveSupplier(@RequestBody SupplierDTO dto) {

        SupplierDTO saved = supplierService.saveSupplier(dto);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Supplier saved successfully");
        response.put("data", saved);

        return ResponseEntity.ok(response);
    }

    // GET ALL
    @GetMapping("/getAll")
    public ResponseEntity<Map<String, Object>> getAllSuppliers() {

        Map<String, Object> response = new HashMap<>();
        response.put("data", supplierService.getAllSuppliers());

        return ResponseEntity.ok(response);
    }

    // GET BY ID
    @GetMapping("/getById/{id}")
    public ResponseEntity<Map<String, Object>> getSupplierById(@PathVariable Integer id) {

        Map<String, Object> response = new HashMap<>();
        response.put("data", supplierService.getSupplierById(id));

        return ResponseEntity.ok(response);
    }

    // UPDATE
    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> updateSupplier(
            @PathVariable Integer id,
            @RequestBody SupplierDTO dto) {

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Supplier updated successfully");
        response.put("data", supplierService.updateSupplier(id, dto));

        return ResponseEntity.ok(response);
    }

    // DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteSupplier(@PathVariable Integer id) {

        supplierService.deleteSupplier(id);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Supplier deleted successfully");

        return ResponseEntity.ok(response);
    }

    // DELETE ALL
    @DeleteMapping("/deleteAll")
    public ResponseEntity<Map<String, Object>> deleteAllSuppliers() {

        supplierService.deleteAllSuppliers();

        Map<String, Object> response = new HashMap<>();
        response.put("message", "All suppliers deleted successfully");

        return ResponseEntity.ok(response);
    }

    // TOTAL STOCK
   
}
