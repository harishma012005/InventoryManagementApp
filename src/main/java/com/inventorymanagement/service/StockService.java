package com.inventorymanagement.service;

import java.util.List;
import java.util.Map;

import com.inventorymanagement.entity.Product;
import com.inventorymanagement.entity.StockTransaction;

public interface StockService {

    Product stockIn(
            Integer productId,
            Integer quantity);

    Product stockOut(
            Integer productId,
            Integer quantity);

    Integer getTotalStock();

    String getStockStatus(
            Integer productId);

    List<Product> getLowStockProducts();

    List<StockTransaction>
    getStockHistory();
    List<StockTransaction> getStockHistoryByProduct(
            Integer productId);

    List<Product> getOutOfStockProducts();

    Double getInventoryValue();

    Map<String, Object> getDashboardSummary();
}
