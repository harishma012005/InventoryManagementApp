package com.inventorymanagement.service;

import java.math.BigDecimal;
import java.util.List;

import com.inventorymanagement.dto.CreateProductDTO;
import com.inventorymanagement.dto.ProductDTO;

public interface ProductService {

    ProductDTO saveProduct(CreateProductDTO createProductDTO);

    List<ProductDTO> getAllProducts();

    ProductDTO getProductById(Integer id);

    ProductDTO updateProduct(Integer id, CreateProductDTO createProductDTO);

    void deleteProduct(Integer id);

    void deleteAllProducts();

    // Filters
    List<ProductDTO> getProductsBySupplierId(Integer supplierId);

    List<ProductDTO> searchProductsByName(String productName);

    List<ProductDTO> searchProductsByCategory(String category);

    List<ProductDTO> searchProductsBySupplierName(String supplierName);

    List<ProductDTO> getProductsByCategory(String category);

    List<ProductDTO> getProductsByPrice(BigDecimal price);

    List<ProductDTO> getProductsByQuantity(Integer quantity);
}