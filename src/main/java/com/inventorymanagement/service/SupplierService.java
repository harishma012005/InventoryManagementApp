package com.inventorymanagement.service;

import java.util.List;

import com.inventorymanagement.entity.Supplier;

public interface SupplierService {

    Supplier saveSupplier(
            Supplier supplier);

    List<Supplier> getAllSuppliers();

    Supplier getSupplierById(
            Integer id);

    Supplier updateSupplier(
            Integer id,
            Supplier supplier);

    void deleteSupplier(
            Integer id);

    void deleteAllSuppliers();

    // Get Total Stock By Supplier
    Integer getTotalStockBySupplier(
            Integer supplierId);
}