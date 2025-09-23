package com.controle.estoque.application.product.usecase;

import com.controle.estoque.application.product.service.ProductService;
import com.controle.estoque.domain.model.Product;
import com.controle.estoque.shared.exceptions.BusinessException;
import com.controle.estoque.shared.exceptions.codes.ProductError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeactiveProductUseCase {

    private final ProductService service;

    public void execute(Long id) {
        Product product = service.findById(id);

        if (!product.getActive()) {
            throw new BusinessException(
                    ProductError.ALREADY_DEACTIVATED,
                    new Object[]{id}
            );
        }

        product.setActive(false);
        service.save(product);
    }
}

