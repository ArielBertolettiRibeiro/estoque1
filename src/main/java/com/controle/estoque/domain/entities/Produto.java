package com.controle.estoque.domain.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(name = "produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", nullable = false)
    private Categoria categoria;

    @Column(name = "valor_unidade", nullable = false)
    private BigDecimal preco;

    @Column(name = "quantidade_disponivel", nullable = false)
    private Integer quantidadeDisponivel;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL)
    private List<Venda> vendas;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL)
    private List<MovimentacaoEstoque> movimentacoes;

    public Produto() {
    }

    public Produto(String nome, Categoria categoria, BigDecimal preco, Integer quantidadeDisponivel) {
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
        this.quantidadeDisponivel = quantidadeDisponivel;
    }
}
