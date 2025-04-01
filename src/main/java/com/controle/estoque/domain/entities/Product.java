package com.controle.estoque.domain.entities;

import com.controle.estoque.domain.enums.Category;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;


@Data
@Entity
@Table(name = "produtos")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", nullable = false)
    private Category categoria;

    @Column(name = "valor_unidade", nullable = false)
    private BigDecimal preco;

    @Column(name = "quantidade_disponivel", nullable = false)
    private Integer quantidadeDisponivel;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL)
    private List<Sale> vendas;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL)
    private List<StockMovement> movimentacoes;

    public Product() {
    }

    public Product(String nome, Category categoria, BigDecimal preco, Integer quantidadeDisponivel) {
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
        this.quantidadeDisponivel = quantidadeDisponivel;
    }
}
