package com.controle.estoque.infrastructure.http;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.net.URI;
import java.util.Map;
import java.util.Objects;

public class ProblemDatailFactory {

    public  static ProblemDetail build(HttpStatus status, String title, String detail,
                                       String code, String instance, String traceId,
                                       Map<String, Object> extras) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(status, detail);
        pd.setTitle(title);
        pd.setType(URI.create("http://localhost:8081/errors/" + code.toLowerCase()));
        pd.setProperty("code", code);
        pd.setProperty("instance", instance);
        if (traceId != null) pd.setProperty("traceId", traceId);
        if (extras != null) extras.forEach(pd::setProperty);
        return pd;
    }
}
