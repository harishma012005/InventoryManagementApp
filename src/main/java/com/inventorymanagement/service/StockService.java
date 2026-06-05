package com.inventorymanagement.service;

import java.util.List;

import com.inventorymanagement.dto.StockDTO;
import com.inventorymanagement.entity.Product;
import com.inventorymanagement.dto.LowStockDTO;

public interface StockService {
	
    List<StockDTO> getAllStock();

    List<LowStockDTO> getLowStock(Integer threshold);

    StockDTO getStockByProductId(Integer productId);
}