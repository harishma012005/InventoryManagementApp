package com.inventorymanagement.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.inventorymanagement.entity.Product;
import com.inventorymanagement.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Add Product
    @PostMapping
    public Map<String, Object> saveProduct(
            @RequestBody Product product) {

        Product savedProduct =
                productService.saveProduct(product);

        Map<String, Object> response =
                new HashMap<>();

        response.put(
                "status",
                201);

        response.put(
                "message",
                "Product Saved Successfully");

        response.put(
                "data",
                savedProduct);

        return response;
    }

    // View All Products
    @GetMapping
    public List<Product> getAllProducts() {

        return productService.getAllProducts();
    }

    // View Product By ID
    @GetMapping("/{id}")
    public Product getProductById(
            @PathVariable Integer id) {

        return productService.getProductById(id);
    }

    // Update Product
    @PutMapping("/{id}")
    public Map<String, Object> updateProduct(
            @PathVariable Integer id,
            @RequestBody Product product) {

        Product updatedProduct =
                productService.updateProduct(
                id, product);

        Map<String, Object> response =
                new HashMap<>();

        response.put(
                "status",
                200);

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

        response.put(
                "status",
                200);

        response.put(
                "message",
                "Product Deleted Successfully");

        return response;
    }

    // Delete All Products
    @DeleteMapping
    public Map<String, Object> deleteAllProducts() {

        productService.deleteAllProducts();

        Map<String, Object> response =
                new HashMap<>();

        response.put(
                "status",
                200);

        response.put(
                "message",
                "All Products Deleted Successfully");

        return response;
    }
}