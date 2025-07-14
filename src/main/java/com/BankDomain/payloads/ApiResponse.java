package com.BankDomain.payloads;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
public class ApiResponse<T> {
    private LocalDateTime timeStamp;
    private String message;
    private HttpStatus status;
    private T data;

    //success with data
    public static <T> ApiResponse<T> successMessage(String message , T data){
            return ApiResponse.<T>builder()
                    .timeStamp(LocalDateTime.now())
                    .message(message)
                    .status(HttpStatus.OK)
                    .data(data)
                    .build();
    }
    //success without data
    public static <T> ApiResponse<T> withoutData(String message){
            return ApiResponse.<T>builder()
                    .timeStamp(LocalDateTime.now())
                    .data(null)
                    .status(HttpStatus.OK)
                    .message(message)
                    .build();
    }
    //error message
    public static <T> ApiResponse<T> errorMessage(HttpStatus status,String message ){
            return ApiResponse.<T>builder()
                    .timeStamp(LocalDateTime.now())
                    .message(message)
                    .status(status)
                    .data(null)
                    .build();
    }
}
