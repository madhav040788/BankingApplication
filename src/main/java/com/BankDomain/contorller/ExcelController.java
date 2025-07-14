package com.BankDomain.contorller;

import com.BankDomain.entity.Account;
import com.BankDomain.service.impl.ExcelReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/excel")
public class ExcelController {

    @Autowired
    private ExcelReportService excelReportService;

    @Value("${file.excel.path}")
    private String excelFolderPath;

    @GetMapping("/accounts")
    public ResponseEntity<String> excelGenerate(){
        //Format currnet date
        String timeStamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"));
        //full file path
        String fileName = "AccountDetails_"+timeStamp + "_accounts.xlsx";

        String filePath = excelFolderPath + "/" +fileName;

        try {
            //target folder exist or not
            File folder = new File(excelFolderPath);
            if (!folder.exists()){
                folder.mkdir();//if folder doesn't exist
            }
            excelReportService.generateExcel(filePath);
            return ResponseEntity.ok("Generated to : "+fileName);
        }catch (IOException e){
            return ResponseEntity.status(500).body("Failed to Generate : "+e.getMessage());
        }

    }
}
