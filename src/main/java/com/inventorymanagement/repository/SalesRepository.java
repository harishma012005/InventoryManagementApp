package com.inventorymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

import com.inventorymanagement.entity.Sales;

@Repository
public interface SalesRepository extends JpaRepository<Sales, Integer> {
	 List<Sales> findByUser_UserId(Integer userId);


}