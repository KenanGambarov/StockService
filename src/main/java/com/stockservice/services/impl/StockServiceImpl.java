package com.stockservice.services.impl;

import com.stockservice.client.ProductServiceClient;
import com.stockservice.dto.enums.StockChangeType;
import com.stockservice.dto.enums.StockChangeTypeDescription;
import com.stockservice.dto.product.ProductDto;
import com.stockservice.dto.request.StockRequestDto;
import com.stockservice.dto.response.StockResponseDto;
import com.stockservice.entity.StockEntity;
import com.stockservice.exception.ExceptionConstants;
import com.stockservice.exception.OutOfStockException;
import com.stockservice.mapper.StockMapper;
import com.stockservice.queue.QueueSender;
import com.stockservice.repository.StockLogRepository;
import com.stockservice.repository.StockRepository;
import com.stockservice.services.StockCacheService;
import com.stockservice.services.StockService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.stockservice.dto.enums.RabbitQueueType.QUEUE_NAME;
import static com.stockservice.exception.ExceptionConstants.PRODUCT_OUT_OF_STOCK;

@Slf4j
@Service
@AllArgsConstructor
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final StockLogRepository stockLogRepository;
    private final StockCacheService stockCacheService;
    private final ProductServiceClient productServiceClient;
    private final QueueSender queueSender;



    @Override
    @Transactional
    public void addProductToStock(StockRequestDto stockDto) {
        getProduct(stockDto.getProductId());

        StockEntity stock = findStockOrThrow(stockDto.getProductId());
        stock.setQuantity(stock.getQuantity() + stockDto.getQuantity());
        stock = stockRepository.save(stock);
        logStockChange(StockChangeType.INCREASE.name().toLowerCase(),stock,stock.getQuantity());

        saveStockLog(stock,StockChangeType.INCREASE,stock.getQuantity(),StockChangeTypeDescription.PRODUCT_ADDED.getDescription());
        queueSender.sendStockUpdate(QUEUE_NAME.getQueueName(),StockMapper.getRequestDto(stock));
        stockCacheService.clearStockCache(stock.getProductId());
    }

    @Override
    @Transactional
    public void deleteProductFromStock(Long productId, int amount) {
        StockEntity stock = findStockOrThrow(productId);

        if (stock.getQuantity() < amount) {
            throw new OutOfStockException(PRODUCT_OUT_OF_STOCK.getMessage());
        }

        stock.setQuantity(stock.getQuantity() - amount);
        stockRepository.save(stock);
        logStockChange(StockChangeType.DECREASE.name().toLowerCase(),stock,stock.getQuantity());

        saveStockLog(stock,StockChangeType.DECREASE,stock.getQuantity(),StockChangeTypeDescription.PRODUCT_SOLD.getDescription());
        queueSender.sendStockUpdate(QUEUE_NAME.getQueueName(),StockMapper.getRequestDto(stock));
        stockCacheService.clearStockCache(productId);
    }

    @Override
    public StockResponseDto getProductsFromStock(Long productId) {
        ProductDto product = getProduct(productId);
        StockEntity stock = findStockOrThrow(productId);
        return StockMapper.getResponseStockDto(stock,product);
    }

    private ProductDto getProduct(Long productId){
        ProductDto product = productServiceClient.getProductById(productId);
        log.info("Feign client product {} ", product);
        return product;
    }

    private StockEntity findStockOrThrow(Long productId) {
        return stockCacheService.getStockFromCacheOrDB(productId)
                .orElseThrow(()-> new OutOfStockException(ExceptionConstants.PRODUCT_OUT_OF_STOCK.getMessage()));
    }

    private void logStockChange(String action, StockEntity stock, int amount) {
        log.info("Product [{}] stock {}. New quantity: {}, Amount changed: {}",
                stock.getProductId(), action, stock.getQuantity(), amount);
    }

    private void saveStockLog(StockEntity stock, StockChangeType changeType,
                              int amount, String description) {
        stockLogRepository.save(
                StockMapper.createEntity(stock, changeType, amount, description)
        );
        log.info("StockLog saved. productId: {}, changeType: {}, amount: {}",
                stock.getProductId(), changeType, amount);
    }
}
