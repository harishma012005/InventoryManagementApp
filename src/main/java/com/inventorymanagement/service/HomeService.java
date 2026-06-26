package com.inventorymanagement.service;

import java.util.List;

import com.inventorymanagement.dto.HomeNotificationDTO;
import com.inventorymanagement.dto.HomeProductDTO;
import com.inventorymanagement.dto.OfferDTO;
import com.inventorymanagement.dto.WelcomeDTO;

public interface HomeService {

    // Featured Products
    List<HomeProductDTO> getFeaturedProducts();

    // New Arrivals
    List<HomeProductDTO> getNewArrivals();

    // Low Stock Products
    List<HomeProductDTO> getLowStockProducts();

    // Recent Notifications
    List<HomeNotificationDTO> getRecentNotifications();

    // Special Offers
    List<OfferDTO> getOffers();

    // Welcome Message
    WelcomeDTO getWelcomeMessage();
}