package com.controle.estoque.infrastructure.config.handlers;

import org.springframework.validation.FieldError;

public record ValidationError(String field, String message) {

    public ValidationError(FieldError erro) {
        this(erro.getField(), erro.getDefaultMessage());
    }
}
