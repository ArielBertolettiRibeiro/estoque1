package com.controle.estoque.infrastructure.logging;

import jakarta.servlet.*;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.UUID;

@Component
public class CorrelationIdFilter implements Filter {

    public static final String CORRELATION_ID = "traceId";

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
        try {
            MDC.put(CORRELATION_ID, UUID.randomUUID().toString());
            filterChain.doFilter(req, res);
        } finally {
            MDC.remove(CORRELATION_ID);
        }
    }
}
