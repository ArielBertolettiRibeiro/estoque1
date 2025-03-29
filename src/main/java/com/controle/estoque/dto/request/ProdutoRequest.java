package com.controle.estoque.dto.request;

import com.controle.estoque.domain.entities.Categoria;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProdutoRequest {

    @NotBlank(message = "Nome é obrigatório.")
    private String nome;

    @NotNull(message = "A categoria é obrigatória.")
    private Categoria categoria;

    @NotNull(message = "O preço é obrigatório.")
    @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero.")
    private BigDecimal preco;

    @NotNull(message = "A quantidade disponível é obrigatória.")
    @Min(value = 0, message = "A qauntidade disponível não pode ser negativa.")
    private Integer quantidadeDisponivel;
}
