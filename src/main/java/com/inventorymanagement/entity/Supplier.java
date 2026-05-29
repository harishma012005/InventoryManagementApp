package com.inventorymanagement.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import jakarta.validation.constraints.*;

@Entity
@Table(name = "suppliers")
public class Supplier {

    @Id
    @GeneratedValue(strategy =
            GenerationType.IDENTITY)

    @Column(name = "supplier_id")
    private Integer supplierId;

    @NotBlank(message =
            "Supplier Name Cannot Be Empty")

    @Column(name = "supplier_name")
    private String supplierName;

    @Email(message =
            "Invalid Email Format")

    private String email;

    @NotBlank(message =
            "Phone Number Cannot Be Empty")

    private String phone;

    @NotBlank(message =
            "Address Cannot Be Empty")

    private String address;

    @OneToMany(mappedBy = "supplier")
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

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(
            Integer supplierId) {

        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(
            String supplierName) {

        this.supplierName = supplierName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(
            String email) {

        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(
            String phone) {

        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(
            String address) {

        this.address = address;
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
