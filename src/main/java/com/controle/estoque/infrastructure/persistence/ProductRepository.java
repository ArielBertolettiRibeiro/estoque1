package com.controle.estoque.infrastructure.persistence;

import com.controle.estoque.domain.model.Product;
import com.controle.estoque.domain.enums.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByAvailableQuantityGreaterThan(Integer quantity, Pageable pageable);

    Optional<Product> findByNameContainingIgnoreCase(String name);

    Optional<Product> findByNameAndCategoryAndPrice(
            String name, Category category, BigDecimal price);
}
