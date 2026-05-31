
package com.inventorymanagement.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "stock_transactions")
public class StockTransaction {

    @Id
    @GeneratedValue(strategy =
            GenerationType.IDENTITY)

    @Column(name = "transaction_id")
    private Integer transactionId;

    @ManyToOne
    @JoinColumn(name = "product_id")

    private Product product;

    @Column(name = "transaction_type")
    private String transactionType;

    private Integer quantity;

    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(
            Integer transactionId) {

        this.transactionId = transactionId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(
            String transactionType) {

        this.transactionType = transactionType;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(
            Integer quantity) {

        this.quantity = quantity;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(
            LocalDateTime transactionDate) {

        this.transactionDate = transactionDate;
    }
}