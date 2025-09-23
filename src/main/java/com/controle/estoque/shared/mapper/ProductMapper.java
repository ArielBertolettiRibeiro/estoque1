package com.controle.estoque.shared.mapper;

import com.controle.estoque.application.product.dto.ProductRequest;
import com.controle.estoque.application.product.dto.ProductResponse;
import com.controle.estoque.application.product.dto.ProductUpdateRequest;
import com.controle.estoque.domain.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(ProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setCategory(request.getCategory());
        product.setPrice(request.getPrice());
        product.setAvailableQuantity(request.getAvailableQuantity());
        return product;
    }

    public ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getAvailableQuantity(),
                product.getActive()
        );
    }

    public void update(ProductUpdateRequest request, Product product) {
        product.setName(request.getName());
        product.setCategory(request.getCategory());
        product.setPrice(request.getPrice());
        product.setAvailableQuantity(request.getAvailableQuantity());
    }

}
