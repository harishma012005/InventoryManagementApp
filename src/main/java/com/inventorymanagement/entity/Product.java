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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;

    @NotBlank
    @Column(name = "product_name")
    private String productName;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


    @NotNull
    @Min(0)
    private Integer quantity;

    @NotNull
    @Positive
    private BigDecimal price;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // getters & setters


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