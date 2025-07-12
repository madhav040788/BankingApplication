package com.BankDomain.entity;

public interface Constants {
    // Field names
    String ACCOUNT_NUMBER = "account number";
    String ACCOUNT_HOLDER_NAME = "account holder name";
    String ACCOUNT_TYPE = "account type";
    String BALANCE = "balance";

    // Messages
    String ACCOUNT_NUMBER_REQUIRED = "Account number is required";
    String ACCOUNT_HOLDER_NAME_REQUIRED = "Account holder name is required";
    String ACCOUNT_TYPE_REQUIRED = "Account type is required";
    String BALANCE_NON_NEGATIVE = "Balance must be non-negative";

    // Excel headers
    String[] ACCOUNT_EXCEL_HEADERS = {"Account Number", "Holder Name", "Balance", "Account Type", "Created At"};

    // Format
    String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
}
