package com.BankDomain.service;
import com.BankDomain.entity.Account;
import com.BankDomain.dto.AccountDTO;
import java.util.List;
import java.math.BigDecimal;

public interface AccountService {
    Account createAccount(AccountDTO dto);
    BigDecimal getBalance(String accountNumber);
    void depositAmount(String accountNumber, BigDecimal amount);
    void withdrawAmount(String accountNumber, BigDecimal amount);
    List<Account> getAccountsAboveBalance(BigDecimal amount);
    void deleteAccount(String accountNumber);
}
