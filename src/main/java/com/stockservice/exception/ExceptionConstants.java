package com.stockservice.exception;

import lombok.Getter;

@Getter
public enum ExceptionConstants {

    VALIDATION_FAILED("Validation Failed"),
    UNEXPECTED_ERROR("Unexpected Error"),
    CLIENT_ERROR("Exception from Client"),
    PRODUCT_NOT_FOUND("Product Not Found"),
    PRODUCT_OUT_OF_STOCK("Product is out of stock");

    private final String message;

    ExceptionConstants(String message) {
        this.message = message;
    }
}
