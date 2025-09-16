package com.controle.estoque.application.product.dto;

import com.controle.estoque.domain.enums.Category;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductRequest {

    @NotBlank(message = "Name is required.")
    private String name;

    @NotNull(message = "Category is required.")
    private Category category;

    @NotNull(message = "Price is required.")
    @DecimalMin(value = "0.01", message = "Price must be greater than zero.")
    private BigDecimal price;

    @NotNull(message = "Available quantity is required.")
    @Min(value = 0, message = "Available quantity cannot be negative.")
    private Integer availableQuantity;
}
