package com.controle.estoque.domain.entities;

public enum TipoMovimentacao {

    ENTRADA(1, "Entrada"),
    SAIDA(2,"Saída");

    private final Integer tipo;
    private final String nome;

    TipoMovimentacao(Integer tipo, String nome) {
        this.tipo = tipo;
        this.nome = nome;
    }
}
