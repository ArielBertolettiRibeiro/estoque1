package com.controle.estoque.infrastructure.config.handlers;

import java.time.LocalDateTime;

public record ErrorResponse(
        String message,
        LocalDateTime date,
        int status,
        String path) {
}
