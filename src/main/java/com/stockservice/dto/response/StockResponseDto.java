package com.stockservice.dto.response;

import com.stockservice.dto.product.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockResponseDto implements Serializable {

    private ProductDto product;

    private Integer quantity;

    private Date createdAt;

    private Date updatedAt;

}
