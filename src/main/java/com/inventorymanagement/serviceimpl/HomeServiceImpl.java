package com.inventorymanagement.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.inventorymanagement.dto.HomeNotificationDTO;
import com.inventorymanagement.dto.HomeProductDTO;
import com.inventorymanagement.dto.OfferDTO;
import com.inventorymanagement.dto.WelcomeDTO;
import com.inventorymanagement.entity.Notification;
import com.inventorymanagement.entity.Product;
import com.inventorymanagement.entity.User;
import com.inventorymanagement.exception.ResourceNotFoundException;
import com.inventorymanagement.repository.NotificationRepository;
import com.inventorymanagement.repository.ProductRepository;
import com.inventorymanagement.repository.UserRepository;
import com.inventorymanagement.service.HomeService;

@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    // ================= LOGGED IN USER =================

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

    // ================= PRODUCT DTO =================

    private HomeProductDTO convertProductDTO(
            Product product) {

        HomeProductDTO dto =
                new HomeProductDTO();

        dto.setProductId(
                product.getProductId());

        dto.setProductName(
                product.getProductName());

        dto.setPrice(
                product.getPrice());

        dto.setQuantity(
                product.getQuantity());

        return dto;
    }

    // ================= NOTIFICATION DTO =================

    private HomeNotificationDTO convertNotificationDTO(
            Notification notification) {

        HomeNotificationDTO dto =
                new HomeNotificationDTO();

        dto.setTitle(
                notification.getTitle());

        dto.setMessage(
                notification.getMessage());

        dto.setType(
                notification.getType());

        return dto;
    }

    // ================= FEATURED PRODUCTS =================

    @Override
    public List<HomeProductDTO> getFeaturedProducts() {

        return productRepository.findAll()
                .stream()
                .limit(10)
                .map(this::convertProductDTO)
                .toList();
    }

    // ================= NEW ARRIVALS =================

    @Override
    public List<HomeProductDTO> getNewArrivals() {

        return productRepository.findAll()
                .stream()
                .sorted((p1, p2) ->
                        p2.getProductId()
                                .compareTo(
                                        p1.getProductId()))
                .limit(10)
                .map(this::convertProductDTO)
                .toList();
    }

    // ================= LOW STOCK PRODUCTS =================

    @Override
    public List<HomeProductDTO> getLowStockProducts() {

        return productRepository.findAll()
                .stream()
                .filter(product ->
                        product.getQuantity() <= 5)
                .map(this::convertProductDTO)
                .toList();
    }

    // ================= RECENT NOTIFICATIONS =================

    @Override
    public List<HomeNotificationDTO>
    getRecentNotifications() {

        User user =
                getLoggedInUser();

        return notificationRepository
                .findByUser_UserId(
                        user.getUserId())
                .stream()
                .limit(10)
                .map(this::convertNotificationDTO)
                .toList();
    }

    // ================= OFFERS =================

    @Override
    public List<OfferDTO> getOffers() {

        OfferDTO offer1 =
                new OfferDTO();

        offer1.setTitle(
                "Summer Sale");

        offer1.setDescription(
                "Get 20% Discount On Selected Products");

        OfferDTO offer2 =
                new OfferDTO();

        offer2.setTitle(
                "Bulk Purchase Offer");

        offer2.setDescription(
                "Buy More Than 10 Units And Get Extra Discount");

        return List.of(
                offer1,
                offer2);
    }

    // ================= WELCOME =================

    @Override
    public WelcomeDTO getWelcomeMessage() {

        User user =
                getLoggedInUser();

        WelcomeDTO dto =
                new WelcomeDTO();

        dto.setFullName(
                user.getFullName());

        dto.setRole(
                user.getRole());

        return dto;
    }
}