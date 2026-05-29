package com.inventorymanagement.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.inventorymanagement.entity.Product;
import com.inventorymanagement.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Add Product
    @PostMapping
    public Map<String, Object> saveProduct(
            @Valid
            @RequestBody Product product) {

        Product savedProduct =
                productService.saveProduct(
                product);

        Map<String, Object> response =
                new HashMap<>();

        response.put("status", 201);
        response.put(
                "message",
                "Product Saved Successfully");

        response.put(
                "data",
                savedProduct);

        return response;
    }

    // Get All Products
    @GetMapping
    public List<Product> getAllProducts() {

        return productService.getAllProducts();
    }

    // Get Product By ID
    @GetMapping("/{id}")
    public Product getProductById(
            @PathVariable Integer id) {

        return productService.getProductById(id);
    }

    // Search By Product Name
    @GetMapping("/search/{productName}")
    public List<Product>
    searchProductsByName(
            @PathVariable String productName) {

        return productService
                .searchProductsByName(
                productName);
    }

    // Search By Category
    @GetMapping("/search/category/{category}")
    public List<Product>
    searchProductsByCategory(
            @PathVariable String category) {

        return productService
                .searchProductsByCategory(
                category);
    }

    // Search By Supplier Name
    @GetMapping("/search/supplier/{supplierName}")
    public List<Product>
    searchProductsBySupplierName(
            @PathVariable String supplierName) {

        return productService
                .searchProductsBySupplierName(
                supplierName);
    }

    // Filter By Supplier ID
    @GetMapping("/supplier/{supplierId}")
    public List<Product>
    getProductsBySupplierId(
            @PathVariable Integer supplierId) {

        return productService
                .getProductsBySupplierId(
                supplierId);
    }

    // Filter By Category
    @GetMapping("/category/{category}")
    public List<Product>
    getProductsByCategory(
            @PathVariable String category) {

        return productService
                .getProductsByCategory(
                category);
    }

    // Filter By Price
    @GetMapping("/price/{price}")
    public List<Product>
    getProductsByPrice(
            @PathVariable BigDecimal price) {

        return productService
                .getProductsByPrice(
                price);
    }

    // Filter By Quantity
    @GetMapping("/quantity/{quantity}")
    public List<Product>
    getProductsByQuantity(
            @PathVariable Integer quantity) {

        return productService
                .getProductsByQuantity(
                quantity);
    }

    // Update Product
    @PutMapping("/{id}")
    public Map<String, Object> updateProduct(
    		
            @PathVariable Integer id,
            @Valid
            
            @RequestBody Product product) {

        Product updatedProduct =
                productService.updateProduct(
                id, product);

        Map<String, Object> response =
                new HashMap<>();

        response.put("status", 200);

        response.put(
                "message",
                "Product Updated Successfully");

        response.put(
                "data",
                updatedProduct);

        return response;
    }

    // Delete Product
    @DeleteMapping("/{id}")
    public Map<String, Object> deleteProduct(
            @PathVariable Integer id) {

        productService.deleteProduct(id);

        Map<String, Object> response =
                new HashMap<>();

        response.put("status", 200);

        response.put(
                "message",
                "Product Deleted Successfully");

        return response;
    }

    // Delete All Products
    @DeleteMapping
    public Map<String, Object>
    deleteAllProducts() {

        productService.deleteAllProducts();

        Map<String, Object> response =
                new HashMap<>();

        response.put("status", 200);

        response.put(
                "message",
                "All Products Deleted Successfully");

        return response;
    }
}