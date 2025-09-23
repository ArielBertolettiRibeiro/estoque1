package com.controle.estoque.shared.exceptions.codes;

import com.controle.estoque.shared.exceptions.ErrorCode;
import org.springframework.http.HttpStatus;

public enum TokenError implements ErrorCode {

    INVALID("TOKEN.INVALID", "token.invalid", HttpStatus.UNAUTHORIZED),
    VALIDATION_FAILED("TOKEN.VALIDATION_FAILED", "token.validation.failed", HttpStatus.UNAUTHORIZED),
    GENERATION_FAILED("TOKEN.GENERATION_FAILED", "token.generation.failed", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String key;
    private final HttpStatus status;

    TokenError(String code, String key, HttpStatus status) {
        this.code = code;
        this.key = key;
        this.status = status;
    }


    @Override
    public String code() {
        return code;
    }

    @Override
    public String i18nKey() {
        return key;
    }

    @Override
    public HttpStatus httpStatus() {
        return status;
    }
}
