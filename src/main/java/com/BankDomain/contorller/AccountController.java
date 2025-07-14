package com.BankDomain.contorller;

import com.BankDomain.dto.AccountDTO;
import com.BankDomain.entity.Account;
import com.BankDomain.payloads.ApiResponse;
import com.BankDomain.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {



        private final AccountService accountService;

        @PostMapping("/create")
        public ResponseEntity<ApiResponse<Account>> createNewAccount(@Valid @RequestBody  AccountDTO dto){
            Account created = accountService.createAccount(dto);
            return ResponseEntity.ok(ApiResponse.successMessage("Account Created : ",created));
        }

        @GetMapping("/{accountNumber}/balance")
        public ResponseEntity<BigDecimal> getBalance(@PathVariable String accountNumber){
            return ResponseEntity.ok(accountService.getBalance(accountNumber));
        }

    @PostMapping("/{accountNumber}/deposit")
    public ResponseEntity<String> deposit(@PathVariable String accountNumber, @RequestParam BigDecimal amount) {
        accountService.depositAmount(accountNumber, amount);
        return ResponseEntity.ok("Deposit successful");
    }

    @PostMapping("/{accountNumber}/withdraw")
    public ResponseEntity<String> withdraw(@PathVariable String accountNumber, @RequestParam BigDecimal amount) {
        accountService.withdrawAmount(accountNumber, amount);
        return ResponseEntity.ok("Withdrawal successful");
    }

    @GetMapping("/above-balance")
    public ResponseEntity<List<Account>> getAccountsAboveBalance(@RequestParam BigDecimal amount) {
        return ResponseEntity.ok(accountService.getAccountsAboveBalance(amount));
    }
    @Transactional
    @DeleteMapping("/remove/{accountNumber}")
    public ResponseEntity<ApiResponse<String>> deleteAccount(@PathVariable String accountNumber){
            accountService.deleteAccount(accountNumber);
            return  ResponseEntity.ok(ApiResponse.withoutData("Account Deleted Successfully : "));
    }
}
