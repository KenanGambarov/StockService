package com.stockservice.mapper;

import com.stockservice.dto.product.ProductDto;
import com.stockservice.dto.request.StockRequestDto;
import com.stockservice.dto.response.StockResponseDto;
import com.stockservice.entity.StockEntity;

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
}
