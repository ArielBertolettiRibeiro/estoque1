package com.controle.estoque.infrastructure.persistence.impl;

import com.controle.estoque.domain.model.Product;
import com.controle.estoque.domain.repository.ProductRepository;
import com.controle.estoque.infrastructure.persistence.jpa.JpaProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final JpaProductRepository repository;

    @Override
    public Product save(Product product) {
        return repository.save(product);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return repository.findByIdAndActiveTrue(id);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return repository.findAllByActiveTrue(pageable);
    }

    @Override
    public Optional<Integer> findAvailableQuantityById(Long id) {
        return repository.findAvailableQuantityByIdAndActiveTrue(id);
    }

    @Override
    public Page<Product> findByCategory(String category, Pageable pageable) {
        return repository.findAllByCategoryAndActiveTrue(category, pageable);
    }

    @Override
    public Page<Product> findByName(String name, Pageable pageable) {
        return repository.findAllByNameContainingIgnoreCaseAndActiveTrue(name, pageable);
    }

    @Override
    public Page<Product> findAvailableProducts(Pageable pageable) {
        return repository.findAllByAvailableQuantityGreaterThanAndActiveTrue(0,pageable);
    }
}
