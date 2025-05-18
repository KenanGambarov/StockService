package com.stockservice.services.impl;

import com.stockservice.entity.StockEntity;
import com.stockservice.exception.ExceptionConstants;
import com.stockservice.exception.NotFoundException;
import com.stockservice.exception.OutOfStockException;
import com.stockservice.mapper.StockMapper;
import com.stockservice.repository.StockRepository;
import com.stockservice.services.StockCacheService;
import com.stockservice.util.CacheUtil;
import com.stockservice.util.constraints.StockCacheConstraints;
import com.stockservice.util.constraints.StockCacheDurationConstraints;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class StockCacheServiceImpl implements StockCacheService {

    private final StockRepository stockRepository;
    private final CacheUtil cacheUtil;

    @Override
    @CircuitBreaker(name = "redisBreaker", fallbackMethod = "fallbackStockCache")
    @Retry(name = "redisRetry", fallbackMethod = "fallbackStockCache")
    public Optional<StockEntity> getStockFromCacheOrDB(Long productId) {
        StockEntity stock = cacheUtil.getOrLoad(StockCacheConstraints.PRODUCT_KEY.getKey(productId),
                () -> stockRepository.findByProductId(productId).orElse(StockMapper.createEntity(productId)),
                StockCacheDurationConstraints.DAY.toDuration());
        return Optional.ofNullable(stock);
    }

    public Optional<StockEntity> fallbackStockCache(Long productId, Throwable t) {
        log.error("Redis not available for product {}, falling back to DB. Error: {}",productId, t.getMessage());
        return  Optional.empty();
    }


    @Override
    @CircuitBreaker(name = "redisBreaker", fallbackMethod = "fallbackClearStockCache")
    @Retry(name = "redisRetry", fallbackMethod = "fallbackClearStockCache")
    public void clearStockCache(Long productId) {
        cacheUtil.deleteFromCache(StockCacheConstraints.PRODUCT_KEY.getKey(productId));
        log.debug("Cache cleared for product {}",  productId);
    }

    public void fallbackClearStockCache(Long productId, Throwable t) {
        log.warn("Redis not available to clear cache for product {}, ignoring. Error: {}", productId, t.getMessage());
    }

}
