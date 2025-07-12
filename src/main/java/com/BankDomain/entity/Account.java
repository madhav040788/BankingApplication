package com.BankDomain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true,nullable = false)
    @NotBlank(message = Constants.ACCOUNT_NUMBER_REQUIRED)
    private String accountNumber;
    @NotBlank(message = Constants.ACCOUNT_HOLDER_NAME_REQUIRED)
    private String accountHolderName;
    @NotNull(message = Constants.BALANCE_NON_NEGATIVE)
    @DecimalMin(value = "0.0", message = Constants.BALANCE_NON_NEGATIVE)
    private BigDecimal balance;
    @JsonFormat(pattern = Constants.DATE_TIME_FORMAT)
    private LocalDateTime createdAt;
    private boolean isActive;

    @Enumerated(EnumType.STRING)
    @NotNull(message = Constants.ACCOUNT_TYPE_REQUIRED)
    private AccountType accountType;

}
