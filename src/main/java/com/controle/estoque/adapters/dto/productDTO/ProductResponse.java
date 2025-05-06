package com.controle.estoque.adapters.dto.productDTO;

import com.controle.estoque.domain.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductResponse {

    private Long id;
    private String name;
    private Category category;
    private BigDecimal price;
    private Integer availableQuantity;
}
