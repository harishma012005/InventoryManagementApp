
package com.inventorymanagement.serviceimpl;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventorymanagement.entity.Product;
import com.inventorymanagement.entity.StockTransaction;
import com.inventorymanagement.exception.ResourceNotFoundException;
import com.inventorymanagement.repository.ProductRepository;
import com.inventorymanagement.repository.StockTransactionRepository;
import com.inventorymanagement.service.StockService;

@Service
public class StockServiceImpl
        implements StockService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StockTransactionRepository
            stockTransactionRepository;

    @Override
    public Product stockIn(
            Integer productId,
            Integer quantity) {

        if(quantity <= 0) {

            throw new RuntimeException(
                    "Stock In Quantity Must Be Greater Than Zero");
        }

        Product product =
                productRepository.findById(productId)

                .orElseThrow(() ->
                new ResourceNotFoundException(
                        "Product Not Found With ID : "
                                + productId));

        product.setQuantity(
                product.getQuantity() + quantity);

        Product updatedProduct =
                productRepository.save(product);

        StockTransaction transaction =
                new StockTransaction();

        transaction.setProduct(updatedProduct);

        transaction.setTransactionType(
                "STOCK_IN");

        transaction.setQuantity(quantity);

        transaction.setTransactionDate(
                LocalDateTime.now());

        stockTransactionRepository
                .save(transaction);

        return updatedProduct;
    }

    @Override
    public Product stockOut(
            Integer productId,
            Integer quantity) {

        if(quantity <= 0) {

            throw new RuntimeException(
                    "Stock Out Quantity Must Be Greater Than Zero");
        }

        Product product =
                productRepository.findById(productId)

                .orElseThrow(() ->
                new ResourceNotFoundException(
                        "Product Not Found With ID : "
                                + productId));

        if(product.getQuantity() < quantity) {

            throw new RuntimeException(
                    "Insufficient Stock Available");
        }

        product.setQuantity(
                product.getQuantity() - quantity);

        Product updatedProduct =
                productRepository.save(product);

        StockTransaction transaction =
                new StockTransaction();

        transaction.setProduct(updatedProduct);

        transaction.setTransactionType(
                "STOCK_OUT");

        transaction.setQuantity(quantity);

        transaction.setTransactionDate(
                LocalDateTime.now());

        stockTransactionRepository
                .save(transaction);

        return updatedProduct;
    }

    @Override
    public Integer getTotalStock() {

        List<Product> products =
                productRepository.findAll();

        return products.stream()

                .mapToInt(Product::getQuantity)

                .sum();
    }

    @Override
    public String getStockStatus(
            Integer productId) {

        Product product =
                productRepository.findById(productId)

                .orElseThrow(() ->
                new ResourceNotFoundException(
                        "Product Not Found With ID : "
                                + productId));

        Integer quantity =
                product.getQuantity();

        if(quantity == 0) {

            return "OUT_OF_STOCK";
        }

        if(quantity <= 5) {

            return "LOW_STOCK";
        }

        return "AVAILABLE";
    }

    @Override
    public List<Product>
    getLowStockProducts() {

        return productRepository
                .findByQuantityLessThanEqual(5);
    }
    @Override
    public Double getInventoryValue() {

        return productRepository.findAll()
                .stream()
                .mapToDouble(product ->
                        product.getPrice().doubleValue()
                        * product.getQuantity())
                .sum();
    }

    @Override
    public List<StockTransaction>
    getStockHistory() {

        return stockTransactionRepository
                .findAll();
    }
    @Override
    public List<StockTransaction>
    getStockHistoryByProduct(
            Integer productId) {

        return stockTransactionRepository
                .findByProduct_ProductId(
                        productId);
        
    }
    @Override
    public List<Product>
    getOutOfStockProducts() {

        return productRepository
                .findByQuantity(0);
    }
    @Override
    public Map<String, Object>
    getDashboardSummary() {

        Map<String, Object> summary =
                new HashMap<>();

        List<Product> products =
                productRepository.findAll();

        Integer totalProducts =
                products.size();

        Integer totalStock =
                products.stream()

                .mapToInt(Product::getQuantity)

                .sum();

        long lowStockProducts =
                products.stream()

                .filter(product ->
                        product.getQuantity() <= 5)

                .count();

        long outOfStockProducts =
                products.stream()

                .filter(product ->
                        product.getQuantity() == 0)

                .count();

        summary.put(
                "totalProducts",
                totalProducts);

        summary.put(
                "totalStock",
                totalStock);

        summary.put(
                "lowStockProducts",
                lowStockProducts);

        summary.put(
                "outOfStockProducts",
                outOfStockProducts);

        return summary;
    }
}