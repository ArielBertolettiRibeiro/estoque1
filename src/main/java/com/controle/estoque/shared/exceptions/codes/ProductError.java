package com.controle.estoque.shared.exceptions.codes;

import com.controle.estoque.shared.exceptions.ErrorCode;

public enum ProductError implements ErrorCode {
    NOT_FOUND("PRODUCT_NOT_FOUND", "product.not.found");


    private final String code;
    private final String key;

    ProductError(String code, String key) {
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
