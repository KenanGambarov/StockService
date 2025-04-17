package com.stockservice.dto.enums;

import lombok.Getter;

@Getter
public enum StockChangeTypeDescription {

    PRODUCT_SOLD("Product Sold"),
    NEW_PRODUCT_ADDED("New Product Added"),
    PRODUCT_ADDED("Product Added");

    private final String description;

    StockChangeTypeDescription(String description){
        this.description = description;
    }

}
