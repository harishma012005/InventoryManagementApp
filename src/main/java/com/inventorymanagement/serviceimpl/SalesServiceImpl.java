package com.inventorymanagement.serviceimpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventorymanagement.dto.CreateSalesDTO;
import com.inventorymanagement.dto.CreateSalesItemDTO;
import com.inventorymanagement.dto.SalesItemDTO;
import com.inventorymanagement.dto.SalesResponseDTO;
import com.inventorymanagement.entity.Product;
import com.inventorymanagement.entity.Sales;
import com.inventorymanagement.entity.SalesItem;
import com.inventorymanagement.entity.User;
import com.inventorymanagement.exception.AlreadyExistsException;
import com.inventorymanagement.exception.ResourceNotFoundException;
import com.inventorymanagement.repository.ProductRepository;
import com.inventorymanagement.repository.SalesRepository;
import com.inventorymanagement.repository.UserRepository;
import com.inventorymanagement.service.SalesService;

@Service
public class SalesServiceImpl implements SalesService {

    @Autowired
    private SalesRepository salesRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;
    private SalesResponseDTO convertToResponseDTO(Sales sales) {

        List<SalesItemDTO> items = new ArrayList<>();

        for (SalesItem item : sales.getItems()) {

            SalesItemDTO dto = new SalesItemDTO();

            dto.setProductName(item.getProduct().getProductName());
            dto.setQuantity(item.getQuantity());
            dto.setPrice(item.getPrice());

            dto.setTotalPrice(
                    item.getPrice().multiply(
                            BigDecimal.valueOf(item.getQuantity())
                    )
            );

            items.add(dto);
        }

        SalesResponseDTO response = new SalesResponseDTO();

        response.setSalesId(sales.getSalesId());
        response.setUserName(sales.getUser().getFullName());
        response.setTotalAmount(sales.getTotalAmount());
        response.setItems(items);
        response.setSalesDate(sales.getSalesDate());

        return response;
    }

    // ================= CREATE SALES =================
    @Override
    public SalesResponseDTO createSales(CreateSalesDTO dto) {

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

        if (!"USER".equalsIgnoreCase(user.getRole())) {
            throw new AlreadyExistsException("Only USER can perform sales");
        }

        Sales sales = new Sales();
        sales.setUser(user);

        List<SalesItem> items = new ArrayList<>();
        List<SalesItemDTO> responseItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (CreateSalesItemDTO itemDTO : dto.getItems()) {

            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));

            if (itemDTO.getQuantity() <= 0) {
                throw new IllegalArgumentException("Quantity must be greater than 0");
            }

            if (product.getQuantity() < itemDTO.getQuantity()) {
                throw new IllegalArgumentException("Insufficient stock for product: " + product.getProductName());
            }

            product.setQuantity(product.getQuantity() - itemDTO.getQuantity());
            productRepository.save(product);

            SalesItem item = new SalesItem();
            item.setSales(sales);
            item.setProduct(product);
            item.setQuantity(itemDTO.getQuantity());
            item.setPrice(product.getPrice());

            items.add(item);

            SalesItemDTO itemDTOResp = new SalesItemDTO();
            itemDTOResp.setProductName(product.getProductName());
            itemDTOResp.setQuantity(itemDTO.getQuantity());
            itemDTOResp.setPrice(product.getPrice());
            itemDTOResp.setTotalPrice(
                    product.getPrice().multiply(BigDecimal.valueOf(itemDTO.getQuantity()))
            );

            responseItems.add(itemDTOResp);

            totalAmount = totalAmount.add(
                    product.getPrice().multiply(BigDecimal.valueOf(itemDTO.getQuantity()))
            );
        }

        sales.setItems(items);
        sales.setTotalAmount(totalAmount);

        Sales saved = salesRepository.save(sales);

        SalesResponseDTO response = new SalesResponseDTO();
        response.setSalesId(saved.getSalesId());
        response.setUserName(user.getFullName());
        response.setTotalAmount(saved.getTotalAmount());
        response.setItems(responseItems);

        // ✅ FIX FOR DATE
        response.setSalesDate(saved.getSalesDate());

        return response;
    }

    // ================= GET ALL SALES =================
    @Override
    public List<SalesResponseDTO> getAllSales() {

        List<Sales> list = salesRepository.findAll();
        List<SalesResponseDTO> responseList = new ArrayList<>();

        for (Sales sales : list) {

            List<SalesItemDTO> items = new ArrayList<>();

            for (SalesItem item : sales.getItems()) {

                SalesItemDTO dto = new SalesItemDTO();
                dto.setProductName(item.getProduct().getProductName());
                dto.setQuantity(item.getQuantity());
                dto.setPrice(item.getPrice());
                dto.setTotalPrice(
                        item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))
                );

                items.add(dto);
            }

            SalesResponseDTO response = new SalesResponseDTO();
            response.setSalesId(sales.getSalesId());
            response.setUserName(sales.getUser().getFullName());
            response.setTotalAmount(sales.getTotalAmount());
            response.setItems(items);

            // ✅ DATE FIX
            response.setSalesDate(sales.getSalesDate());

            responseList.add(response);
        }

        return responseList;
    }

    // ================= GET BY ID =================
    @Override
    public SalesResponseDTO getSalesById(Integer id) {

        Sales sales = salesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sales Not Found"));

        List<SalesItemDTO> items = new ArrayList<>();

        for (SalesItem item : sales.getItems()) {

            SalesItemDTO dto = new SalesItemDTO();
            dto.setProductName(item.getProduct().getProductName());
            dto.setQuantity(item.getQuantity());
            dto.setPrice(item.getPrice());
            dto.setTotalPrice(
                    item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))
            );

            items.add(dto);
        }

        SalesResponseDTO response = new SalesResponseDTO();
        response.setSalesId(sales.getSalesId());
        response.setUserName(sales.getUser().getFullName());
        response.setTotalAmount(sales.getTotalAmount());
        response.setItems(items);

        // ✅ DATE FIX
        response.setSalesDate(sales.getSalesDate());

        return response;
    }
    @Override
    public List<SalesResponseDTO> getMySales(Integer userId) {

        List<Sales> salesList =
                salesRepository.findByUser_UserId(userId);

        return salesList.stream()
                .map(this::convertToResponseDTO)
                .toList();
    }

    // ================= DELETE =================
    @Override
    public void deleteSales(Integer id) {

        Sales sales = salesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sales Not Found"));

        salesRepository.delete(sales);
    }
}