package com.inventorymanagement.serviceimpl;

import java.math.BigDecimal;
import java.io.ByteArrayOutputStream;

import com.itextpdf.text.DocumentException;
import com.inventorymanagement.dto.InvoiceDTO;
import com.inventorymanagement.dto.InvoiceItemDTO;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.time.LocalDate;
import com.inventorymanagement.dto.UpdateOrderStatusDTO;
import com.inventorymanagement.dto.OrderStatusResponseDTO;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.inventorymanagement.dto.BuyNowDTO;
import com.inventorymanagement.dto.OrderDTO;
import com.inventorymanagement.dto.OrderItemDTO;
import com.inventorymanagement.dto.OrderResponseDTO;
import com.inventorymanagement.entity.Cart;
import com.inventorymanagement.entity.CartItem;
import com.inventorymanagement.entity.Order;
import com.inventorymanagement.entity.OrderItem;
import com.inventorymanagement.entity.Product;
import com.inventorymanagement.entity.User;
import com.inventorymanagement.exception.ResourceNotFoundException;
import com.inventorymanagement.repository.CartRepository;
import com.inventorymanagement.repository.OrderItemRepository;
import com.inventorymanagement.repository.OrderRepository;
import com.inventorymanagement.repository.ProductRepository;
import com.inventorymanagement.repository.UserRepository;
import com.inventorymanagement.service.OrderService;

import jakarta.transaction.Transactional;
import com.inventorymanagement.dto.CreateNotificationDTO;

import com.inventorymanagement.service.NotificationService;
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

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
 // ================= INVOICE DTO CONVERSION =================

    private InvoiceDTO convertToInvoiceDTO(
            Order order) {

        InvoiceDTO dto = new InvoiceDTO();

        dto.setOrderId(order.getOrderId());
        dto.setCustomerName(order.getUser().getFullName());
        dto.setEmail(order.getUser().getEmail());
        dto.setPhone(order.getUser().getPhone());
        dto.setOrderDate(order.getOrderDate());
        dto.setOrderStatus(order.getStatus());
        dto.setOrderType(order.getOrderType());
        dto.setTotalAmount(order.getTotalAmount());

        List<InvoiceItemDTO> items =
                order.getItems()
                        .stream()
                        .map(item -> {

                            InvoiceItemDTO itemDTO =
                                    new InvoiceItemDTO();

                            itemDTO.setProductId(
                                    item.getProduct().getProductId());

                            itemDTO.setProductName(
                                    item.getProduct().getProductName());

                            itemDTO.setQuantity(
                                    item.getQuantity());

                            itemDTO.setPrice(
                                    item.getPrice());

                            itemDTO.setSubtotal(
                                    item.getSubtotal());

                            return itemDTO;
                        })
                        .toList();

        dto.setItems(items);

        return dto;
    }
    // ================= DTO CONVERSION =================

    private OrderDTO convertToDTO(Order order) {

        OrderDTO dto = new OrderDTO();

        dto.setOrderId(order.getOrderId());
        dto.setUserId(order.getUser().getUserId());

        dto.setOrderDate(order.getOrderDate());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setStatus(order.getStatus());
        dto.setOrderType(order.getOrderType());

        List<OrderItemDTO> itemDTOs =
                order.getItems()
                        .stream()
                        .map(item -> {

                            OrderItemDTO itemDTO =
                                    new OrderItemDTO();

                            itemDTO.setOrderItemId(
                                    item.getOrderItemId());

                            itemDTO.setProductId(
                                    item.getProduct()
                                            .getProductId());

                            itemDTO.setProductName(
                                    item.getProduct()
                                            .getProductName());

                            itemDTO.setQuantity(
                                    item.getQuantity());

                            itemDTO.setPrice(
                                    item.getPrice());

                            itemDTO.setSubtotal(
                                    item.getSubtotal());

                            return itemDTO;
                        })
                        .toList();

        dto.setItems(itemDTOs);

        return dto;
    }
    
    @Override
    public OrderStatusResponseDTO updateOrderStatus(
            Integer orderId,
            UpdateOrderStatusDTO dto) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order Not Found"));

        order.setStatus(dto.getStatus());

        orderRepository.save(order);

        // Notification to customer
        CreateNotificationDTO notification =
                new CreateNotificationDTO();

        notification.setUserId(
                order.getUser().getUserId());

        notification.setTitle(
                "ORDER STATUS UPDATED");

        notification.setMessage(
                "Your Order #"
                + order.getOrderId()
                + " status has been updated to "
                + dto.getStatus());

        notification.setType("ORDER");

        notificationService.createNotification(notification);

        OrderStatusResponseDTO response =
                new OrderStatusResponseDTO();

        response.setOrderId(order.getOrderId());
        response.setStatus(order.getStatus());
        response.setMessage("Order Status Updated Successfully");

        return response;
    }

    // ================= BUY NOW =================

    @Override
    public OrderResponseDTO buyNow(BuyNowDTO dto) {

        User user = getLoggedInUser();

        Product product =
                productRepository.findById(
                        dto.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product Not Found"));

        if(product.getQuantity() <
                dto.getQuantity()) {

            throw new RuntimeException(
                    "Insufficient Stock");
        }

        Order order = new Order();

        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PLACED");
        order.setOrderType("BUY_NOW");

        BigDecimal subtotal =
                product.getPrice()
                        .multiply(
                                BigDecimal.valueOf(
                                        dto.getQuantity()));

        order.setTotalAmount(subtotal);

        Order savedOrder =
                orderRepository.save(order);

        OrderItem item =
                new OrderItem();

        item.setOrder(savedOrder);
        item.setProduct(product);
        item.setQuantity(dto.getQuantity());
        item.setPrice(product.getPrice());
        item.setSubtotal(subtotal);

        orderItemRepository.save(item);

        product.setQuantity(
                product.getQuantity()
                        - dto.getQuantity());

        productRepository.save(product);
        if(product.getQuantity() <= 10) {

            notificationService
                    .createLowStockNotification(
                            product.getProductId());
        }
        CreateNotificationDTO notification =
                new CreateNotificationDTO();

        notification.setUserId(
                user.getUserId());

        notification.setTitle(
                "ORDER PLACED");

        notification.setMessage(
                "Your Order #"
                + savedOrder.getOrderId()
                + " has been placed successfully.");

        notification.setType(
                "ORDER");

        notificationService
                .createNotification(notification);

        OrderResponseDTO response =
                new OrderResponseDTO();

        response.setOrderId(
                savedOrder.getOrderId());

        response.setMessage(
                "Order Placed Successfully");

        return response;
    }
    @Override
    public List<OrderDTO> filterOrdersByDate(
            LocalDate from,
            LocalDate to) {

        return orderRepository
                .findByOrderDateBetween(
                        from.atStartOfDay(),
                        to.atTime(23, 59, 59))
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    // ================= ORDER FROM CART =================

    @Override
    public OrderResponseDTO placeOrderFromCart() {

        User user = getLoggedInUser();

        Cart cart =
                cartRepository
                        .findByUser_UserId(
                                user.getUserId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Cart Not Found"));

        if(cart.getItems().isEmpty()) {

            throw new RuntimeException(
                    "Cart Is Empty");
        }

        Order order =
                new Order();

        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PLACED");
        order.setOrderType("CART");

        Order savedOrder =
                orderRepository.save(order);

        BigDecimal total =
                BigDecimal.ZERO;

        for(CartItem cartItem :
                cart.getItems()) {

            Product product =
                    cartItem.getProduct();

            if(product.getQuantity() <
                    cartItem.getQuantity()) {

                throw new RuntimeException(
                        "Insufficient Stock");
            }

            BigDecimal subtotal =
                    product.getPrice()
                            .multiply(
                                    BigDecimal.valueOf(
                                            cartItem.getQuantity()));

            OrderItem orderItem =
                    new OrderItem();

            orderItem.setOrder(savedOrder);
            orderItem.setProduct(product);
            orderItem.setQuantity(
                    cartItem.getQuantity());

            orderItem.setPrice(
                    product.getPrice());

            orderItem.setSubtotal(
                    subtotal);

            orderItemRepository.save(
                    orderItem);

            total = total.add(
                    subtotal);

            product.setQuantity(
                    product.getQuantity()
                            - cartItem.getQuantity());

            productRepository.save(product);
            if(product.getQuantity() <= 10) {

                notificationService
                        .createLowStockNotification(
                                product.getProductId());
            }
        }

        savedOrder.setTotalAmount(total);

        orderRepository.save(savedOrder);

        cart.getItems().clear();

        cartRepository.save(cart);
        CreateNotificationDTO notification =
                new CreateNotificationDTO();

        notification.setUserId(
                user.getUserId());

        notification.setTitle(
                "ORDER PLACED");

        notification.setMessage(
                "Your Order #"
                + savedOrder.getOrderId()
                + " has been placed successfully.");

        notification.setType(
                "ORDER");

        notificationService
                .createNotification(notification);

        OrderResponseDTO response =
                new OrderResponseDTO();

        response.setOrderId(
                savedOrder.getOrderId());

        response.setMessage(
                "Order Placed From Cart Successfully");

        return response;
    }

    // ================= MY ORDERS =================

    @Override
    public List<OrderDTO> getMyOrders() {

        User user =
                getLoggedInUser();

        return orderRepository
                .findByUser_UserId(
                        user.getUserId())
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    // ================= ALL ORDERS =================

    @Override
    public List<OrderDTO> getAllOrders() {

        return orderRepository
                .findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    // ================= ORDER BY ID =================
    
    @Override
    public OrderDTO getOrderById(
            Integer orderId) {

        Order order =
                orderRepository.findById(
                        orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order Not Found"));

        return convertToDTO(order);
    }

    // ================= CANCEL ORDER =================
    @Override
    @Transactional
    public OrderResponseDTO cancelOrder(
            Integer orderId) {

        Order order =
                orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order Not Found"));

        if("CANCELLED".equals(
                order.getStatus())) {

            throw new RuntimeException(
                    "Order Already Cancelled");
        }

        for(OrderItem item :
                order.getItems()) {

            Product product =
                    item.getProduct();

            product.setQuantity(
                    product.getQuantity()
                            + item.getQuantity());

            productRepository.save(product);
            
        }

        order.setStatus("CANCELLED");

        orderRepository.save(order);
        
        CreateNotificationDTO notification =
                new CreateNotificationDTO();

        notification.setUserId(
                order.getUser().getUserId());

        notification.setTitle(
                "ORDER CANCELLED");

        notification.setMessage(
                "Your Order #"
                + order.getOrderId()
                + " has been cancelled successfully.");

        notification.setType(
                "ORDER");

        notificationService
                .createNotification(notification);
        OrderResponseDTO response =
                new OrderResponseDTO();

        response.setOrderId(orderId);
        response.setMessage(
                "Order Cancelled Successfully");

        return response;
    }
   
    // ================= DELETE ORDER =================

    @Override
    public void deleteOrder(
            Integer orderId) {

        Order order =
                orderRepository.findById(
                        orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order Not Found"));

        orderRepository.delete(order);
    }
    
   
    @Override
    public byte[] downloadInvoice(Integer orderId) {

        Order order =
                orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order Not Found"));

        InvoiceDTO invoice =
                convertToInvoiceDTO(order);

        ByteArrayOutputStream out =
                new ByteArrayOutputStream();

        Document document =
                new Document();

        try {
            PdfWriter.getInstance(document, out);
        } catch (DocumentException e) {
            throw new RuntimeException("Error generating invoice PDF", e);
        }
       try
       {
        document.open();

        // ================= TITLE =================

        Paragraph title =
                new Paragraph(
                        "INVENTORY MANAGEMENT SYSTEM");

        title.setAlignment(
                Paragraph.ALIGN_CENTER);

        document.add(title);

        Paragraph invoiceTitle =
                new Paragraph(
                        "INVOICE");

        invoiceTitle.setAlignment(
                Paragraph.ALIGN_CENTER);

        document.add(invoiceTitle);

        document.add(
                new Paragraph(" "));

        // ================= CUSTOMER DETAILS =================

        document.add(
                new Paragraph(
                        "Order ID : "
                                + invoice.getOrderId()));

        document.add(
                new Paragraph(
                        "Customer : "
                                + invoice.getCustomerName()));

        document.add(
                new Paragraph(
                        "Email : "
                                + invoice.getEmail()));

        document.add(
                new Paragraph(
                        "Phone : "
                                + invoice.getPhone()));

        document.add(
                new Paragraph(
                        "Order Date : "
                                + invoice.getOrderDate()));

        document.add(
                new Paragraph(
                        "Status : "
                                + invoice.getOrderStatus()));

        document.add(
                new Paragraph(" "));

        // ================= PRODUCT TABLE =================

        PdfPTable table =
                new PdfPTable(5);

        table.setWidthPercentage(100);

        table.addCell(
                new PdfPCell(
                        new Phrase("Product")));

        table.addCell(
                new PdfPCell(
                        new Phrase("Quantity")));

        table.addCell(
                new PdfPCell(
                        new Phrase("Price")));

        table.addCell(
                new PdfPCell(
                        new Phrase("Subtotal")));

        table.addCell(
                new PdfPCell(
                        new Phrase("Product ID")));

        for(InvoiceItemDTO item :
                invoice.getItems()) {

            table.addCell(
                    item.getProductName());

            table.addCell(
                    String.valueOf(
                            item.getQuantity()));

            table.addCell(
                    item.getPrice().toString());

            table.addCell(
                    item.getSubtotal().toString());

            table.addCell(
                    String.valueOf(
                            item.getProductId()));
        }

        document.add(table);

        document.add(
                new Paragraph(" "));

        // ================= GRAND TOTAL =================

        Paragraph total =
                new Paragraph(
                        "Grand Total : Rs. "
                                + invoice.getTotalAmount());

        total.setAlignment(
                Paragraph.ALIGN_RIGHT);

        document.add(total);

        document.add(
                new Paragraph(" "));

        // ================= FOOTER =================

        Paragraph footer =
                new Paragraph(
                        "Thank You For Shopping With Inventory Management System!");

        footer.setAlignment(
                Paragraph.ALIGN_CENTER);

        document.add(footer);

        document.close();
       }
       catch (DocumentException e) {

    	    throw new RuntimeException(
    	            "Error while generating invoice",
    	            e);
    	}
        return out.toByteArray();
    }
}