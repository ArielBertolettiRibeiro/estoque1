package com.controle.estoque.application.product.usecase;

import com.controle.estoque.application.product.dto.ProductResponse;
import com.controle.estoque.application.product.service.ProductService;
import com.controle.estoque.domain.model.Product;
import com.controle.estoque.shared.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindByProductUseCase {

    private final ProductService service;
    private final ProductMapper mapper;

    public ProductResponse execute(Long id) {
        Product product = service.findById(id);
        return mapper.toResponse(product);
    }
}
