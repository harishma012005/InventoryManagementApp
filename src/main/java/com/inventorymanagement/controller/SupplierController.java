package com.inventorymanagement.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.inventorymanagement.entity.Supplier;
import com.inventorymanagement.service.SupplierService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/suppliers")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    // Add Supplier
    @PostMapping
    public Map<String, Object> saveSupplier(
    		@Valid
    		
            @RequestBody Supplier supplier) {

        Supplier savedSupplier =
                supplierService.saveSupplier(
                supplier);

        Map<String, Object> response =
                new HashMap<>();

        response.put(
                "status",
                201);

        response.put(
                "message",
                "Supplier Saved Successfully");

        response.put(
                "data",
                savedSupplier);

        return response;
    }

    // Get All Suppliers
    @GetMapping
    public List<Supplier> getAllSuppliers() {

        return supplierService
                .getAllSuppliers();
    }

    // Get Supplier By ID
    @GetMapping("/{id}")
    public Supplier getSupplierById(
            @PathVariable Integer id) {

        return supplierService
                .getSupplierById(id);
    }

    // Update Supplier
    @PutMapping("/{id}")
    public Map<String, Object> updateSupplier(
            @PathVariable Integer id,
            @Valid
            @RequestBody Supplier supplier) {

        Supplier updatedSupplier =
                supplierService.updateSupplier(
                id, supplier);

        Map<String, Object> response =
                new HashMap<>();

        response.put(
                "status",
                200);

        response.put(
                "message",
                "Supplier Updated Successfully");

        response.put(
                "data",
                updatedSupplier);

        return response;
    }

    // Delete Supplier
    @DeleteMapping("/{id}")
    public Map<String, Object> deleteSupplier(
            @PathVariable Integer id) {

        supplierService.deleteSupplier(id);

        Map<String, Object> response =
                new HashMap<>();

        response.put(
                "status",
                200);

        response.put(
                "message",
                "Supplier Deleted Successfully");

        return response;
    }

    // Delete All Suppliers
    @DeleteMapping
    public Map<String, Object>
    deleteAllSuppliers() {

        supplierService.deleteAllSuppliers();

        Map<String, Object> response =
                new HashMap<>();

        response.put(
                "status",
                200);

        response.put(
                "message",
                "All Suppliers Deleted Successfully");

        return response;
    }
    @GetMapping("/{id}/total-stock")
    public Map<String,Object>
    getTotalStockBySupplier(
            @PathVariable Integer id){

        Map<String,Object> response =
                new HashMap<>();

        response.put("status", 200);

        response.put(
                "message",
                "Supplier Stock Retrieved Successfully");

        response.put(
                "totalStock",
                supplierService
                        .getTotalStockBySupplier(id));

        return response;
    }
}