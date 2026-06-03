package com.inventorymanagement.repository;
import java.math.BigDecimal;
import java.util.List; 
import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository;
import com.inventorymanagement.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findBySupplier_SupplierId(Integer supplierId);

    List<Product> findByProductNameContainingIgnoreCase(String productName);

    List<Product> findByCategory_CategoryNameContainingIgnoreCase(String categoryName);

    List<Product> findBySupplier_SupplierNameContainingIgnoreCase(String supplierName);

    List<Product> findByPriceLessThanEqual(BigDecimal price);

    List<Product> findByQuantityLessThanEqual(Integer quantity);
    List<Product> findByQuantity(Integer quantity);
    boolean existsByProductNameAndSupplier_SupplierIdAndCategory_CategoryId(
            String productName,
            Integer supplierId,
            Integer categoryId);
}