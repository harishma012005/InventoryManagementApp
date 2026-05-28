package com.inventorymanagement.controller;

import java.util.List;

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
    public Product saveProduct(
            @RequestBody Product product) {

        return productService.saveProduct(product);
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
    public Product updateProduct(
            @PathVariable Integer id,
            @RequestBody Product product) {

        return productService.updateProduct(
                id, product);
    }

    // Delete Product
    @DeleteMapping("/{id}")
    public String deleteProduct(
            @PathVariable Integer id) {

        productService.deleteProduct(id);

        return "Product Deleted Successfully";
    }

    // Delete All Products
    @DeleteMapping
    public String deleteAllProducts() {

        productService.deleteAllProducts();

        return "All Products Deleted Successfully";
    }
}