package com.stockservice.dto.product;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {

    private String name;

    private String description;

    private Double price;

    private ProductCategoryDto categoryDto;

}
