package com.bankapplication.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ErrorResponseDto {

    public ErrorResponseDto(String apipath, HttpStatus errorCode, String errorMsg, LocalDateTime errorTime) {
        this.apipath = apipath;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.errorTime = errorTime;
    }

    private String apipath;

    private HttpStatus errorCode;

    private String errorMsg;

    private LocalDateTime errorTime;
}
