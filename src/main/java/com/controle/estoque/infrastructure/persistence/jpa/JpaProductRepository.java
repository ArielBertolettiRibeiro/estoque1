package com.controle.estoque.infrastructure.persistence.jpa;

import com.controle.estoque.domain.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByIdAndActiveTrue(Long id);
    Page<Product> findAllByActiveTrue(Pageable pageable);
    Optional<Integer> findAvailableQuantityByIdAndActiveTrue(Long id);
    Page<Product> findAllByCategoryAndActiveTrue(String category, Pageable pageable);
    Page<Product> findAllByNameContainingIgnoreCaseAndActiveTrue(String name, Pageable pageable);
    Page<Product> findAllByAvailableQuantityGreaterThanAndActiveTrue(int minQuantity, Pageable pageable);
}
