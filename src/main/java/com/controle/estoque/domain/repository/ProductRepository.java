package com.controle.estoque.domain.repository;

import com.controle.estoque.domain.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductRepository {

    Product save(Product product);
    Optional<Product> findById(Long id);
    Page<Product> findAll(Pageable pageable);
    Optional<Integer> findAvailableQuantityById(Long id);
    Page<Product> findByCategory(String category, Pageable pageable);
    Page<Product> findByName(String name, Pageable pageable);
    Page<Product> findAvailableProducts(Pageable pageable);
}
