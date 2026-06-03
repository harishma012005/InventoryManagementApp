package com.inventorymanagement.serviceimpl;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventorymanagement.dto.StockTransactionDTO;
import com.inventorymanagement.entity.*;
import com.inventorymanagement.exception.ResourceNotFoundException;
import com.inventorymanagement.repository.*;
import com.inventorymanagement.service.StockService;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StockTransactionRepository stockTransactionRepository;

    // ---------------- DTO MAPPER ----------------
    private StockTransactionDTO convertToDTO(StockTransaction tx) {

        StockTransactionDTO dto = new StockTransactionDTO();

        dto.setTransactionId(tx.getTransactionId());
        dto.setProductId(tx.getProduct().getProductId());
        dto.setProductName(tx.getProduct().getProductName());
        dto.setTransactionType(tx.getTransactionType());
        dto.setQuantity(tx.getQuantity());
        dto.setTransactionDate(tx.getTransactionDate());

        return dto;
    }

    // ---------------- SAVE TRANSACTION ----------------
    private void saveTransaction(Product product, String type, Integer qty) {

        StockTransaction tx = new StockTransaction();
        tx.setProduct(product);
        tx.setTransactionType(type);
        tx.setQuantity(qty);
        tx.setTransactionDate(LocalDateTime.now());

        stockTransactionRepository.save(tx);
    }

    // ---------------- STOCK IN ----------------
    @Override
    public StockTransactionDTO stockIn(Integer productId, Integer quantity) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));

        product.setQuantity(product.getQuantity() + quantity);

        productRepository.save(product);

        StockTransaction tx = new StockTransaction();
        tx.setProduct(product);
        tx.setTransactionType("STOCK_IN");
        tx.setQuantity(quantity);
        tx.setTransactionDate(LocalDateTime.now());

        StockTransaction savedTx = stockTransactionRepository.save(tx);

        return convertToDTO(savedTx);
    }
    // ---------------- STOCK OUT ----------------
    @Override
    public StockTransactionDTO stockOut(Integer productId, Integer quantity) {

        if (quantity <= 0) {
            throw new RuntimeException("Quantity must be greater than 0");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));

        if (product.getQuantity() < quantity) {
            throw new RuntimeException("Insufficient Stock");
        }

        // reduce stock
        product.setQuantity(product.getQuantity() - quantity);
        Product savedProduct = productRepository.save(product);

        // create transaction
        StockTransaction tx = new StockTransaction();
        tx.setProduct(savedProduct);
        tx.setTransactionType("STOCK_OUT");
        tx.setQuantity(quantity);
        tx.setTransactionDate(LocalDateTime.now());

        StockTransaction savedTx = stockTransactionRepository.save(tx);

        // return DTO (NOT ENTITY)
        return convertToDTO(savedTx);
    }

    // ---------------- TOTAL STOCK ----------------
    @Override
    public Integer getTotalStock() {
        return productRepository.findAll()
                .stream()
                .mapToInt(Product::getQuantity)
                .sum();
    }

    // ---------------- STATUS ----------------
    @Override
    public String getStockStatus(Integer productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));

        if (product.getQuantity() == 0) return "OUT_OF_STOCK";
        if (product.getQuantity() <= 5) return "LOW_STOCK";
        return "AVAILABLE";
    }

    // ---------------- LOW STOCK ----------------
    @Override
    public List<Product> getLowStockProducts() {
        return productRepository.findByQuantityLessThanEqual(5);
    }

    // ---------------- STOCK HISTORY ----------------
    @Override
    public List<StockTransactionDTO> getStockHistory() {
        return stockTransactionRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ---------------- STOCK HISTORY BY PRODUCT ----------------
    @Override
    public List<StockTransactionDTO> getStockHistoryByProduct(Integer productId) {
        return stockTransactionRepository.findByProduct_ProductId(productId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ---------------- OUT OF STOCK ----------------
    @Override
    public List<Product> getOutOfStockProducts() {
        return productRepository.findByQuantity(0);
    }

    // ---------------- INVENTORY VALUE ----------------
    @Override
    public Double getInventoryValue() {
        return productRepository.findAll()
                .stream()
                .mapToDouble(p -> p.getPrice().doubleValue() * p.getQuantity())
                .sum();
    }

    // ---------------- DASHBOARD ----------------
    @Override
    public Map<String, Object> getDashboardSummary() {

        Map<String, Object> map = new HashMap<>();

        List<Product> products = productRepository.findAll();

        map.put("totalProducts", products.size());
        map.put("totalStock", products.stream().mapToInt(Product::getQuantity).sum());
        map.put("lowStockProducts", products.stream().filter(p -> p.getQuantity() <= 5).count());
        map.put("outOfStockProducts", products.stream().filter(p -> p.getQuantity() == 0).count());

        return map;
    }
}