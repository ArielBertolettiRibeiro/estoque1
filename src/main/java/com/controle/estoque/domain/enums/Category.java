package com.controle.estoque.domain.enums;

public enum Category {

    ELETRONICOS(1, "Eletronicos"),
    ALIMENTOS(2, "Alimentos"),
    ESPORTES(3, "Esportes"),
    LAZER(4, "Lazer");

    private final Integer number;
    private final String name;

    Category(Integer number, String name) {
        this.number = number;
        this.name = name;
    }
}
