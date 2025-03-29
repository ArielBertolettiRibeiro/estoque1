package com.controle.estoque.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "movimentacoes_estoque")
public class MovimentacaoEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @Column(name = "quantidade_movimentada", nullable = false)
    private int quantidadeMovimentada;

    @Enumerated(EnumType.STRING)
    private TipoMovimentacao tipo;

    @Column(name = "data_movimentacao", nullable = false, updatable = false)
    private LocalDateTime dataMovimentacao = LocalDateTime.now();

    public MovimentacaoEstoque(Produto produto, int quantidadeMovimentada, TipoMovimentacao tipo) {
        this.produto = produto;
        this.quantidadeMovimentada = quantidadeMovimentada;
        this.tipo = tipo;
        this.dataMovimentacao = LocalDateTime.now();
    }
}
