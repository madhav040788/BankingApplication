package com.BankDomain.service.impl;

import com.BankDomain.entity.Account;
import com.BankDomain.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@Service
public class ExcelReportService {

    @Autowired
    private AccountRepository accountRepository;

    public void generateExcel(String filePath) throws IOException {
        List<Account> accounts= accountRepository.findAll();

        // Create parent folder if needed
        File file = new File(filePath);
        // ✅ Only create parent folders if they exist in the path
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        try (XSSFWorkbook workbook = new XSSFWorkbook();
            FileOutputStream out = new FileOutputStream(file))
         {
            XSSFSheet sheet = workbook.createSheet("Accounts");
            /// create font blod
            XSSFFont headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 12);
            headerFont.setColor(IndexedColors.WHITE.getIndex());
            //create header style
            XSSFCellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);

            //date format
            XSSFCellStyle dateCellStyle = workbook.createCellStyle();
            XSSFCreationHelper createHelper = workbook.getCreationHelper();
            dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));

            //data cell style
             XSSFCellStyle dataStyle = workbook.createCellStyle();
             dataStyle.setBorderBottom(BorderStyle.THIN);
             dataStyle.setBorderRight(BorderStyle.THIN);
             dataStyle.setBorderLeft(BorderStyle.THIN);
             dataStyle.setBorderBottom(BorderStyle.THIN);
             dataStyle.setWrapText(true);

             //Header
            XSSFRow headerRow = sheet.createRow(0);
            String[] header = {"Account Number", "Account Holder", "Account Type","Balance", "Created At","Active"};
            for (int i = 0; i < header.length; i++) {
                XSSFCell cell = headerRow.createCell(i);
                cell.setCellValue(header[i]);
                cell.setCellStyle(headerStyle);
            }

            //Data
            int rowIndx = 1;
            for (Account acc : accounts) {
                XSSFRow row = sheet.createRow(rowIndx++);
                row.createCell(0).setCellValue(acc.getAccountNumber());
                row.createCell(1).setCellValue(acc.getAccountHolderName());
                row.createCell(2).setCellValue(acc.getAccountType().toString());
                row.createCell(3).setCellValue(acc.getBalance().doubleValue());

                XSSFCell dateCell = row.createCell(4);
                dateCell.setCellValue(Timestamp.valueOf(acc.getCreatedAt()));
                dateCell.setCellStyle(dateCellStyle);
                row.createCell(5).setCellValue(acc.isActive() ? "Yes" : "No");

                for (int i = 0; i < header.length; i++) {
                    row.getCell(i).setCellStyle(dateCellStyle);
                }
            }
            //auto-size column
             for (int i = 0; i < header.length;i++){
                 sheet.autoSizeColumn(i);
             }
            //write to file
            workbook.write(out);
        }
    }
}
