package com.controle.estoque.infrastructure.repository;

import com.controle.estoque.domain.entities.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    // Busca apenas produtos com quantidade disponível maior que 0
    Page<Produto> findByQuantidadeDisponivelGreaterThan(Integer quantidade, Pageable pageable);

    // Busca um produto exato pelo nome
    Optional<Produto> findByNomeContainingIgnoreCase(String nome);
}
