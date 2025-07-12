package com.BankDomain.service.impl;

import com.BankDomain.dto.AccountDTO;
import com.BankDomain.entity.Account;
import com.BankDomain.exception.InsufficientFundException;
import com.BankDomain.exception.InvalidAmountException;
import com.BankDomain.exception.ResourceNotFoundException;
import com.BankDomain.repository.AccountRepository;
import com.BankDomain.service.AccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    //    @Autowired
    private final AccountRepository accountRepository;

    //    private static final Logger logger =
    @Override
    public Account createAccount(AccountDTO dto) {
        String accNumber = UUID.randomUUID().toString().substring(0,10);
        var account = Account.builder()
                .accountNumber(accNumber)
                .accountHolderName(dto.getAccountHolderName())
                .accountType(dto.getAccountType())
                .balance(dto.getInitialDeposit())
                .createdAt(LocalDateTime.now())
                .isActive(true)
                .build();
        var saved = accountRepository.save(account);
        log.info("New Account Created : {}", saved.getAccountNumber());
        return saved;
    }

    @Override
    public BigDecimal getBalance(String accountNumber) {
        Account acc = getAccount(accountNumber);
        return acc.getBalance();
    }

    @Override
    public void depositAmount(String accountNumber, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) throw new InvalidAmountException("Deposit must be positive : ");
        Account acc = getAccount(accountNumber);
        acc.setBalance(acc.getBalance().add(amount));
        accountRepository.save(acc);
        log.info("Deposited {} to amount : {}", amount, accountNumber);
    }

    private Account getAccount(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Account not Found : " + accountNumber));
    }

    @Override
    public void withdrawAmount(String accountNumber, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) throw new InvalidAmountException("Withdrawal must be positive");
        Account acc = getAccount(accountNumber);
        if (acc.getBalance().compareTo(amount) < 0) throw new InsufficientFundException("Insufficient Balance ");
        acc.setBalance(acc.getBalance().subtract(amount));
        accountRepository.save(acc);
        log.info("Withdrawal {} from Account:  {}", amount, accountNumber);
    }

    @Override
    public List<Account> getAccountsAboveBalance(BigDecimal amount) {
        var accounts = accountRepository.findAccountsWithBalanceGreaterThan(amount);
        log.info("Found {} account with balance above {} : ", accounts.size(), amount);
        return accounts;
    }
    public void deleteAccount(String accountNumber){
        log.info("AccuontServiceImpl:: deletingAccount() delete from db : {}",accountNumber);
        Optional<Account> optionalAccount = accountRepository.findByAccountNumber(accountNumber);
        if (optionalAccount.isPresent()){
            accountRepository.deleteByAccountNumber(accountNumber);
        }else {
            throw new ResourceNotFoundException("Account Not Found : "+accountNumber);
        }
    }


}
