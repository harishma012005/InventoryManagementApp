package com.inventorymanagement.controller;
import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.inventorymanagement.dto.InventoryReportDTO;
import com.inventorymanagement.dto.PaymentReportDTO;
import com.inventorymanagement.dto.PurchaseReportDTO;
import com.inventorymanagement.dto.RefundReportDTO;
import com.inventorymanagement.dto.SalesReportDTO;
import com.inventorymanagement.service.ReportService;

@RestController
@RequestMapping("/reports")
public class ReportController {


@Autowired
private ReportService reportService;

// ================= INVENTORY REPORT =================

@GetMapping("/inventory")
public ResponseEntity<List<InventoryReportDTO>>
getInventoryReport() {

    return ResponseEntity.ok(
            reportService.getInventoryReport());
}


// ================= SALES REPORT =================

@GetMapping("/sales")
public ResponseEntity<List<SalesReportDTO>>
getSalesReport() {

    return ResponseEntity.ok(
            reportService.getSalesReport());
}

// ================= PAYMENT REPORT =================

@GetMapping("/payments")
public ResponseEntity<List<PaymentReportDTO>>
getPaymentReport() {

    return ResponseEntity.ok(
            reportService.getPaymentReport());
}

// ================= REFUND REPORT =================

@GetMapping("/refunds")
public ResponseEntity<List<RefundReportDTO>>
getRefundReport() {

    return ResponseEntity.ok(
            reportService.getRefundReport());
}

// ================= PURCHASE REPORT =================

@GetMapping("/purchases")
public ResponseEntity<List<PurchaseReportDTO>>
getPurchaseReport() {

    return ResponseEntity.ok(
            reportService.getPurchaseReport());
}
@GetMapping("/inventory/pdf")
public ResponseEntity<byte[]> exportInventoryPdf()
        throws IOException {

    byte[] pdf =
            reportService.exportInventoryReportPdf();

    return ResponseEntity.ok()

            .header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=inventory_report.pdf")

            .contentType(
                    MediaType.APPLICATION_PDF)

            .body(pdf);
}
@GetMapping("/inventory/excel")
public ResponseEntity<byte[]> exportInventoryExcel()
        throws IOException {

    byte[] excel =
            reportService.exportInventoryReportExcel();

    return ResponseEntity.ok()

            .header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=inventory_report.xlsx")

            .contentType(
                    MediaType.APPLICATION_OCTET_STREAM)

            .body(excel);
}
@GetMapping("/sales/pdf")
public ResponseEntity<byte[]> exportSalesPdf()
        throws IOException {

    byte[] pdf =
            reportService.exportSalesReportPdf();

    return ResponseEntity.ok()

            .header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=sales_report.pdf")

            .contentType(
                    MediaType.APPLICATION_PDF)

            .body(pdf);
}
@GetMapping("/sales/excel")
public ResponseEntity<byte[]> exportSalesExcel()
        throws IOException {

    byte[] excel =
            reportService.exportSalesReportExcel();

    return ResponseEntity.ok()

            .header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=sales_report.xlsx")

            .contentType(
                    MediaType.APPLICATION_OCTET_STREAM)

            .body(excel);
}
@GetMapping("/purchase/pdf")
public ResponseEntity<byte[]> exportPurchasePdf()
        throws IOException {

    byte[] pdf =
            reportService.exportPurchaseReportPdf();

    return ResponseEntity.ok()

            .header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=purchase_report.pdf")

            .contentType(
                    MediaType.APPLICATION_PDF)

            .body(pdf);
}
@GetMapping("/purchase/excel")
public ResponseEntity<byte[]> exportPurchaseExcel()
        throws IOException {

    byte[] excel =
            reportService.exportPurchaseReportExcel();

    return ResponseEntity.ok()

            .header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=purchase_report.xlsx")

            .contentType(
                    MediaType.APPLICATION_OCTET_STREAM)

            .body(excel);
}
@GetMapping("/payment/pdf")
public ResponseEntity<byte[]> exportPaymentPdf()
        throws IOException {

    byte[] pdf =
            reportService.exportPaymentReportPdf();

    return ResponseEntity.ok()

            .header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=payment_report.pdf")

            .contentType(
                    MediaType.APPLICATION_PDF)

            .body(pdf);
}
@GetMapping("/payment/excel")
public ResponseEntity<byte[]> exportPaymentExcel()
        throws IOException {

    byte[] excel =
            reportService.exportPaymentReportExcel();

    return ResponseEntity.ok()

            .header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=payment_report.xlsx")

            .contentType(
                    MediaType.APPLICATION_OCTET_STREAM)

            .body(excel);
}
@GetMapping("/refund/pdf")
public ResponseEntity<byte[]> exportRefundPdf()
        throws IOException {

    byte[] pdf =
            reportService.exportRefundReportPdf();

    return ResponseEntity.ok()

            .header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=refund_report.pdf")

            .contentType(
                    MediaType.APPLICATION_PDF)

            .body(pdf);
}
@GetMapping("/refund/excel")
public ResponseEntity<byte[]> exportRefundExcel()
        throws IOException {

    byte[] excel =
            reportService.exportRefundReportExcel();

    return ResponseEntity.ok()

            .header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=refund_report.xlsx")

            .contentType(
                    MediaType.APPLICATION_OCTET_STREAM)

            .body(excel);
}


}
