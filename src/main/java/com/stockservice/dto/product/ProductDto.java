package com.stockservice.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto implements Serializable {

    private String name;

    private String description;

    private Double price;

    private ProductCategoryDto categoryDto;

}
