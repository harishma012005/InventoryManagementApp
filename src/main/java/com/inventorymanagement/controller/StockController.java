package com.inventorymanagement.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.inventorymanagement.entity.Product;
import com.inventorymanagement.entity.StockTransaction;
import com.inventorymanagement.service.StockService;

@RestController
@RequestMapping("/stocks")
public class StockController {

    @Autowired
    private StockService stockService;

    @PostMapping("/stockin/{id}/{quantity}")
    public Map<String,Object> stockIn(

            @PathVariable Integer id,

            @PathVariable Integer quantity){

        Product product =
                stockService.stockIn(
                        id,
                        quantity);

        Map<String,Object> response =
                new HashMap<>();

        response.put("status",200);

        response.put(
                "message",
                "Stock Added Successfully");

        response.put(
                "data",
                product);

        return response;
    }

    @PostMapping("/stockout/{id}/{quantity}")
    public Map<String,Object> stockOut(

            @PathVariable Integer id,

            @PathVariable Integer quantity){

        Product product =
                stockService.stockOut(
                        id,
                        quantity);

        Map<String,Object> response =
                new HashMap<>();

        response.put("status",200);

        response.put(
                "message",
                "Stock Reduced Successfully");

        response.put(
                "data",
                product);

        return response;
    }

    @GetMapping("/total-stock")
    public Map<String,Object>
    getTotalStock(){

        Map<String,Object> response =
                new HashMap<>();

        response.put("status",200);

        response.put(
                "message",
                "Total Stock Retrieved Successfully");

        response.put(
                "totalStock",
                stockService.getTotalStock());

        return response;
    }

    @GetMapping("/status/{id}")
    public Map<String,Object>
    getStockStatus(

            @PathVariable Integer id){

        Map<String,Object> response =
                new HashMap<>();

        response.put("status",200);

        response.put(
                "message",
                "Stock Status Retrieved Successfully");

        response.put(
                "data",
                stockService.getStockStatus(id));

        return response;
    }

    @GetMapping("/low-stock")
    public List<Product>
    getLowStockProducts(){

        return stockService
                .getLowStockProducts();
    }

    @GetMapping("/history")
    public List<StockTransaction>
    getStockHistory(){

        return stockService
                .getStockHistory();
    }
    @GetMapping("/history/product/{id}")
    public List<StockTransaction>
    getStockHistoryByProduct(
            @PathVariable Integer id) {

        return stockService
                .getStockHistoryByProduct(id);
    }
    @GetMapping("/out-of-stock")
    public List<Product>
    getOutOfStockProducts() {

        return stockService
                .getOutOfStockProducts();
    }
    @GetMapping("/inventory-value")
    public Map<String,Object>
    getInventoryValue() {

        Map<String,Object> response =
                new HashMap<>();

        response.put("status", 200);

        response.put(
                "message",
                "Inventory Value Retrieved Successfully");

        response.put(
                "inventoryValue",
                stockService
                .getInventoryValue());

        return response;
    }
    @GetMapping("/dashboard")
    public Map<String,Object>
    getDashboardSummary() {

        return stockService
                .getDashboardSummary();
    }
}