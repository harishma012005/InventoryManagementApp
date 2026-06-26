package com.inventorymanagement.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.inventorymanagement.dto.CreatePaymentDTO;
import com.inventorymanagement.dto.PaymentDTO;
import com.inventorymanagement.dto.PaymentResponseDTO;
import com.inventorymanagement.entity.Order;

import com.inventorymanagement.entity.Payment;
import com.inventorymanagement.entity.User;
import com.inventorymanagement.exception.ResourceNotFoundException;
import com.inventorymanagement.repository.OrderRepository;
import com.inventorymanagement.repository.PaymentRepository;

import com.inventorymanagement.repository.UserRepository;
import com.inventorymanagement.service.PaymentService;

import jakarta.transaction.Transactional;
import com.inventorymanagement.dto.CreateNotificationDTO;
import com.inventorymanagement.service.NotificationService;
@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserRepository userRepository;

    // ================= LOGGED IN USER =================

    private User getLoggedInUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        return userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User Not Found"));
    }

    // ================= DTO CONVERSION =================

    private PaymentDTO convertToDTO(
            Payment payment) {

        PaymentDTO dto =
                new PaymentDTO();

        dto.setPaymentId(
                payment.getPaymentId());

        dto.setOrderId(
                payment.getOrder()
                        .getOrderId());

        dto.setAmount(
                payment.getAmount());

        dto.setPaymentMethod(
                payment.getPaymentMethod());

        dto.setPaymentStatus(
                payment.getPaymentStatus());

        dto.setTransactionId(
                payment.getTransactionId());

        dto.setPaymentDate(
                payment.getPaymentDate());

        return dto;
    }

    // ================= MAKE PAYMENT =================

    @Override
    public PaymentResponseDTO makePayment(
            CreatePaymentDTO dto) {

        User user =
                getLoggedInUser();

        Order order =
                orderRepository.findById(
                        dto.getOrderId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order Not Found"));

        if (!order.getUser()
                .getUserId()
                .equals(user.getUserId())) {

            throw new RuntimeException(
                    "You Can Only Pay Your Own Order");
        }

        if ("CANCELLED".equals(
                order.getStatus())) {

            throw new RuntimeException(
                    "Cancelled Orders Cannot Be Paid");
        }

        if ("PAID".equals(
                order.getStatus())) {

            throw new RuntimeException(
                    "Order Already Paid");
        }

        List<String> validMethods =
                List.of(
                        "UPI",
                        "CARD",
                        "NET_BANKING",
                        "WALLET",
                        "CASH_ON_DELIVERY"
                );

        if (!validMethods.contains(
                dto.getPaymentMethod())) {

            throw new RuntimeException(
                    "Invalid Payment Method");
        }

        if (paymentRepository.existsByOrder_OrderId(
                order.getOrderId())) {

            throw new RuntimeException(
                    "Payment Already Exists");
        }

        Payment payment =
                new Payment();

        payment.setOrder(order);

        payment.setAmount(
                order.getTotalAmount());

        payment.setPaymentMethod(
                dto.getPaymentMethod());

        payment.setPaymentStatus(
                "SUCCESS");

        payment.setTransactionId(
                UUID.randomUUID().toString());

        payment.setPaymentDate(
                LocalDateTime.now());

        Payment savedPayment =
                paymentRepository.save(
                        payment);

        order.setStatus("PAID");

        orderRepository.save(order);
        
        CreateNotificationDTO notification =
                new CreateNotificationDTO();

        notification.setUserId(
                user.getUserId());

        notification.setTitle(
                "PAYMENT SUCCESSFUL");

        notification.setMessage(
                "Payment received successfully for Order #"
                + order.getOrderId());

        notification.setType(
                "PAYMENT");

        notificationService
                .createNotification(
                        notification);
        PaymentResponseDTO response =
                new PaymentResponseDTO();

        response.setPaymentId(
                savedPayment.getPaymentId());

        response.setMessage(
                "Payment Successful");

        return response;
    }
  
    // ================= GET PAYMENT BY ID =================

    @Override
    public PaymentDTO getPaymentById(
            Integer paymentId) {

        User user =
                getLoggedInUser();

        Payment payment =
                paymentRepository.findById(
                        paymentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Payment Not Found"));

        if (!payment.getOrder()
                .getUser()
                .getUserId()
                .equals(user.getUserId())) {

            throw new RuntimeException(
                    "Access Denied");
        }

        return convertToDTO(payment);
    }

    // ================= GET PAYMENT BY ORDER =================

    @Override
    public PaymentDTO getPaymentByOrderId(
            Integer orderId) {

        User user =
                getLoggedInUser();

        Payment payment =
                paymentRepository
                        .findByOrder_OrderId(
                                orderId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Payment Not Found"));

        if (!payment.getOrder()
                .getUser()
                .getUserId()
                .equals(user.getUserId())) {

            throw new RuntimeException(
                    "Access Denied");
        }

        return convertToDTO(payment);
    }
    // ================= MY PAYMENTS =================

    @Override
    public List<PaymentDTO> getMyPayments() {

        User user =
                getLoggedInUser();

        return paymentRepository.findAll()
                .stream()
                .filter(payment ->
                        payment.getOrder()
                                .getUser()
                                .getUserId()
                                .equals(user.getUserId()))
                .map(this::convertToDTO)
                .toList();
    }

    // ================= ALL PAYMENTS =================

    @Override
    public List<PaymentDTO> getAllPayments() {

        return paymentRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    // ================= PAYMENTS BY STATUS =================

    @Override
    public List<PaymentDTO> getPaymentsByStatus(
            String paymentStatus) {

        return paymentRepository
                .findByPaymentStatus(paymentStatus)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    // ================= REFUND PAYMENT =================

 
    // ================= DELETE PAYMENT =================

    @Override
    public void deletePayment(
            Integer paymentId) {

        Payment payment =
                paymentRepository.findById(
                        paymentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Payment Not Found"));

        if ("SUCCESS".equals(
                payment.getPaymentStatus())) {

            throw new RuntimeException(
                    "Successful Payments Cannot Be Deleted");
        }

        paymentRepository.delete(
                payment);
    }
}