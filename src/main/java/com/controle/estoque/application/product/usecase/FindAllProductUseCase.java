package com.controle.estoque.application.product.usecase;

import com.controle.estoque.application.product.dto.ProductResponse;
import com.controle.estoque.application.product.service.ProductService;
import com.controle.estoque.shared.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindAllProductUseCase {

    private final ProductService service;
    private final ProductMapper mapper;

    public Page<ProductResponse> execute(Pageable pageable) {
        return service.findAll(pageable).map(mapper::toResponse);
    }
}
