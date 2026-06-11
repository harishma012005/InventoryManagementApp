package com.inventorymanagement.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.inventorymanagement.dto.CreateProductDTO;
import com.inventorymanagement.dto.ProductDTO;
import com.inventorymanagement.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // ================= CREATE PRODUCT =================
    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> saveProduct(
            @Valid @RequestBody CreateProductDTO dto) {

        ProductDTO savedProduct = productService.saveProduct(dto);

        Map<String, Object> response = new HashMap<>();
        response.put("status", 201);
        response.put("message", "Product Saved Successfully");
        response.put("data", savedProduct);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ================= GET ALL =================
    @GetMapping("/getAll")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    // ================= GET BY ID =================
    @GetMapping("/getById/{id}")
    public ResponseEntity<ProductDTO> getProductById(
            @PathVariable Integer id) {

        return ResponseEntity.ok(productService.getProductById(id));
    }

    // ================= SEARCH BY NAME =================
    @GetMapping("/search/name/{productName}")
    public ResponseEntity<List<ProductDTO>> searchByName(
            @PathVariable String productName) {

        return ResponseEntity.ok(
                productService.searchProductsByName(productName));
    }

    // ================= SEARCH BY CATEGORY =================
    @GetMapping("/search/category/{category}")
    public ResponseEntity<List<ProductDTO>> searchByCategory(
            @PathVariable String category) {

        return ResponseEntity.ok(
                productService.searchProductsByCategory(category));
    }

    // ================= SEARCH BY SUPPLIER NAME =================
   

    // ================= FILTER BY SUPPLIER ID =================
   

    // ================= FILTER BY CATEGORY =================
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductDTO>> getByCategory(
            @PathVariable String category) {

        return ResponseEntity.ok(
                productService.getProductsByCategory(category));
    }

    // ================= FILTER BY PRICE =================
    @GetMapping("/price/{price}")
    public ResponseEntity<List<ProductDTO>> getByPrice(
            @PathVariable BigDecimal price) {

        return ResponseEntity.ok(
                productService.getProductsByPrice(price));
    }

    // ================= FILTER BY QUANTITY =================
    @GetMapping("/quantity/{quantity}")
    public ResponseEntity<List<ProductDTO>> getByQuantity(
            @PathVariable Integer quantity) {

        return ResponseEntity.ok(
                productService.getProductsByQuantity(quantity));
    }

    // ================= UPDATE PRODUCT =================
    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> updateProduct(
            @PathVariable Integer id,
            @Valid @RequestBody CreateProductDTO dto) {

        ProductDTO updatedProduct = productService.updateProduct(id, dto);

        Map<String, Object> response = new HashMap<>();
        response.put("status", 200);
        response.put("message", "Product Updated Successfully");
        response.put("data", updatedProduct);

        return ResponseEntity.ok(response);
    }

    // ================= DELETE PRODUCT =================
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteProduct(
            @PathVariable Integer id) {

        productService.deleteProduct(id);

        Map<String, Object> response = new HashMap<>();
        response.put("status", 200);
        response.put("message", "Product Deleted Successfully");

        return ResponseEntity.ok(response);
    }

    // ================= DELETE ALL =================
    @DeleteMapping("/deleteAll")
    public ResponseEntity<Map<String, Object>> deleteAllProducts() {

        productService.deleteAllProducts();

        Map<String, Object> response = new HashMap<>();
        response.put("status", 200);
        response.put("message", "All Products Deleted Successfully");

        return ResponseEntity.ok(response);
    }
}
