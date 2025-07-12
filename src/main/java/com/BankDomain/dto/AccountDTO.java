package com.BankDomain.dto;

import com.BankDomain.entity.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDTO {
    private String accountHolderName;
    private AccountType accountType;
    private BigDecimal initialDeposit;
}
