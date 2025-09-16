package com.controle.estoque.application.auth.dto;

import com.controle.estoque.domain.enums.Roles;

public record RegisterDTO(String email, String password, Roles role) {
}
