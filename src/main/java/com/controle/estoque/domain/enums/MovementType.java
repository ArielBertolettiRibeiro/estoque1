package com.controle.estoque.domain.enums;

public enum MovementType {

    ENTRADA(1, "Entrada"),
    SAIDA(2,"Saída");

    private final Integer tipo;
    private final String nome;

    MovementType(Integer tipo, String nome) {
        this.tipo = tipo;
        this.nome = nome;
    }
}
