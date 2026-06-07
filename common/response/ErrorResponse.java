package com.ktn3.core_banking.common.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {

    private int status;

    private String message;

    private String errorCode;

    private LocalDateTime timestamp;
}
