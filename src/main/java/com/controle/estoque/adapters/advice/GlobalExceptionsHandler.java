package com.controle.estoque.adapters.advice;

import com.controle.estoque.infrastructure.http.ProblemDatailFactory;
import com.controle.estoque.infrastructure.logging.CorrelationIdFilter;
import com.controle.estoque.shared.exceptions.BusinessException;
import com.controle.estoque.shared.exceptions.codes.CommonError;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class GlobalExceptionsHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(BusinessException.class)
    public ProblemDetail handleBusiness(BusinessException ex, HttpServletRequest req) {
        HttpStatus status = ex.getErrorCode().httpStatus();
        String detail = messageSource.getMessage(ex.getI18nKey(), ex.getArgs(), req.getLocale());
        return ProblemDatailFactory.build(
                status,
                "Business rule validation",
                detail,
                ex.getCode(),
                req.getRequestURI(),
                MDC.get(CorrelationIdFilter.CORRELATION_ID),
                null
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        HttpStatus status = CommonError.VALIDATION.httpStatus();
        String detail = messageSource.getMessage(CommonError.VALIDATION.i18nKey(), null, req.getLocale());
        Map<String, Object> extras = Map.of(
                "errors", ex.getBindingResult().getFieldErrors().stream()
                        .map(fe -> Map.of("field", fe.getField(), "message", fe.getDefaultMessage()))
                        .toList()
        );

        return ProblemDatailFactory.build(
                status,
                "Invalid request",
                detail,
                CommonError.VALIDATION.code(),
                req.getRequestURI(),
                MDC.get(CorrelationIdFilter.CORRELATION_ID),
                extras
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleJson(HttpMessageNotReadableException ex, HttpServletRequest req) {
        HttpStatus status = CommonError.JSON_INVALID.httpStatus();
        String detail = messageSource.getMessage(CommonError.JSON_INVALID.i18nKey(), null, req.getLocale());
        return ProblemDatailFactory.build(
                status,
                "Malformed JSON",
                detail,
                CommonError.JSON_INVALID.code(),
                req.getRequestURI(),
                MDC.get(CorrelationIdFilter.CORRELATION_ID),
                null
        );
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleUnexpected(Exception ex, HttpServletRequest req) {
        HttpStatus status = CommonError.UNEXPECTED.httpStatus();
        String detail = messageSource.getMessage(CommonError.UNEXPECTED.i18nKey(), null, Locale.getDefault());
        return ProblemDatailFactory.build(
                status,
                "Unexpected error",
                detail,
                CommonError.UNEXPECTED.code(),
                req.getRequestURI(),
                MDC.get(CorrelationIdFilter.CORRELATION_ID),
                null
        );
    }
}
