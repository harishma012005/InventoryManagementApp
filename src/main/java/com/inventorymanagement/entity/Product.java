package com.inventorymanagement.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;

import jakarta.validation.constraints.*;

@Entity
@Table(
	    name = "products",
	    uniqueConstraints = @UniqueConstraint(
	        columnNames = {"product_name", "supplier_id", "category_id"}
	    )
	)
public class Product {

    @Id
    @GeneratedValue(strategy =
            GenerationType.IDENTITY)

    @Column(name = "product_id")
    private Integer productId;

    @NotBlank(message =
            "Product Name Cannot Be Empty")

    @Column(name = "product_name")
    private String productName;

    @ManyToOne
    @JoinColumn(name = "category_id")

    private Category category;

    @NotNull(message =
            "Quantity Cannot Be Null")

    @Min(value = 0,
            message =
            "Quantity Cannot Be Negative")

    private Integer quantity;

    @NotNull(message =
            "Price Cannot Be Null")

    @Positive(message =
            "Price Must Be Greater Than Zero")

    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "supplier_id")

    private Supplier supplier;

    @CreationTimestamp
    @Column(name = "created_at",
            updatable = false)

    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")

    private LocalDateTime updatedAt;

    // Getters and Setters

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(
            Integer productId) {

        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(
            String productName) {

        this.productName = productName;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(
            Category category) {

        this.category = category;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(
            Integer quantity) {

        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(
            BigDecimal price) {

        this.price = price;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(
            Supplier supplier) {

        this.supplier = supplier;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(
            LocalDateTime createdAt) {

        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(
            LocalDateTime updatedAt) {

        this.updatedAt = updatedAt;
    }
}