
package com.inventorymanagement.service;

import java.math.BigDecimal;
import java.util.List;

import com.inventorymanagement.entity.Product;

public interface ProductService {

    Product saveProduct(
            Product product);

    List<Product> getAllProducts();

    Product getProductById(
            Integer id);

    Product updateProduct(
            Integer id,
            Product product);

    void deleteProduct(
            Integer id);

    void deleteAllProducts();

    // Filter By Supplier ID
    List<Product>
    getProductsBySupplierId(
            Integer supplierId);

    // Search Product By Name
    List<Product>
    searchProductsByName(
            String productName);

    // Search Product By Category
    List<Product>
    searchProductsByCategory(
            String category);

    // Search Product By Supplier Name
    List<Product>
    searchProductsBySupplierName(
            String supplierName);

    // Filter By Category
    List<Product>
    getProductsByCategory(
            String category);

    // Filter By Price
    List<Product>
    getProductsByPrice(
            BigDecimal price);

    // Filter By Quantity
    List<Product>
    getProductsByQuantity(
            Integer quantity);
}