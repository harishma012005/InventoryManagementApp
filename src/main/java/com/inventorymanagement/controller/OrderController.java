package com.inventorymanagement.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.inventorymanagement.dto.BuyNowDTO;
import com.inventorymanagement.dto.OrderDTO;
import com.inventorymanagement.dto.OrderResponseDTO;
import com.inventorymanagement.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // ================= BUY NOW =================

    @PostMapping("/buy-now")
    public ResponseEntity<OrderResponseDTO> buyNow(
            @RequestBody BuyNowDTO dto) {

        return ResponseEntity.ok(
                orderService.buyNow(dto));
    }

    // ================= ORDER FROM CART =================

    @PostMapping("/place-from-cart")
    public ResponseEntity<OrderResponseDTO> placeOrderFromCart() {

        return ResponseEntity.ok(
                orderService.placeOrderFromCart());
    }

    // ================= USER ORDERS =================

    @GetMapping("/my-orders")
    public ResponseEntity<List<OrderDTO>> getMyOrders() {

        return ResponseEntity.ok(
                orderService.getMyOrders());
    }

    // ================= ADMIN =================

    @GetMapping("/all")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {

        return ResponseEntity.ok(
                orderService.getAllOrders());
    }

    @GetMapping("/get/{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(
            @PathVariable Integer orderId) {

        return ResponseEntity.ok(
                orderService.getOrderById(orderId));
    }

    // ================= CANCEL =================

    @PutMapping("/cancel/{orderId}")
    public ResponseEntity<OrderResponseDTO> cancelOrder(
            @PathVariable Integer orderId) {

        return ResponseEntity.ok(
                orderService.cancelOrder(orderId));
    }

    // ================= DELETE =================

    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<Map<String,Object>> deleteOrder(
            @PathVariable Integer orderId) {

        orderService.deleteOrder(orderId);

        Map<String,Object> response =
                new HashMap<>();

        response.put("status",200);
        response.put("message",
                "Order Deleted Successfully");

        return ResponseEntity.ok(response);
    }
}