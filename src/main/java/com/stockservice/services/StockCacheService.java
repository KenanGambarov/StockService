package com.stockservice.services;

import com.stockservice.entity.StockEntity;

import java.util.Optional;

public interface StockCacheService {

    Optional<StockEntity> getStockFromCacheOrDB(Long productId);

    void clearStockCache(Long productId);

}
