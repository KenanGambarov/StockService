package com.stockservice.controller;

import com.stockservice.dto.request.StockRequestDto;
import com.stockservice.dto.response.StockResponseDto;
import com.stockservice.services.StockService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@RestController
@RequestMapping("v1/stock/")
public class StockController {

    private StockService stockService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addProductToCart(@Validated @RequestBody StockRequestDto requestDto){
        stockService.addProductToStock(requestDto);
    }

//    @DeleteMapping("/{productId}/delete")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void deleteProduct(@PathVariable Long productId, int amount){
//        stockService.deleteProductFromStock(productId, amount);
//    }

    @GetMapping("/products/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public StockResponseDto getProducts(@PathVariable Long productId){
        return stockService.getProductsFromStock(productId);
    }

}
