package com.inventorymanagement.service;

import java.util.List;

import com.inventorymanagement.dto.CreateSalesDTO;
import com.inventorymanagement.dto.SalesResponseDTO;

public interface SalesService {

    // ================= CREATE SALES =================
    SalesResponseDTO createSales(CreateSalesDTO dto);

    // ================= GET ALL SALES =================
    List<SalesResponseDTO> getAllSales();

    // ================= GET SALES BY ID =================
    SalesResponseDTO getSalesById(Integer id);

    // ================= DELETE SALES =================
    void deleteSales(Integer id);
    List<SalesResponseDTO> getMySales(Integer userId);
}