

package com.inventorymanagement.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventorymanagement.entity.Product;
import com.inventorymanagement.repository.ProductRepository;
import com.inventorymanagement.service.ProductService;

@Service
public class ProductServiceImpl
        implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product saveProduct(Product product) {

        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {

        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Integer id) {

        return productRepository.findById(id)
                .orElse(null);
    }

    @Override
    public Product updateProduct(Integer id,
                                 Product product) {

        Product existingProduct =
                productRepository.findById(id)
                .orElse(null);

        if(existingProduct != null) {

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

        return null;
    }

    @Override
    public void deleteProduct(Integer id) {

        productRepository.deleteById(id);
    }

    @Override
    public void deleteAllProducts() {

        productRepository.deleteAll();
    }
}