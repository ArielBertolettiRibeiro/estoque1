package com.controle.estoque.domain.entities;

public enum Categoria {

    ELETRONICOS(1, "Eletronicos"),
    ALIMENTOS(2, "Alimentos"),
    ESPORTES(3, "Esportes"),
    LAZER(4, "Lazer");

    private final Integer numero;
    private final String nome;

    Categoria(Integer numero, String nome) {
        this.numero = numero;
        this.nome = nome;
    }
}
