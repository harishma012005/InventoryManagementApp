package com.inventorymanagement.service;

import java.util.List;

import com.inventorymanagement.dto.CreatePurchaseDTO;
import com.inventorymanagement.dto.PurchaseDTO;
import com.inventorymanagement.dto.PurchaseResponseDTO;
import com.inventorymanagement.entity.Purchase;

public interface PurchaseService {

    PurchaseResponseDTO createPurchase(CreatePurchaseDTO dto);

    List<PurchaseDTO> getAllPurchases();

    PurchaseDTO getPurchaseById(Integer id);

    void deletePurchase(Integer id);
}