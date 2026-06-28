package com.inventorymanagement.service;
import java.io.IOException;
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

//================= EXPORT INVENTORY REPORT =================

byte[] exportInventoryReportPdf() throws IOException;

byte[] exportInventoryReportExcel() throws IOException;

//================= EXPORT SALES REPORT =================

byte[] exportSalesReportPdf() throws IOException;

byte[] exportSalesReportExcel() throws IOException;

//================= EXPORT PURCHASE REPORT =================

byte[] exportPurchaseReportPdf() throws IOException;

byte[] exportPurchaseReportExcel() throws IOException;

//================= EXPORT PAYMENT REPORT =================

byte[] exportPaymentReportPdf() throws IOException;

byte[] exportPaymentReportExcel() throws IOException;

//================= EXPORT REFUND REPORT =================

byte[] exportRefundReportPdf() throws IOException;

byte[] exportRefundReportExcel() throws IOException;
}
