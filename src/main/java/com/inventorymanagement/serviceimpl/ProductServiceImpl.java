package com.inventorymanagement.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventorymanagement.entity.Product;
import com.inventorymanagement.exception.ResourceNotFoundException;
import com.inventorymanagement.repository.ProductRepository;
import com.inventorymanagement.service.ProductService;

@Service
public class ProductServiceImpl
        implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Save Product
    @Override
    public Product saveProduct(Product product) {

        return productRepository.save(product);
    }

    // Get All Products
    @Override
    public List<Product> getAllProducts() {

        return productRepository.findAll();
    }

    // Get Product By ID
    @Override
    public Product getProductById(Integer id) {

        return productRepository.findById(id)
                .orElseThrow(() ->
                new ResourceNotFoundException(
                "Product Not Found With ID : "
                + id));
    }

    // Update Product
    @Override
    public Product updateProduct(
            Integer id,
            Product product) {

        Product existingProduct =
                productRepository.findById(id)
                .orElseThrow(() ->
                new ResourceNotFoundException(
                "Product Not Found With ID : "
                + id));

        existingProduct.setProductName(
                product.getProductName());

        existingProduct.setCategory(
                product.getCategory());

        existingProduct.setQuantity(
                product.getQuantity());

        existingProduct.setPrice(
                product.getPrice());

        existingProduct.setSupplier(
                product.getSupplier());

        return productRepository.save(
                existingProduct);
    }

    // Delete Product
    @Override
    public void deleteProduct(Integer id) {

        Product product =
                productRepository.findById(id)
                .orElseThrow(() ->
                new ResourceNotFoundException(
                "Product Not Found With ID : "
                + id));

        productRepository.delete(product);
    }

    // Delete All Products
    @Override
    public void deleteAllProducts() {

        productRepository.deleteAll();
    }
}