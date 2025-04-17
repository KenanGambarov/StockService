package com.stockservice.services;

import com.stockservice.dto.request.StockRequestDto;
import com.stockservice.dto.response.StockResponseDto;

public interface StockService {

    void addProductToStock(StockRequestDto stockDto);

    void deleteProductFromStock(Long productId, int amount);

    StockResponseDto getProductsFromStock(Long userId);

}
