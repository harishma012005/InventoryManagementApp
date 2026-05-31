package com.inventorymanagement.repository;
import java.math.BigDecimal;
import java.util.List; 
import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository;
import com.inventorymanagement.entity.Product;
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    // Filter By Supplier ID
    List<Product> findBySupplier_SupplierId(Integer supplierId);

    // Search By Product Name
    List<Product> findByProductNameContainingIgnoreCase(String productName);

    // Search By Category Name
    List<Product> findByCategory_CategoryNameContainingIgnoreCase(String categoryName);

    // Filter By Category Name (Exact)
    List<Product> findByCategory_CategoryNameIgnoreCase(String categoryName);

    // Search By Supplier Name
    List<Product> findBySupplier_SupplierNameContainingIgnoreCase(String supplierName);

    // Filter By Price
    List<Product> findByPriceLessThanEqual(BigDecimal price);

    // Filter By Quantity
    List<Product> findByQuantityLessThanEqual(Integer quantity);
    List<Product> findByQuantity(
            Integer quantity);
    
    boolean existsByProductNameAndSupplier_SupplierIdAndCategory_CategoryId(
            String productName,
            Integer supplierId,
            Integer categoryId);
}