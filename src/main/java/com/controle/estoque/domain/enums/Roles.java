package com.controle.estoque.domain.enums;

public enum Roles {

    ADMIN("Admin"),
    USER("User");

    private String name;

    Roles(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
