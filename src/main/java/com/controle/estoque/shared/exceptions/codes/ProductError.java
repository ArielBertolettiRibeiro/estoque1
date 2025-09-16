package com.controle.estoque.shared.exceptions.codes;

import com.controle.estoque.shared.exceptions.ErrorCode;
import org.springframework.http.HttpStatus;

public enum ProductError implements ErrorCode {

    NOT_FOUND("PRODUCT_NOT_FOUND", "product.not.found", HttpStatus.NOT_FOUND);


    private final String code;
    private final String key;
    private final HttpStatus status;

    ProductError(String code, String key, HttpStatus status) {
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
