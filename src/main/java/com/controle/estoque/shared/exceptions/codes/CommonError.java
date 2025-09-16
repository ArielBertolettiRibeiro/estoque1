package com.controle.estoque.shared.exceptions.codes;

import com.controle.estoque.shared.exceptions.ErrorCode;

public enum CommonError implements ErrorCode {

    UNEXPECTED("COMMON.UNEXPECTED", "common.unexpected"),
    VALIDATION("COMMON.VALIDATION", "common.validation"),
    AUTHENTICATION_FAILED("COMMON.AUTHENTICATION_FAILED", "common.authentication.failed"),
    JSON_INVALID("COMMON.JSON_INVALID", "common.json.invalid");

    private final String code;
    private final String key;

    CommonError(String code, String key) {
        this.code = code;
        this.key = key;
    }

    @Override
    public String code() {
        return "";
    }

    @Override
    public String i18nKey() {
        return "";
    }
}
