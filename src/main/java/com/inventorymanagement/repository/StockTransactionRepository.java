package com.inventorymanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventorymanagement.entity.Product;
import com.inventorymanagement.entity.StockTransaction;

public interface StockTransactionRepository
        extends JpaRepository
        <StockTransaction, Integer> {
	List<StockTransaction>
	findByProduct_ProductId(
	        Integer productId);
	

}
