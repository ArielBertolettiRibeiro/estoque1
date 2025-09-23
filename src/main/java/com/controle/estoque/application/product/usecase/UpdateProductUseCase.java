package com.controle.estoque.application.product.usecase;

import com.controle.estoque.application.product.dto.ProductResponse;
import com.controle.estoque.application.product.dto.ProductUpdateRequest;
import com.controle.estoque.application.product.service.ProductService;
import com.controle.estoque.domain.model.Product;
import com.controle.estoque.shared.exceptions.BusinessException;
import com.controle.estoque.shared.exceptions.codes.ProductError;
import com.controle.estoque.shared.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class UpdateProductUseCase {

    private final ProductService service;
    private final ProductMapper mapper;

    public ProductResponse execute(Long id, ProductUpdateRequest request) {
        Product product = service.findById(id);

        if (request.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException(ProductError.INVALID_PRICE, new Object[]{request.getPrice()});
        }
        if (request.getAvailableQuantity() < 0) {
            throw new BusinessException(ProductError.INVALID_QUANTITY, new Object[]{request.getAvailableQuantity()});
        }

        mapper.update(request, product);
        return mapper.toResponse(service.save(product));
    }
}
