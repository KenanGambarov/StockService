package com.stockservice.mapper;

import com.stockservice.dto.enums.StockChangeType;
import com.stockservice.dto.product.ProductDto;
import com.stockservice.dto.request.StockRequestDto;
import com.stockservice.dto.response.StockResponseDto;
import com.stockservice.entity.StockEntity;
import com.stockservice.entity.StockLogEntity;

public class StockMapper {

    public static StockResponseDto getResponseStockDto(StockEntity stock, ProductDto productDto){
        return StockResponseDto.builder()
                .product(productDto)
                .quantity(stock.getQuantity())
                .createdAt(stock.getCreatedAt())
                .updatedAt(stock.getUpdatedAt())
                .build();
    }

    public static StockRequestDto getRequestDto(StockEntity stock){
        return StockRequestDto.builder()
                .productId(stock.getProductId())
                .quantity(stock.getQuantity())
                .build();
    }


    public static StockEntity createEntity(Long productId){
        return StockEntity.builder()
                .productId(productId)
                .build();
    }

    public static StockLogEntity createEntity(StockEntity stock, StockChangeType changeType,
                                              int amount, String description){
        return StockLogEntity.builder()
                .stock(stock)
                .changeType(changeType)
                .amount(amount)
                .description(description)
                .build();
    }

}
