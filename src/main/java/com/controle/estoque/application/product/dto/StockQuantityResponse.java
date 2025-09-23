package com.controle.estoque.application.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StockQuantityResponse {

    private Long productId;
    private Integer availableQuantity;
}
