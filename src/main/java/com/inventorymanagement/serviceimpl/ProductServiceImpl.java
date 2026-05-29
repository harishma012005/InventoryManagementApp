package com.inventorymanagement.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventorymanagement.entity.Product;
import com.inventorymanagement.exception.ResourceNotFoundException;
import com.inventorymanagement.repository.ProductRepository;
import com.inventorymanagement.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    
    // Save Product
    @Override
    public Product saveProduct(Product product) {

        boolean exists = productRepository
                .existsByProductNameAndSupplier_SupplierIdAndCategory_CategoryId(
                        product.getProductName(),
                        product.getSupplier().getSupplierId(),
                        product.getCategory().getCategoryId()
                );

        if (exists) {
            throw new RuntimeException(
                    "Product already exists for this Supplier and Category");
        }

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
                                "Product Not Found With ID : " + id));
    }

    // Update Product
    @Override
    public Product updateProduct(Integer id, Product product) {

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product Not Found With ID : " + id));

        existingProduct.setProductName(product.getProductName());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setSupplier(product.getSupplier());

        return productRepository.save(existingProduct);
    }

    // Delete Product
    @Override
    public void deleteProduct(Integer id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product Not Found With ID : " + id));

        productRepository.delete(product);
    }

    // Delete All Products
    @Override
    public void deleteAllProducts() {
        productRepository.deleteAll();
    }

    // Filter By Supplier ID (FIXED)
    @Override
    public List<Product> getProductsBySupplierId(Integer supplierId) {

        return productRepository.findBySupplier_SupplierId(supplierId);
    }

    // Search By Product Name
    @Override
    public List<Product> searchProductsByName(String productName) {

        return productRepository
                .findByProductNameContainingIgnoreCase(productName);
    }

    // Search By Category (FIXED for entity relationship)
    @Override
    public List<Product> searchProductsByCategory(String category) {

        return productRepository
                .findByCategory_CategoryNameContainingIgnoreCase(category);
    }

    // Search By Supplier Name
    @Override
    public List<Product> searchProductsBySupplierName(String supplierName) {

        return productRepository
                .findBySupplier_SupplierNameContainingIgnoreCase(supplierName);
    }

    // Filter By Exact Category
    @Override
    public List<Product> getProductsByCategory(String category) {

        return productRepository
                .findByCategory_CategoryNameContainingIgnoreCase(category);
    }

    // Filter By Price
    @Override
    public List<Product> getProductsByPrice(BigDecimal price) {

        return productRepository
                .findByPriceLessThanEqual(price);
    }

    // Filter By Quantity
    @Override
    public List<Product> getProductsByQuantity(Integer quantity) {

        return productRepository
                .findByQuantityLessThanEqual(quantity);
    }
}