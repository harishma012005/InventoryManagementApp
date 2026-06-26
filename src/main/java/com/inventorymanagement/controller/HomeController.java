package com.inventorymanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventorymanagement.dto.HomeNotificationDTO;
import com.inventorymanagement.dto.HomeProductDTO;
import com.inventorymanagement.dto.OfferDTO;
import com.inventorymanagement.dto.WelcomeDTO;
import com.inventorymanagement.service.HomeService;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private HomeService homeService;

    // ================= FEATURED PRODUCTS =================

    @GetMapping("/featured-products")
    public ResponseEntity<List<HomeProductDTO>>
    getFeaturedProducts() {

        return ResponseEntity.ok(
                homeService.getFeaturedProducts());
    }

    // ================= NEW ARRIVALS =================

    @GetMapping("/new-arrivals")
    public ResponseEntity<List<HomeProductDTO>>
    getNewArrivals() {

        return ResponseEntity.ok(
                homeService.getNewArrivals());
    }

    // ================= LOW STOCK =================

    @GetMapping("/low-stock")
    public ResponseEntity<List<HomeProductDTO>>
    getLowStockProducts() {

        return ResponseEntity.ok(
                homeService.getLowStockProducts());
    }

    // ================= RECENT NOTIFICATIONS =================

    @GetMapping("/notifications")
    public ResponseEntity<List<HomeNotificationDTO>>
    getRecentNotifications() {

        return ResponseEntity.ok(
                homeService.getRecentNotifications());
    }

    // ================= OFFERS =================

    @GetMapping("/offers")
    public ResponseEntity<List<OfferDTO>>
    getOffers() {

        return ResponseEntity.ok(
                homeService.getOffers());
    }

    // ================= WELCOME =================

    @GetMapping("/welcome")
    public ResponseEntity<WelcomeDTO>
    getWelcomeMessage() {

        return ResponseEntity.ok(
                homeService.getWelcomeMessage());
    }
}