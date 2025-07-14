package com.BankDomain.exception;

import com.BankDomain.payloads.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    //  404 Not Found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFound(ResourceNotFoundException ex) {
        log.error("Error occurred : ",ex);
        return new ResponseEntity<>(
                ApiResponse.errorMessage(HttpStatus.NOT_FOUND, ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    // 400 bad request (custom invalid logic)
    @ExceptionHandler(InvalidAmountException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidAmount(InvalidAmountException ex){
        log.error("Error Invalid Amount occurred : ",ex);
        return new ResponseEntity<>(
                ApiResponse.errorMessage(HttpStatus.BAD_REQUEST,ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ApiResponse<Void>> handleInsufficientFundException(InsufficientFundException ex){
            return new ResponseEntity<>(
                    ApiResponse.errorMessage(HttpStatus.NOT_ACCEPTABLE,ex.getMessage()),
                    HttpStatus.NOT_ACCEPTABLE);
    }

    //500 - catch any unhandled errors
    public ResponseEntity<ApiResponse<Void>> handleAllException(Exception ex){
        log.error("Unhandled Error here : ",ex);
        return new ResponseEntity<>(
                ApiResponse.errorMessage(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
