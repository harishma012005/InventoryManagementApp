package com.inventorymanagement.service;

import java.util.List;

import com.inventorymanagement.dto.SupplierDTO;

public interface SupplierService {

    SupplierDTO saveSupplier(SupplierDTO supplierDTO);

    List<SupplierDTO> getAllSuppliers();

    SupplierDTO getSupplierById(Integer id);

    SupplierDTO updateSupplier(Integer id, SupplierDTO supplierDTO);

    void deleteSupplier(Integer id);

    void deleteAllSuppliers();

    Integer getTotalStockBySupplier(Integer supplierId);
}