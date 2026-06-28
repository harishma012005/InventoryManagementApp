package com.inventorymanagement.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventorymanagement.dto.InventoryReportDTO;
import com.inventorymanagement.dto.PaymentReportDTO;
import com.inventorymanagement.dto.PurchaseReportDTO;
import com.inventorymanagement.dto.RefundReportDTO;
import com.inventorymanagement.dto.SalesReportDTO;
import com.inventorymanagement.entity.Order;
import com.inventorymanagement.entity.Payment;
import com.inventorymanagement.entity.Product;
import com.inventorymanagement.entity.Purchase;
import com.inventorymanagement.entity.Refund;
import com.inventorymanagement.repository.OrderRepository;
import com.inventorymanagement.repository.PaymentRepository;
import com.inventorymanagement.repository.ProductRepository;
import com.inventorymanagement.repository.PurchaseRepository;
import com.inventorymanagement.repository.RefundRepository;
import com.inventorymanagement.service.ReportService;
import java.io.IOException;

import com.inventorymanagement.util.ExcelGenerator;
import com.inventorymanagement.util.PdfGenerator;
@Service
public class ReportServiceImpl implements ReportService {


@Autowired
private ProductRepository productRepository;

@Autowired
private OrderRepository orderRepository;

@Autowired
private PaymentRepository paymentRepository;

@Autowired
private RefundRepository refundRepository;

@Autowired
private PurchaseRepository purchaseRepository;

// ================= INVENTORY REPORT =================

@Override
public List<InventoryReportDTO> getInventoryReport() {

    return productRepository.findAll()
            .stream()
            .map(this::convertInventoryDTO)
            .toList();
}

private InventoryReportDTO convertInventoryDTO(
        Product product) {

    InventoryReportDTO dto =
            new InventoryReportDTO();

    dto.setProductId(
            product.getProductId());

    dto.setProductName(
            product.getProductName());

    dto.setQuantity(
            product.getQuantity());

    dto.setPrice(
            product.getPrice());

    dto.setInventoryValue(
            product.getPrice().multiply(
                    BigDecimal.valueOf(
                            product.getQuantity())));

    return dto;
}
// ================= SALES REPORT =================

@Override
public List<SalesReportDTO> getSalesReport() {

    return orderRepository.findAll()
            .stream()
            .map(this::convertSalesDTO)
            .toList();
}

private SalesReportDTO convertSalesDTO(
        Order order) {

    SalesReportDTO dto =
            new SalesReportDTO();

    dto.setOrderId(
            order.getOrderId());

    dto.setCustomerName(
            order.getUser()
                    .getFullName());

    dto.setOrderDate(
            order.getOrderDate());

    dto.setTotalAmount(
            order.getTotalAmount());

    dto.setStatus(
            order.getStatus());

    dto.setOrderType(
            order.getOrderType());

    return dto;
}

// ================= PAYMENT REPORT =================

@Override
public List<PaymentReportDTO> getPaymentReport() {

    return paymentRepository.findAll()
            .stream()
            .map(this::convertPaymentDTO)
            .toList();
}

private PaymentReportDTO convertPaymentDTO(
        Payment payment) {

    PaymentReportDTO dto =
            new PaymentReportDTO();

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

// ================= REFUND REPORT =================

@Override
public List<RefundReportDTO> getRefundReport() {

    return refundRepository.findAll()
            .stream()
            .map(this::convertRefundDTO)
            .toList();
}

private RefundReportDTO convertRefundDTO(
        Refund refund) {

    RefundReportDTO dto =
            new RefundReportDTO();

    dto.setRefundId(
            refund.getRefundId());

    dto.setPaymentId(
            refund.getPayment()
                    .getPaymentId());

    dto.setRefundAmount(
            refund.getRefundAmount());

    dto.setReason(
            refund.getReason());

    dto.setRefundStatus(
            refund.getRefundStatus());

    dto.setAdminRemarks(
            refund.getAdminRemarks());

    dto.setRefundDate(
            refund.getRefundDate());

    dto.setApprovedDate(
            refund.getApprovedDate());

    return dto;
}

// ================= PURCHASE REPORT =================

@Override
public List<PurchaseReportDTO> getPurchaseReport() {

    return purchaseRepository.findAll()
            .stream()
            .map(this::convertPurchaseDTO)
            .toList();
}

private PurchaseReportDTO convertPurchaseDTO(
        Purchase purchase) {

    PurchaseReportDTO dto =
            new PurchaseReportDTO();

    dto.setPurchaseId(
            purchase.getPurchaseId());

    dto.setSupplierName(
            purchase.getSupplier()
                    .getSupplierName());

    dto.setTotalAmount(
            purchase.getTotalAmount());

    return dto;
}
@Override
public byte[] exportInventoryReportPdf() throws IOException {

    return PdfGenerator.generateInventoryReport(
            getInventoryReport());
}
@Override
public byte[] exportInventoryReportExcel() throws IOException {

    return ExcelGenerator.generateInventoryReport(
            getInventoryReport());
}

@Override
public byte[] exportSalesReportPdf() throws IOException {

    throw new UnsupportedOperationException(
            "Sales PDF Export Not Implemented Yet");
}
@Override
public byte[] exportSalesReportExcel() throws IOException {

    throw new UnsupportedOperationException(
            "Sales Excel Export Not Implemented Yet");
}
@Override
public byte[] exportPurchaseReportPdf() throws IOException {

    throw new UnsupportedOperationException(
            "Purchase PDF Export Not Implemented Yet");
}
@Override
public byte[] exportPurchaseReportExcel() throws IOException {

    throw new UnsupportedOperationException(
            "Purchase Excel Export Not Implemented Yet");
}
@Override
public byte[] exportPaymentReportPdf() throws IOException {

    throw new UnsupportedOperationException(
            "Payment PDF Export Not Implemented Yet");
}
@Override
public byte[] exportPaymentReportExcel() throws IOException {

    throw new UnsupportedOperationException(
            "Payment Excel Export Not Implemented Yet");
}
@Override
public byte[] exportRefundReportPdf() throws IOException {

    throw new UnsupportedOperationException(
            "Refund PDF Export Not Implemented Yet");
}
@Override
public byte[] exportRefundReportExcel() throws IOException {

    throw new UnsupportedOperationException(
            "Refund Excel Export Not Implemented Yet");
}
}

