

package com.inventorymanagement.service;

import java.util.List;

import com.inventorymanagement.entity.Product;

public interface ProductService {

    Product saveProduct(Product product);

    List<Product> getAllProducts();

    Product getProductById(Integer id);

    Product updateProduct(Integer id,
                          Product product);

    void deleteProduct(Integer id);

    void deleteAllProducts();
}