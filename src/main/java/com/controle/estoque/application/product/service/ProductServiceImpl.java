package com.controle.estoque.application.product.service;

import com.controle.estoque.domain.model.Product;
import com.controle.estoque.domain.repository.ProductRepository;
import com.controle.estoque.shared.exceptions.BusinessException;
import com.controle.estoque.shared.exceptions.codes.ProductError;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    @Override
    public Product save(Product product) {
        return repository.save(product);
    }

    @Override
    public Product findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BusinessException(
                        ProductError.NOT_FOUND, new Object[]{id}
                ));
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<Product> findByCategory(String category, Pageable pageable) {
        return repository.findByCategory(category, pageable);
    }

    @Override
    public Page<Product> findByName(String name, Pageable pageable) {
        return repository.findByName(name, pageable);
    }

    @Override
    public Page<Product> findAvailableProducts(Pageable pageable) {
        return repository.findAvailableProducts(pageable);
    }

    @Override
    public Integer findAvailableQuantityById(Long id) {
        return repository.findAvailableQuantityById(id)
                .orElseThrow(() -> new BusinessException(
                        ProductError.NOT_FOUND, new Object[]{id}
                ));
    }
}
