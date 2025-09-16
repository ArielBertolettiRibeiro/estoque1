package com.controle.estoque.application.product.usecase;


import com.controle.estoque.application.product.dto.ProductRequest;
import com.controle.estoque.application.product.dto.ProductResponse;
import com.controle.estoque.infrastructure.config.mapper.ProductMapper;
import com.controle.estoque.infrastructure.persistence.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateProductUseCase {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public ProductResponse execute(ProductRequest request) {
        var entity = mapper.toEntity(request);
        var saved = repository.save(entity);
        return mapper.toResponse(saved);
    }
}
