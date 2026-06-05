package com.inventorymanagement.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventorymanagement.dto.LowStockDTO;
import com.inventorymanagement.dto.StockDTO;
import com.inventorymanagement.entity.Product;
import com.inventorymanagement.exception.ResourceNotFoundException;
import com.inventorymanagement.repository.ProductRepository;
import com.inventorymanagement.service.StockService;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<StockDTO> getAllStock() {

        List<Product> products = productRepository.findAll();

        if (products.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No Products Available");
        }

        List<StockDTO> stockList = new ArrayList<>();

        for (Product product : products) {

            StockDTO dto = new StockDTO();

            dto.setProductId(product.getProductId());
            dto.setProductName(product.getProductName());
            dto.setAvailableQuantity(product.getQuantity());
            dto.setPrice(product.getPrice());

            stockList.add(dto);
        }

        return stockList;
    }

    @Override
    public List<LowStockDTO> getLowStock(Integer threshold) {

        if (threshold == null || threshold <= 0) {
            throw new IllegalArgumentException(
                    "Threshold must be greater than zero");
        }

        List<Product> products =
                productRepository.findByQuantityLessThanEqual(threshold);

        List<LowStockDTO> lowStockList = new ArrayList<>();

        for (Product product : products) {

            LowStockDTO dto = new LowStockDTO();

            dto.setProductId(product.getProductId());
            dto.setProductName(product.getProductName());
            dto.setQuantity(product.getQuantity());

            lowStockList.add(dto);
        }

        return lowStockList;
    }

    @Override
    public StockDTO getStockByProductId(Integer productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product Not Found"));

        StockDTO dto = new StockDTO();

        dto.setProductId(product.getProductId());
        dto.setProductName(product.getProductName());
        dto.setAvailableQuantity(product.getQuantity());
        dto.setPrice(product.getPrice());

        return dto;
    }
}