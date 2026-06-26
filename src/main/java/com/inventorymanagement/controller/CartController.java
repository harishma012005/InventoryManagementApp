package com.inventorymanagement.controller;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.inventorymanagement.dto.*;
import com.inventorymanagement.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/create")
    public ResponseEntity<?> createCart() {

        return ResponseEntity.ok(
                cartService.createCart());
    }
    @PostMapping("/add-item")
    public ResponseEntity<?> addItem(
            @RequestBody AddCartItemDTO dto) {

        return ResponseEntity.ok(
                cartService.addItemToCart(dto));
    }
    @GetMapping("/my-cart")
    public ResponseEntity<?> getCart() {

        return ResponseEntity.ok(
                cartService.getCart());
    }

    @PutMapping("/update-item/{cartItemId}")
    public ResponseEntity<?> updateItem(
            @PathVariable Integer cartItemId,
            @RequestParam Integer quantity) {

        return ResponseEntity.ok(
                cartService.updateCartItem(
                        cartItemId,
                        quantity));
    }

    @DeleteMapping("/remove-item/{cartItemId}")
    public ResponseEntity<?> removeItem(
            @PathVariable Integer cartItemId) {

        cartService.removeCartItem(cartItemId);

        return ResponseEntity.ok(
                "Item Removed Successfully");
    }
    @DeleteMapping("/clear")
    public ResponseEntity<?> clearCart() {

        cartService.clearCart();

        return ResponseEntity.ok(
                "Cart Cleared Successfully");
    }
}
