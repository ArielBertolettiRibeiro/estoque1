package com.controle.estoque.infrastructure.config.mapper;

import com.controle.estoque.domain.entities.Product;
import com.controle.estoque.adapters.dto.productDTO.ProductRequest;
import com.controle.estoque.adapters.dto.productDTO.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class ProductMapper {

    private final ModelMapper modelMapper;

    public Product toEntity(ProductRequest productRequest) {
        return modelMapper.map(productRequest, Product.class);
    }

    public ProductResponse toResponse(Product product) {
        return modelMapper.map(product, ProductResponse.class);
    }

}
