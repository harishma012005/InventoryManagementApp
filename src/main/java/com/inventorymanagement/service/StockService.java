package com.inventorymanagement.service;

import java.util.List;
import java.util.Map;

import com.inventorymanagement.dto.StockTransactionDTO;
import com.inventorymanagement.entity.Product;

public interface StockService {

    StockTransactionDTO stockIn(Integer productId, Integer quantity);

    StockTransactionDTO stockOut(Integer productId, Integer quantity);

    Integer getTotalStock();

    String getStockStatus(Integer productId);

    List<Product> getLowStockProducts();

    List<StockTransactionDTO> getStockHistory();

    List<StockTransactionDTO> getStockHistoryByProduct(Integer productId);

    List<Product> getOutOfStockProducts();

    Double getInventoryValue();

    Map<String, Object> getDashboardSummary();
}