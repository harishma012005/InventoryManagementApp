package com.inventorymanagement.service;

import java.util.List;

import com.inventorymanagement.dto.InventoryReportDTO;
import com.inventorymanagement.dto.PaymentReportDTO;
import com.inventorymanagement.dto.PurchaseReportDTO;
import com.inventorymanagement.dto.RefundReportDTO;
import com.inventorymanagement.dto.SalesReportDTO;

public interface ReportService {


List<InventoryReportDTO> getInventoryReport();

List<SalesReportDTO> getSalesReport();

List<PaymentReportDTO> getPaymentReport();

List<RefundReportDTO> getRefundReport();

List<PurchaseReportDTO> getPurchaseReport();


}
