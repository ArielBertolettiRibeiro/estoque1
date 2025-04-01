package com.controle.estoque.domain.entities;

import com.controle.estoque.domain.enums.MovementType;
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
public class StockMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Product produto;

    @Column(name = "quantidade_movimentada", nullable = false)
    private int quantidadeMovimentada;

    @Enumerated(EnumType.STRING)
    private MovementType tipo;

    @Column(name = "data_movimentacao", nullable = false, updatable = false)
    private LocalDateTime dataMovimentacao = LocalDateTime.now();

    public StockMovement(Product produto, int quantidadeMovimentada, MovementType tipo) {
        this.produto = produto;
        this.quantidadeMovimentada = quantidadeMovimentada;
        this.tipo = tipo;
        this.dataMovimentacao = LocalDateTime.now();
    }
}
