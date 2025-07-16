package com.BankDomain.contorller;

import com.BankDomain.service.impl.EmailService;
import com.BankDomain.service.impl.ExcelReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/mail")
@RequiredArgsConstructor
public class MailController {

    private final EmailService emailService;
    private final ExcelReportService excelReportService;

    @Value("${file.excel.path}")
    private String excelFolderPath;

@GetMapping("/send-excel")
    public ResponseEntity<String> sendExcelToMail(@RequestParam String toEmail){
    String timeStamp = LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("yyyyMMdd_HH-mm-ss"));
    String fileName = timeStamp +"_accounts.xlsx";
    String filePath =  excelFolderPath +"/" +fileName;

          try {
              File folder = new File(excelFolderPath);
              if (!folder.exists()){
                  folder.mkdir();
              }
              // Generate Excel file
              excelReportService.generateExcel(filePath);
              // Send email with attachment
              emailService.sendMailWithAttachment(
                      toEmail,
                      "Bank Account Report : ",
                      "please find attached your account report",
                      filePath
              );
              return ResponseEntity.ok("✅ Excel send on Mail : "+toEmail+"\nFilename: "+fileName);
          } catch (IOException e) {
              return ResponseEntity.internalServerError().body("❌ Error: generating Report : "+e.getMessage());
          }
    }
}
