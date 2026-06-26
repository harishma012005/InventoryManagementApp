package com.inventorymanagement.serviceimpl;

import java.time.LocalDateTime;
import com.inventorymanagement.dto.CreateNotificationDTO;
import com.inventorymanagement.service.NotificationService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.inventorymanagement.dto.AdminRefundActionDTO;
import com.inventorymanagement.dto.CreateRefundDTO;
import com.inventorymanagement.dto.RefundDTO;
import com.inventorymanagement.dto.RefundResponseDTO;
import com.inventorymanagement.entity.Order;
import com.inventorymanagement.entity.OrderItem;
import com.inventorymanagement.entity.Payment;
import com.inventorymanagement.entity.Product;
import com.inventorymanagement.entity.Refund;
import com.inventorymanagement.entity.User;
import com.inventorymanagement.exception.ResourceNotFoundException;
import com.inventorymanagement.repository.OrderRepository;
import com.inventorymanagement.repository.PaymentRepository;
import com.inventorymanagement.repository.ProductRepository;
import com.inventorymanagement.repository.RefundRepository;
import com.inventorymanagement.repository.UserRepository;
import com.inventorymanagement.service.RefundService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RefundServiceImpl implements RefundService {


@Autowired
private RefundRepository refundRepository;

@Autowired
private PaymentRepository paymentRepository;

@Autowired
private OrderRepository orderRepository;

@Autowired
private ProductRepository productRepository;

@Autowired
private UserRepository userRepository;
@Autowired
private NotificationService notificationService;

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

// ================= DTO CONVERSION =================

private RefundDTO convertToDTO(
        Refund refund) {

    RefundDTO dto =
            new RefundDTO();

    dto.setRefundId(
            refund.getRefundId());

    dto.setPaymentId(
            refund.getPayment()
                    .getPaymentId());

    dto.setRefundAmount(
            refund.getRefundAmount());

    dto.setReason(
            refund.getReason());

    dto.setAdminRemarks(
            refund.getAdminRemarks());

    dto.setRefundStatus(
            refund.getRefundStatus());

    dto.setRefundDate(
            refund.getRefundDate());

    dto.setApprovedDate(
            refund.getApprovedDate());

    return dto;
}

// ================= REQUEST REFUND =================

@Override
public RefundResponseDTO requestRefund(
        CreateRefundDTO dto) {

    User user =
            getLoggedInUser();

    Payment payment =
            paymentRepository.findById(
                    dto.getPaymentId())
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Payment Not Found"));

    if (!payment.getOrder()
            .getUser()
            .getUserId()
            .equals(user.getUserId())) {

        throw new RuntimeException(
                "You Can Request Refund Only For Your Own Payment");
    }

    if (!"SUCCESS".equalsIgnoreCase(
            payment.getPaymentStatus())) {

        throw new RuntimeException(
                "Refund Allowed Only For Successful Payments");
    }

    if (refundRepository.existsByPayment_PaymentId(
            payment.getPaymentId())) {

        throw new RuntimeException(
                "Refund Already Requested");
    }

    Refund refund =
            new Refund();

    refund.setPayment(payment);
    refund.setRefundAmount(
            payment.getAmount());

    refund.setReason(
            dto.getReason());

    refund.setRefundStatus(
            "REQUESTED");

    refund.setRefundDate(
            LocalDateTime.now());

    Refund savedRefund =
            refundRepository.save(
                    refund);
    CreateNotificationDTO notification =
            new CreateNotificationDTO();

    notification.setUserId(
            user.getUserId());

    notification.setTitle(
            "REFUND REQUESTED");

    notification.setMessage(
            "Refund request submitted for Payment #"
            + payment.getPaymentId());

    notification.setType(
            "REFUND");

    notificationService
            .createNotification(
                    notification);
    RefundResponseDTO response =
            new RefundResponseDTO();

    response.setRefundId(
            savedRefund.getRefundId());

    response.setMessage(
            "Refund Request Submitted Successfully");

    return response;
}

// ================= MY REFUNDS =================

@Override
public List<RefundDTO> getMyRefunds() {

    User user =
            getLoggedInUser();

    return refundRepository
            .findByPayment_Order_User_UserId(
                    user.getUserId())
            .stream()
            .map(this::convertToDTO)
            .toList();
}

// ================= GET REFUND BY ID =================

@Override
public RefundDTO getRefundById(
        Integer refundId) {

    User user =
            getLoggedInUser();

    Refund refund =
            refundRepository.findById(
                    refundId)
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Refund Not Found"));

    if (!refund.getPayment()
            .getOrder()
            .getUser()
            .getUserId()
            .equals(user.getUserId())) {

        throw new RuntimeException(
                "Access Denied");
    }

    return convertToDTO(refund);
}
// ================= ALL REFUNDS =================

@Override
public List<RefundDTO> getAllRefunds() {

    return refundRepository.findAll()
            .stream()
            .map(this::convertToDTO)
            .toList();
}

// ================= REFUNDS BY STATUS =================

@Override
public List<RefundDTO> getRefundsByStatus(
        String refundStatus) {

    return refundRepository
            .findByRefundStatus(
                    refundStatus)
            .stream()
            .map(this::convertToDTO)
            .toList();
}

// ================= APPROVE REFUND =================

@Override
public RefundResponseDTO approveRefund(
        Integer refundId,
        AdminRefundActionDTO dto) {

    Refund refund =
            refundRepository.findById(
                    refundId)
            .orElseThrow(() ->
                    new ResourceNotFoundException(
     
                    		"Refund Not Found"));
    if (!"REQUESTED".equals(
            refund.getRefundStatus())) {

        throw new RuntimeException(
                "Refund Request Already Processed");
    }

    if ("APPROVED".equalsIgnoreCase(
            refund.getRefundStatus())) {

        throw new RuntimeException(
                "Refund Already Approved");
    }

    Payment payment =
            refund.getPayment();

    Order order =
            payment.getOrder();

    for (OrderItem item :
            order.getItems()) {

        Product product =
                item.getProduct();

        product.setQuantity(
                product.getQuantity()
                        + item.getQuantity());

        productRepository.save(
                product);
    }

    refund.setRefundStatus(
            "APPROVED");

    refund.setAdminRemarks(
            dto.getAdminRemarks());

    refund.setApprovedDate(
            LocalDateTime.now());

    payment.setPaymentStatus(
            "REFUNDED");

    order.setStatus(
            "REFUNDED");

    paymentRepository.save(
            payment);

    orderRepository.save(
            order);

    refundRepository.save(
            refund);
    CreateNotificationDTO notification =
            new CreateNotificationDTO();

    notification.setUserId(
            order.getUser()
                 .getUserId());

    notification.setTitle(
            "REFUND APPROVED");

    notification.setMessage(
            "Your refund request for Order #"
            + order.getOrderId()
            + " has been approved.");

    notification.setType(
            "REFUND");

    notificationService
            .createNotification(
                    notification);

    RefundResponseDTO response =
            new RefundResponseDTO();

    response.setRefundId(
            refund.getRefundId());

    response.setMessage(
            "Refund Approved Successfully");

    return response;
}

// ================= REJECT REFUND =================

@Override
public RefundResponseDTO rejectRefund(
        Integer refundId,
        AdminRefundActionDTO dto) {

    Refund refund =
            refundRepository.findById(
                    refundId)
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Refund Not Found"));
    if (!"REQUESTED".equals(
            refund.getRefundStatus())) {

        throw new RuntimeException(
                "Refund Request Already Processed");
    }
    refund.setRefundStatus(
            "REJECTED");

    refund.setAdminRemarks(
            dto.getAdminRemarks());

    refundRepository.save(
            refund);
    CreateNotificationDTO notification =
            new CreateNotificationDTO();

    notification.setUserId(
            refund.getPayment()
                  .getOrder()
                  .getUser()
                  .getUserId());

    notification.setTitle(
            "REFUND REJECTED");

    notification.setMessage(
            "Your refund request for Payment #"
            + refund.getPayment()
                    .getPaymentId()
            + " has been rejected.");

    notification.setType(
            "REFUND");

    notificationService
            .createNotification(
                    notification);

    RefundResponseDTO response =
            new RefundResponseDTO();

    response.setRefundId(
            refund.getRefundId());

    response.setMessage(
            "Refund Rejected Successfully");

    return response;
}

// ================= DELETE REFUND =================

@Override
public void deleteRefund(
        Integer refundId) {

    Refund refund =
            refundRepository.findById(
                    refundId)
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Refund Not Found"));

    refundRepository.delete(
            refund);
}


}
