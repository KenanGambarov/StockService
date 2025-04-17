package com.stockservice.util.constraints;

public enum StockCacheConstraints {

    PRODUCT_KEY("ms-stock:products:%s");

    private final String keyFormat;

    StockCacheConstraints(String keyFormat) {
        this.keyFormat = keyFormat;
    }

    public String getKey(Object... args) {
        return String.format(this.keyFormat, args);
    }
}
