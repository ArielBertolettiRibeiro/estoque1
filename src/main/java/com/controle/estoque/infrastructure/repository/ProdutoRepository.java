package com.controle.estoque.infrastructure.repository;

import com.controle.estoque.domain.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    // Busca apenas produtos com quantidade disponível maior que 0
    List<Produto> findByQuantidadeDisponivelGreaterThan(Integer quantidade);

    // Busca um produto exato pelo nome
    Produto findByNomeContainingIgnoreCase(String nome);
}
