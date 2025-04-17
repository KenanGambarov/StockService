package com.stockservice.services;

import com.stockservice.entity.StockEntity;

public interface StockCacheService {

    StockEntity getStockFromCacheOrDB(Long productId);

    void clearStockCache(Long productId);

}
