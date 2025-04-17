package com.stockservice.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionResponse {

    private LocalDateTime timestamp;
    private int status;
    private String message;
    private String errors;
    private Map<String, Object> details;

}
