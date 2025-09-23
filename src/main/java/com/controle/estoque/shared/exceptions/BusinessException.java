package com.controle.estoque.shared.exceptions;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{

    private final ErrorCode errorCode;
    private final Object[] args;

    public BusinessException(ErrorCode errorCode, Object[] args) {
        this.errorCode = errorCode;
        this.args = args;
    }

    public String getCode() {
        return errorCode.code();
    }

    public String getI18nKey() {
        return errorCode.i18nKey();
    }
}
