package com.controle.estoque.shared.exceptions;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{

    private final String code;
    private final String i18nKey;
    private final Object[] args;

    public BusinessException(String code, String i18nKey, String technicalMessage, Object[] args) {
        super(technicalMessage);
        this.code = code;
        this.i18nKey = i18nKey;
        this.args = args;
    }
}
