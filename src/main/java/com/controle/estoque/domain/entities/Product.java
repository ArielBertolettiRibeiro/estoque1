package com.controle.estoque.domain.entities;

import com.controle.estoque.domain.enums.Category;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "produtos")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

    @Column(name = "unit_price", nullable = false)
    private BigDecimal price;

    @Column(name = "availableQuantity", nullable = false)
    private Integer availableQuantity;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Sale> sales;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<StockMovement> stockMovements;

    public Product(String name, Category category, BigDecimal price, Integer availableQuantity) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.availableQuantity = availableQuantity;
    }
}
