package com.inventorymanagement.serviceimpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.inventorymanagement.dto.CreateNotificationDTO;
import com.inventorymanagement.entity.User;
import com.inventorymanagement.repository.UserRepository;
import com.inventorymanagement.service.NotificationService;
import com.inventorymanagement.dto.CreatePurchaseDTO;
import com.inventorymanagement.dto.CreatePurchaseItemDTO;
import com.inventorymanagement.dto.PurchaseDTO;
import com.inventorymanagement.dto.PurchaseItemDTO;
import com.inventorymanagement.dto.PurchaseResponseDTO;
import com.inventorymanagement.entity.Product;
import com.inventorymanagement.entity.Purchase;
import com.inventorymanagement.entity.PurchaseItem;
import com.inventorymanagement.entity.Supplier;
import com.inventorymanagement.exception.AlreadyExistsException;
import com.inventorymanagement.exception.ResourceNotFoundException;
import com.inventorymanagement.repository.ProductRepository;
import com.inventorymanagement.repository.PurchaseRepository;
import com.inventorymanagement.repository.SupplierRepository;
import com.inventorymanagement.service.PurchaseService;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserRepository userRepository;
    private PurchaseDTO convertToDTO(Purchase purchase) {

        PurchaseDTO dto = new PurchaseDTO();

        dto.setPurchaseId(purchase.getPurchaseId());
        dto.setSupplierName(purchase.getSupplier().getSupplierName());
        dto.setTotalAmount(purchase.getTotalAmount());

        List<PurchaseItemDTO> itemDTOList = new ArrayList<>();

        for (PurchaseItem item : purchase.getItems()) {

            PurchaseItemDTO itemDTO = new PurchaseItemDTO();

            itemDTO.setProductName(item.getProduct().getProductName());
            itemDTO.setQuantity(item.getQuantity());
            itemDTO.setPrice(item.getPrice());

            itemDTO.setTotalPrice(
                    item.getPrice().multiply(
                            BigDecimal.valueOf(item.getQuantity())
                    )
            );

            itemDTOList.add(itemDTO);
        }

        dto.setItems(itemDTOList);

        return dto;
    }
    private User getAdminUser() {

        return userRepository.findByRole("ADMIN")
                .stream()
                .findFirst()
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Admin Not Found"));
    }
    // ================= CREATE PURCHASE =================
    @Override
    public PurchaseResponseDTO createPurchase(CreatePurchaseDTO dto) {

        // 1. Validate Supplier
        Supplier supplier = supplierRepository.findById(dto.getSupplierId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Supplier Not Found"));

        if (dto.getItems() == null || dto.getItems().isEmpty()) {
            throw new IllegalArgumentException("Purchase items cannot be empty");
        }

        Purchase purchase = new Purchase();
        purchase.setSupplier(supplier);

        List<PurchaseItem> itemList = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        // 2. Process Items
        for (CreatePurchaseItemDTO itemDTO : dto.getItems()) {

            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Product Not Found"));

            if (itemDTO.getQuantity() <= 0) {
                throw new IllegalArgumentException("Quantity must be greater than 0");
            }

            // duplicate check inside request
            boolean duplicate = itemList.stream()
                    .anyMatch(i -> i.getProduct()
                            .getProductId()
                            .equals(product.getProductId()));

            if (duplicate) {
                throw new AlreadyExistsException("Duplicate product not allowed");
            }

            // ================= STOCK IN LOGIC =================
            product.setQuantity(product.getQuantity() + itemDTO.getQuantity());
            productRepository.save(product);

            // Create Purchase Item
            PurchaseItem item = new PurchaseItem();
            item.setPurchase(purchase);
            item.setProduct(product);
            item.setQuantity(itemDTO.getQuantity());
            item.setPrice(product.getPrice());

            itemList.add(item);

            totalAmount = totalAmount.add(
                    product.getPrice().multiply(
                            BigDecimal.valueOf(itemDTO.getQuantity())
                    )
            );
        }

        // 3. Save Purchase
        purchase.setItems(itemList);
        purchase.setTotalAmount(totalAmount);

        Purchase saved = purchaseRepository.save(purchase);
        User admin = getAdminUser();

        CreateNotificationDTO notification =
                new CreateNotificationDTO();

        notification.setUserId(
                admin.getUserId());

        notification.setTitle(
                "PURCHASE CREATED");

        notification.setMessage(
                "Purchase #"
                + saved.getPurchaseId()
                + " created successfully. Stock has been updated.");

        notification.setType(
                "PURCHASE");

        notificationService
                .createNotification(
                        notification);

        // 4. RESPONSE DTO (ONLY SIMPLE OUTPUT)
        PurchaseResponseDTO response = new PurchaseResponseDTO();
        response.setPurchaseId(saved.getPurchaseId());
        response.setMessage("Purchase created successfully");

        return response;
    }

    // ================= GET ALL PURCHASES =================
    @Override
    public List<PurchaseDTO> getAllPurchases() {

        return purchaseRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    // ================= GET BY ID =================
    @Override
    public PurchaseDTO getPurchaseById(Integer id) {

        Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Purchase Not Found"));

        return convertToDTO(purchase);
    }
    
    // ================= DELETE =================
    @Override
    public void deletePurchase(Integer id) {

        Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Purchase Not Found"));

        purchaseRepository.delete(purchase);
    }
}