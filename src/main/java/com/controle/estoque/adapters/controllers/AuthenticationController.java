package com.controle.estoque.adapters.controllers;

import com.controle.estoque.application.auth.dto.AuthenticationDTO;
import com.controle.estoque.application.auth.dto.LoginResponseDTO;
import com.controle.estoque.application.auth.dto.RegisterDTO;
import com.controle.estoque.domain.model.User;
import com.controle.estoque.domain.repository.UserRepository;
import com.controle.estoque.infrastructure.security.token.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("auth")
public class AuthenticationController {

    private final UserRepository repository;
    private final AuthenticationManager manager;
    private final TokenService service;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO dto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        var auth = manager.authenticate(usernamePassword);

        var token = service.generationToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO dto) {
        if (repository.findByEmail(dto.email()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());
        User newUser = new User(dto.email(), encryptedPassword, dto.role());

        repository.save(newUser);

        return ResponseEntity.ok().build();
    }
}
