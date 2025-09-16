package com.controle.estoque.shared.exceptions;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    String code();
    String i18nKey();
    HttpStatus httpStatus();
}
