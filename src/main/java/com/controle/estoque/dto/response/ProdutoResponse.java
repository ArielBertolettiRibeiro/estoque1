package com.controle.estoque.dto.response;

import com.controle.estoque.domain.entities.Categoria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProdutoResponse {

    private Long id;
    private String nome;
    private Categoria categoria;
    private BigDecimal preco;
    private Integer quantidadeDisponivel;
}
