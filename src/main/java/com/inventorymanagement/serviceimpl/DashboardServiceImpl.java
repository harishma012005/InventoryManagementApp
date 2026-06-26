package com.inventorymanagement.serviceimpl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventorymanagement.dto.DashboardDTO;
import com.inventorymanagement.entity.Product;
import com.inventorymanagement.entity.Purchase;
import com.inventorymanagement.entity.Sales;
import com.inventorymanagement.repository.CategoryRepository;
import com.inventorymanagement.repository.ProductRepository;
import com.inventorymanagement.repository.PurchaseRepository;
import com.inventorymanagement.repository.SalesRepository;
import com.inventorymanagement.repository.SupplierRepository;
import com.inventorymanagement.service.DashboardService;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private SalesRepository salesRepository;

    @Override
    public DashboardDTO getDashboardSummary() {

        DashboardDTO dto = new DashboardDTO();

        dto.setTotalProducts(productRepository.count());

        dto.setTotalCategories(categoryRepository.count());

        dto.setTotalSuppliers(supplierRepository.count());

        // Inventory Value

        BigDecimal inventoryValue = productRepository.findAll()
                .stream()
                .map(product ->
                        product.getPrice().multiply(
                                BigDecimal.valueOf(product.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        dto.setInventoryValue(inventoryValue);

        // Total Stock

        Integer stock = productRepository.findAll()
                .stream()
                .mapToInt(Product::getQuantity)
                .sum();

        dto.setTotalStock(stock);

        // Total Purchases

        BigDecimal totalPurchases = purchaseRepository.findAll()
                .stream()
                .map(Purchase::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        dto.setTotalPurchases(totalPurchases);

        // Total Sales

        BigDecimal totalSales = salesRepository.findAll()
                .stream()
                .map(Sales::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        dto.setTotalSales(totalSales);

        // Low Stock

        dto.setLowStockCount(
                productRepository.countByQuantityLessThanEqual(5));

        // Out Of Stock

        dto.setOutOfStockCount(
                productRepository.countByQuantity(0));

        return dto;
    }
}