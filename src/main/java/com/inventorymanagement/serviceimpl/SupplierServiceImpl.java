package com.inventorymanagement.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventorymanagement.entity.Product;
import com.inventorymanagement.entity.Supplier;
import com.inventorymanagement.exception.ResourceNotFoundException;
import com.inventorymanagement.repository.ProductRepository;
import com.inventorymanagement.repository.SupplierRepository;
import com.inventorymanagement.service.SupplierService;

@Service
public class SupplierServiceImpl
        implements SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ProductRepository productRepository;

    // Save Supplier
    @Override
    public Supplier saveSupplier(
            Supplier supplier) {

        return supplierRepository.save(
                supplier);
    }

    // Get All Suppliers
    @Override
    public List<Supplier> getAllSuppliers() {

        return supplierRepository.findAll();
    }

    // Get Supplier By ID
    @Override
    public Supplier getSupplierById(
            Integer id) {

        return supplierRepository.findById(id)
                .orElseThrow(() ->
                new ResourceNotFoundException(
                "Supplier Not Found With ID : "
                + id));
    }

    // Update Supplier
    @Override
    public Supplier updateSupplier(
            Integer id,
            Supplier supplier) {

        Supplier existingSupplier =
                supplierRepository.findById(id)
                .orElseThrow(() ->
                new ResourceNotFoundException(
                "Supplier Not Found With ID : "
                + id));

        existingSupplier.setSupplierName(
                supplier.getSupplierName());

        existingSupplier.setEmail(
                supplier.getEmail());

        existingSupplier.setPhone(
                supplier.getPhone());

        existingSupplier.setAddress(
                supplier.getAddress());

        return supplierRepository.save(
                existingSupplier);
    }

    // Delete Supplier
    @Override
    public void deleteSupplier(
            Integer id) {

        Supplier supplier =
                supplierRepository.findById(id)
                .orElseThrow(() ->
                new ResourceNotFoundException(
                "Supplier Not Found With ID : "
                + id));

        supplierRepository.delete(
                supplier);
    }

    // Delete All Suppliers
    @Override
    public void deleteAllSuppliers() {

        supplierRepository.deleteAll();
    }

    // Get Total Stock By Supplier
    @Override
    public Integer getTotalStockBySupplier(
            Integer supplierId) {

       
                supplierRepository.findById(
                        supplierId)

                .orElseThrow(() ->
                new ResourceNotFoundException(
                "Supplier Not Found With ID : "
                + supplierId));

        List<Product> products =
                productRepository
                .findBySupplier_SupplierId(
                        supplierId);

        return products.stream()
                .mapToInt(
                        Product::getQuantity)
                .sum();
    }
}