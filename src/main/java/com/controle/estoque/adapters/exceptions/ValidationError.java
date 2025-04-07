package com.controle.estoque.adapters.exceptions;

import org.springframework.validation.FieldError;

public record ValidationError(String field, String message) {
    public ValidationError(FieldError erro) {
        this(erro.getField(), erro.getDefaultMessage());
    }
}
