package com.inventorymanagement.service;

import java.util.List;

import com.inventorymanagement.dto.BuyNowDTO;
import com.inventorymanagement.dto.OrderDTO;
import com.inventorymanagement.dto.OrderResponseDTO;

public interface OrderService {

    // Buy Now
    OrderResponseDTO buyNow(BuyNowDTO dto);

    // Place Order From Cart
    OrderResponseDTO placeOrderFromCart();

    // User Orders
    List<OrderDTO> getMyOrders();

    // Admin
    List<OrderDTO> getAllOrders();

    OrderDTO getOrderById(Integer orderId);

    // Cancel Order
    OrderResponseDTO cancelOrder(Integer orderId);

    // Admin Delete
    void deleteOrder(Integer orderId);
}