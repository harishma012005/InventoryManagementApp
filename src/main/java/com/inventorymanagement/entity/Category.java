package com.inventorymanagement.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import jakarta.validation.constraints.*;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy =
            GenerationType.IDENTITY)

    @Column(name = "category_id")
    private Integer categoryId;

    @NotBlank(message =
            "Category Name Cannot Be Empty")

    @Column(name = "category_name")
    private String categoryName;

    @NotBlank(message =
            "Description Cannot Be Empty")

    private String description;

    @OneToMany(mappedBy = "category")
    @JsonIgnore

    private List<Product> products;

    @CreationTimestamp
    @Column(name = "created_at",
            updatable = false)

    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")

    private LocalDateTime updatedAt;

    // Getters and Setters

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(
            Integer categoryId) {

        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(
            String categoryName) {

        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(
            String description) {

        this.description = description;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(
            List<Product> products) {

        this.products = products;
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