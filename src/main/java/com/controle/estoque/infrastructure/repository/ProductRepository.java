package com.controle.estoque.infrastructure.repository;

import com.controle.estoque.domain.entities.Product;
import com.controle.estoque.domain.enums.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByQuantidadeDisponivelGreaterThan(Integer quantidade, Pageable pageable);

    Optional<Product> findByNomeContainingIgnoreCase(String nome);

    Optional<Product> findByNomeAndCategoriaAndPrecoAndQuantidadeDisponivel(
            String nome, Category categoria, BigDecimal preco, Integer quantidadeDisponivel);
}
