package com.inventorymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inventorymanagement.entity.Sales;

@Repository
public interface SalesRepository extends JpaRepository<Sales, Integer> {

}