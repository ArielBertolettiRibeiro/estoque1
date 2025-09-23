package com.controle.estoque.application.product.usecase;

import com.controle.estoque.application.product.dto.StockQuantityResponse;
import com.controle.estoque.application.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindQuantityStockUseCase {

    private final ProductService service;

    public StockQuantityResponse execute(Long id) {
        Integer quantity = service.findAvailableQuantityById(id);
        return new StockQuantityResponse(id, quantity);
    }
}
