package com.inventorymanagement.service;

import com.inventorymanagement.dto.*;

public interface CartService {

	public CartResponseDTO createCart();
	

	CartDTO getCart();

	CartResponseDTO addItemToCart(
	        AddCartItemDTO dto);

	CartResponseDTO updateCartItem(
	        Integer cartItemId,
	        Integer quantity);

	void removeCartItem(
	        Integer cartItemId);

	void clearCart();

  
}