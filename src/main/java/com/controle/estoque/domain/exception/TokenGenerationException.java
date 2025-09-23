package com.controle.estoque.domain.exception;

import com.controle.estoque.shared.exceptions.BusinessException;
import com.controle.estoque.shared.exceptions.codes.TokenError;

public class TokenGenerationException extends BusinessException {
    public TokenGenerationException(Object... args) {
        super(TokenError.GENERATION_FAILED, args);
    }
}
