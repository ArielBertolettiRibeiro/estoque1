package com.controle.estoque.infrastructure.config.mapper;

import com.controle.estoque.domain.entities.Product;
import com.controle.estoque.adapters.dto.requestDTO.ProductRequest;
import com.controle.estoque.adapters.dto.responseDTO.ProductResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    private final ModelMapper modelMapper;

    public ProductMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ProductRequest toRequest(Product product) {
        return modelMapper.map(product, ProductRequest.class);
    }

    public Product toEntity(ProductRequest productRequest) {
        return modelMapper.map(productRequest, Product.class);
    }

    public ProductResponse toResponse(Product product) {
        return modelMapper.map(product, ProductResponse.class);
    }

    public List<ProductResponse> toResponseList(List<Product> products) {
        return products.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
