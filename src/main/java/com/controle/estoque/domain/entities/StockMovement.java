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
@Table(name = "stock_movements")
public class StockMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "moved_quantity", nullable = false)
    private int movedQuantity;

    @Enumerated(EnumType.STRING)
    private MovementType type;

    @Column(name = "movement_date", nullable = false, updatable = false)
    private LocalDateTime movementDate = LocalDateTime.now();

    public StockMovement(Product product, int movedQuantity, MovementType type) {
        this.product = product;
        this.movedQuantity = movedQuantity;
        this.type = type;
        this.movementDate = LocalDateTime.now();
    }
}
