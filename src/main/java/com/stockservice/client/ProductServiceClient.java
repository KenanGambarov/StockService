package com.stockservice.client;

import com.stockservice.client.decoder.ClientServiceErrorDecoder;
import com.stockservice.dto.product.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", url = "${client.product-service.url}",
        path = "/internal/v1",configuration = ClientServiceErrorDecoder.class)
public interface ProductServiceClient {


    @GetMapping("/products/{id}")
    ProductDto getProductById(@PathVariable("id") Long id);

}
