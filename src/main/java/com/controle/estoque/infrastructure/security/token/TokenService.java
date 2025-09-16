package com.controle.estoque.infrastructure.security.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.controle.estoque.application.product.exception.InvalidTokenException;
import com.controle.estoque.application.auth.exception.TokenGenerationException;
import com.controle.estoque.application.auth.exception.TokenValidationException;
import com.controle.estoque.domain.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generationToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("estoque")
                    .withSubject(user.getEmail())
                    .withClaim("id", user.getId())
                    .withExpiresAt(expirationDate())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            throw  new TokenGenerationException("ERRO AO GERAR O TOKEN!");
        }
    }

    public String getSubject(String tokenJWT) {
        try {
            if (tokenJWT == null || tokenJWT.trim().isEmpty()) {
                throw new InvalidTokenException("TOKEN INVÁLIDO OU VAZIO!");
            }

            var algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("estoque")
                    .build()
                    .verify(tokenJWT.trim())
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new TokenValidationException("token inválido ou expirado!");
        }
    }

    private Instant expirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
