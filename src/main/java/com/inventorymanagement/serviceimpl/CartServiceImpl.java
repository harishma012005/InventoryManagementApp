package com.inventorymanagement.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.inventorymanagement.dto.AddCartItemDTO;
import com.inventorymanagement.dto.CartDTO;
import com.inventorymanagement.dto.CartItemDTO;
import com.inventorymanagement.dto.CartResponseDTO;
import com.inventorymanagement.entity.Cart;
import com.inventorymanagement.entity.CartItem;
import com.inventorymanagement.entity.Product;
import com.inventorymanagement.entity.User;
import com.inventorymanagement.exception.AlreadyExistsException;
import com.inventorymanagement.exception.ResourceNotFoundException;
import com.inventorymanagement.repository.CartRepository;
import com.inventorymanagement.repository.ProductRepository;
import com.inventorymanagement.repository.UserRepository;
import com.inventorymanagement.service.CartService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    // ================= PRIVATE METHOD =================

    private Cart getCartByUserId(Integer userId) {

        return cartRepository
                .findByUser_UserId(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cart Not Found"));
    }

    // ================= CREATE CART =================

    @Override
    public CartResponseDTO createCart() {

        User user = getLoggedInUser();

        if (cartRepository
                .findByUser_UserId(user.getUserId())
                .isPresent()) {

            throw new AlreadyExistsException(
                    "Cart Already Exists");
        }

        Cart cart = new Cart();
        cart.setUser(user);

        Cart savedCart = cartRepository.save(cart);

        CartResponseDTO response =
                new CartResponseDTO();

        response.setCartId(savedCart.getCartId());
        response.setMessage("Cart Created Successfully");

        return response;
    }
   

    // ================= ADD ITEM TO CART =================

    @Override
    public CartResponseDTO addItemToCart(
            AddCartItemDTO dto) {

    	User user =
    	        getLoggedInUser();

    	Cart cart =
    	        getCartByUserId(
    	                user.getUserId());
        if (dto.getQuantity() <= 0) {

            throw new IllegalArgumentException(
                    "Quantity must be greater than zero");
        }

        Product product = productRepository
                .findById(dto.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product Not Found"));

        CartItem existingItem = cart.getItems()
                .stream()
                .filter(item ->
                        item.getProduct()
                                .getProductId()
                                .equals(product.getProductId()))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {

            existingItem.setQuantity(
                    existingItem.getQuantity()
                            + dto.getQuantity());

        } else {

            CartItem item = new CartItem();

            item.setCart(cart);
            item.setProduct(product);
            item.setQuantity(dto.getQuantity());

            cart.getItems().add(item);
        }

        cartRepository.save(cart);

        CartResponseDTO response =
                new CartResponseDTO();

        response.setCartId(cart.getCartId());
        response.setMessage(
                "Item Added To Cart Successfully");

        return response;
    }
    private User getLoggedInUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email =
                authentication.getName();

        return userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User Not Found"));
    }

    // ================= VIEW CART =================

    @Override
    public CartDTO getCart() {
    User user =
    getLoggedInUser();

Cart cart =
    getCartByUserId(
            user.getUserId());

        CartDTO dto = new CartDTO();

        dto.setCartId(cart.getCartId());

        List<CartItemDTO> itemDTOList =
                cart.getItems()
                        .stream()
                        .map(item -> {

                            CartItemDTO itemDTO =
                                    new CartItemDTO();

                            itemDTO.setCartItemId(
                                    item.getCartItemId());

                            itemDTO.setProductId(
                                    item.getProduct()
                                            .getProductId());

                            itemDTO.setProductName(
                                    item.getProduct()
                                            .getProductName());

                            itemDTO.setQuantity(
                                    item.getQuantity());

                            itemDTO.setPrice(
                                    item.getProduct()
                                            .getPrice());

                            return itemDTO;
                        })
                        .toList();

        dto.setItems(itemDTOList);

        return dto;
    }

    // ================= UPDATE CART ITEM =================

    @Override
    public CartResponseDTO updateCartItem(
            Integer cartItemId,
            Integer quantity) {

    	User user =
    	        getLoggedInUser();

    	Cart cart =
    	        getCartByUserId(
    	                user.getUserId());
        if (quantity <= 0) {

            throw new IllegalArgumentException(
                    "Quantity must be greater than zero");
        }

        CartItem item = cart.getItems()
                .stream()
                .filter(i ->
                        i.getCartItemId()
                                .equals(cartItemId))
                .findFirst()
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cart Item Not Found"));

        item.setQuantity(quantity);

        cartRepository.save(cart);

        CartResponseDTO response =
                new CartResponseDTO();

        response.setCartId(cart.getCartId());
        response.setMessage(
                "Cart Item Updated Successfully");

        return response;
    }

    // ================= REMOVE ITEM =================

    @Override
    public void removeCartItem(
            Integer cartItemId) {

    	User user =
    	        getLoggedInUser();

    	Cart cart =
    	        getCartByUserId(
    	                user.getUserId());

        CartItem item = cart.getItems()
                .stream()
                .filter(i ->
                        i.getCartItemId()
                                .equals(cartItemId))
                .findFirst()
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cart Item Not Found"));

        cart.getItems().remove(item);

        cartRepository.save(cart);
    }

    // ================= CLEAR CART =================

    @Override
    public void clearCart() {
    	User user =
    	        getLoggedInUser();

    	Cart cart =
    	        getCartByUserId(
    	                user.getUserId());

        cart.getItems().clear();

        cartRepository.save(cart);
    }
}