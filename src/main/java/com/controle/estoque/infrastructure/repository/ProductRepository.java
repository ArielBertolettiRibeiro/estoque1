package com.controle.estoque.infrastructure.repository;

import com.controle.estoque.domain.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByQuantidadeDisponivelGreaterThan(Integer quantidade, Pageable pageable);

    Optional<Product> findByNomeContainingIgnoreCase(String nome);
}
