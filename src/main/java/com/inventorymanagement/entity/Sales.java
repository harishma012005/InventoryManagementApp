package com.inventorymanagement.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "sales")
public class Sales {

    // ================= PRIMARY KEY =================
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer salesId;

    // ================= USER MAPPING =================
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // ================= TOTAL AMOUNT =================
    private BigDecimal totalAmount;

    // ================= SALES DATE =================
 
    private LocalDateTime salesDate;
     

    // ================= SALES ITEMS =================
    @OneToMany(mappedBy = "sales",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<SalesItem> items = new ArrayList<>();
    @PrePersist
    public void onCreate() {
        this.salesDate = LocalDateTime.now();
    }
    // ================= GETTERS & SETTERS =================

    public Integer getSalesId() {
        return salesId;
    }

    public void setSalesId(Integer salesId) {
        this.salesId = salesId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getSalesDate() {
        return salesDate;
    }

    public void setSalesDate(LocalDateTime salesDate) {
        this.salesDate = salesDate;
    }

    public List<SalesItem> getItems() {
        return items;
    }

    public void setItems(List<SalesItem> items) {
        this.items = items;
    }
}