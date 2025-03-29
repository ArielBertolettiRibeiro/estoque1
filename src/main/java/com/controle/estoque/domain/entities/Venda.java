package com.controle.estoque.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Entity
@Table(name = "vendas")
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantidade_vendida", nullable = false)
    private Integer quantidadeVendida;

    @Column(name = "data_venda", updatable = false)
    private LocalDateTime dataVenda = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    public Venda() {
    }

    public Venda(Integer quantidadeVendida, Produto produto) {
        this.quantidadeVendida = quantidadeVendida;
        this.dataVenda = LocalDateTime.now();
        this.produto = produto;
    }


}
