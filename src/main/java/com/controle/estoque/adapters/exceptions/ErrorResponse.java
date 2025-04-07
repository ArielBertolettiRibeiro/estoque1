package com.controle.estoque.adapters.exceptions;

import java.time.LocalDateTime;

public record ErrorResponse(String message, LocalDateTime date, int status, String path) {
}
