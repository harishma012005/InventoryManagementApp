package com.inventorymanagement.util;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.inventorymanagement.dto.InventoryReportDTO;

public class ExcelGenerator {

    public static byte[] generateInventoryReport(
            List<InventoryReportDTO> reports) {

        try {

            XSSFWorkbook workbook =
                    new XSSFWorkbook();

            XSSFSheet sheet =
                    workbook.createSheet(
                            "Inventory Report");

            int rowNum = 0;

            Row header =
                    sheet.createRow(rowNum++);

            header.createCell(0)
                    .setCellValue("Product ID");

            header.createCell(1)
                    .setCellValue("Product Name");

            header.createCell(2)
                    .setCellValue("Quantity");

            header.createCell(3)
                    .setCellValue("Price");

            header.createCell(4)
                    .setCellValue("Inventory Value");

            for (InventoryReportDTO dto : reports) {

                Row row =
                        sheet.createRow(rowNum++);

                row.createCell(0)
                        .setCellValue(
                                dto.getProductId());

                row.createCell(1)
                        .setCellValue(
                                dto.getProductName());

                row.createCell(2)
                        .setCellValue(
                                dto.getQuantity());

                row.createCell(3)
                        .setCellValue(
                                dto.getPrice()
                                        .doubleValue());

                row.createCell(4)
                        .setCellValue(
                                dto.getInventoryValue()
                                        .doubleValue());
            }

            ByteArrayOutputStream outputStream =
                    new ByteArrayOutputStream();

            workbook.write(outputStream);

            workbook.close();

            return outputStream.toByteArray();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Error Generating Excel");
        }
    }
}