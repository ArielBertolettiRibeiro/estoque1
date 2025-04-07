package com.controle.estoque.domain.enums;

public enum MovementType {

    ENTRADA(1, "Entrada"),
    SAIDA(2,"Saída");

    private final Integer type;
    private final String name;

    MovementType(Integer type, String name) {
        this.type = type;
        this.name = name;
    }
}
