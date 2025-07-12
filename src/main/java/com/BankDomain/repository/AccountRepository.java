package com.BankDomain.repository;

import com.BankDomain.entity.Account;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountNumber(String accountNumber);

    @Query("SELECT a FROM Account a WHERE a.balance > :amount")
    List<Account> findAccountsWithBalanceGreaterThan(BigDecimal amount);

    @Query("SELECT COUNT(a) FROM Account a WHERE a.isActive = true")
    long countActiveAccounts();

    List<Account> findByAccountType(String accountType);

    void deleteByAccountNumber(String accountNumber);
}
