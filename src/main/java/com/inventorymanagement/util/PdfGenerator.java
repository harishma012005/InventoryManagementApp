package com.inventorymanagement.util;

import java.io.ByteArrayOutputStream;
import java.util.List;

import com.inventorymanagement.dto.InventoryReportDTO;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfGenerator {

    public static byte[] generateInventoryReport(
            List<InventoryReportDTO> reports) {

        try {

            Document document = new Document();

            ByteArrayOutputStream outputStream =
                    new ByteArrayOutputStream();

            PdfWriter.getInstance(
                    document,
                    outputStream);

            document.open();

            document.add(
                    new Paragraph("Inventory Report"));

            document.add(
                    new Paragraph(" "));

            PdfPTable table =
                    new PdfPTable(5);

            table.setWidthPercentage(100);

            table.addCell(
                    new PdfPCell(
                            new Phrase("Product ID")));

            table.addCell(
                    new PdfPCell(
                            new Phrase("Product Name")));

            table.addCell(
                    new PdfPCell(
                            new Phrase("Quantity")));

            table.addCell(
                    new PdfPCell(
                            new Phrase("Price")));

            table.addCell(
                    new PdfPCell(
                            new Phrase("Inventory Value")));

            for (InventoryReportDTO dto : reports) {

                table.addCell(
                        dto.getProductId().toString());

                table.addCell(
                        dto.getProductName());

                table.addCell(
                        dto.getQuantity().toString());

                table.addCell(
                        dto.getPrice().toString());

                table.addCell(
                        dto.getInventoryValue().toString());
            }

            document.add(table);

            document.close();

            return outputStream.toByteArray();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Error Generating PDF");
        }
    }
}