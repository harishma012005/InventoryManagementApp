package com.inventorymanagement.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventorymanagement.dto.SupplierDTO;
import com.inventorymanagement.entity.Supplier;
import com.inventorymanagement.entity.Product;
import com.inventorymanagement.exception.ResourceNotFoundException;
import com.inventorymanagement.repository.ProductRepository;
import com.inventorymanagement.repository.SupplierRepository;
import com.inventorymanagement.service.SupplierService;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

 

    // ENTITY → DTO
    private SupplierDTO convertToDTO(Supplier supplier) {

        SupplierDTO dto = new SupplierDTO();

        dto.setSupplierId(supplier.getSupplierId());
        dto.setSupplierName(supplier.getSupplierName());
        dto.setEmail(supplier.getEmail());
        dto.setPhone(supplier.getPhone());
        dto.setAddress(supplier.getAddress());

        return dto;
    }

    // DTO → ENTITY
    private Supplier convertToEntity(SupplierDTO dto) {

        Supplier supplier = new Supplier();

        supplier.setSupplierName(dto.getSupplierName());
        supplier.setEmail(dto.getEmail());
        supplier.setPhone(dto.getPhone());
        supplier.setAddress(dto.getAddress());

        return supplier;
    }

    // SAVE
    @Override
    public SupplierDTO saveSupplier(SupplierDTO dto) {

        Supplier supplier = convertToEntity(dto);

        Supplier saved = supplierRepository.save(supplier);

        return convertToDTO(saved);
    }

    // GET ALL
    @Override
    public List<SupplierDTO> getAllSuppliers() {

        return supplierRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // GET BY ID
    @Override
    public SupplierDTO getSupplierById(Integer id) {

        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Supplier Not Found With ID: " + id));

        return convertToDTO(supplier);
    }

    // UPDATE
    @Override
    public SupplierDTO updateSupplier(Integer id, SupplierDTO dto) {

        Supplier existing = supplierRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Supplier Not Found With ID: " + id));

        existing.setSupplierName(dto.getSupplierName());
        existing.setEmail(dto.getEmail());
        existing.setPhone(dto.getPhone());
        existing.setAddress(dto.getAddress());

        Supplier updated = supplierRepository.save(existing);

        return convertToDTO(updated);
    }

    // DELETE
    @Override
    public void deleteSupplier(Integer id) {

        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Supplier Not Found With ID: " + id));

        supplierRepository.delete(supplier);
    }

    // DELETE ALL
    @Override
    public void deleteAllSuppliers() {
        supplierRepository.deleteAll();
    }

    // TOTAL STOCK
  
}