package com.github.matheusbucater.quadras_smc.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.matheusbucater.quadras_smc.dto.EmailDTO;
import com.github.matheusbucater.quadras_smc.enums.TipoDeUsuario;
import com.github.matheusbucater.quadras_smc.enums.TokenPurpose;
import com.github.matheusbucater.quadras_smc.exception.TokenCreationFailedException;
import com.github.matheusbucater.quadras_smc.exception.TokenValidationFailedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;


@Service
public class TokenService {

    @Value("${api.security.token.secret.auth}")
    private String authSecret;
    @Value("${api.security.token.secret.pwd}")
    private String pwdSecret;
    @Value("${api.security.token.secret.vrfact}")
    private String vrfactSecret;
    @Value("${api.security.token.secret.email}")
    private String emailSecret;

    @Value("${api.security.token.expiration.auth}")
    private Integer authExpirationInHours;

    public String generateAuthToken(String email, TipoDeUsuario tipoDeUsuario) {

        return this.createToken(email, TokenPurpose.AUTHENTICATION, tipoDeUsuario, authExpirationInHours, this.authSecret);
    }

    public String extractEmailFromAuthToken(String token) {

        DecodedJWT jwt = this.verify(token, this.authSecret);

        return jwt.getSubject();
    }

    public TipoDeUsuario extractRoleFromAuthToken(String token) {

        DecodedJWT jwt = this.verify(token, this.authSecret);
        return jwt.getClaim("role").as(TipoDeUsuario.class);
    }

    public String refreshAuthToken(String token) {

        DecodedJWT jwt = this.verify(token, this.authSecret);
        String email = jwt.getSubject();
        TipoDeUsuario role = extractRoleFromAuthToken(token);

        return this.createToken(email, TokenPurpose.AUTHENTICATION, role,authExpirationInHours, this.authSecret);
    }

    public String generateVerifyAccountToken(String email) {
        return this.createToken(email, TokenPurpose.VERIFY_ACCOUNT, TipoDeUsuario.JOGADOR, 2, this.vrfactSecret);
    }

    public String validateVerifyAccountToken(String token) {

        DecodedJWT jwt = this.verify(token, this.vrfactSecret);

        return jwt.getSubject();
    }

    public EmailDTO validateEmailToken(String token) {

        DecodedJWT jwt = this.verify(token, this.emailSecret);

        return new EmailDTO(
                jwt.getClaim("addr_to").asString(),
                jwt.getClaim("subject").asString(),
                jwt.getClaim("body").asString()
        );
    }

    public String generatePasswordResetToken(String email) {

        // TODO! - gravar no banco de dados ???????

        // TODO! - handle Role claim in jwt token
        return this.createToken(email, TokenPurpose.PASSWORD_RESET, TipoDeUsuario.JOGADOR, 1, this.pwdSecret);
    }

    public String validatePasswordResetToken(String token) {

        // TODO! - buscar do banco de dados?????

        DecodedJWT jwt = this.verify(token, this.pwdSecret);

        return jwt.getSubject();
    }

    private String createToken(
            String email,
            TokenPurpose purpose,
            TipoDeUsuario role,
            Integer expiresInHours,
            String secret
    ) {

        try {
            return JWT.create()
                    .withIssuer("qsmc-auth-api")
                    .withSubject(email)
                    .withClaim("purpose", purpose.toString())
                    .withClaim("role", role.toString())
                    .withExpiresAt(LocalDateTime.now()
                            .plusHours(expiresInHours).toInstant(ZoneOffset.of("-03:00")))
                    .sign(Algorithm.HMAC256(secret));
        } catch (JWTCreationException ex) {
            throw new TokenCreationFailedException(ex.getMessage());
        }
    }

    private DecodedJWT verify(String token, String secret) {
        try {
            return JWT.require(Algorithm.HMAC256(secret))
                    .withIssuer("qsmc-auth-api")
                    .build()
                    .verify(token);
        } catch (JWTVerificationException ex) {
            throw new TokenValidationFailedException(ex.getMessage());
        }

    }
}
