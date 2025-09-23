package com.controle.estoque.domain.exception;

import com.controle.estoque.shared.exceptions.BusinessException;
import com.controle.estoque.shared.exceptions.codes.TokenError;

public class TokenValidationException extends BusinessException {
    public TokenValidationException(Object... args) {
        super(TokenError.VALIDATION_FAILED, args);
    }
}
