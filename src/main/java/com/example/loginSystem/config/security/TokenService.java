package com.example.loginSystem.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.loginSystem.domain.entities.Users;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${jwt_secret_password}")
    private String jwtSecret;

    @Value("${jwt_data_expiration_access_token}")
    private int expiresAccessToken;

    @Value("${jwt_data_expiration_refresh_token}")
    private int expiresRefreshToken;

    public String generateAccessToken(Users users){
        String access_token = JWT.create()
                .withSubject(users.getUsername())
                .withClaim("id",users.getId())
                .withIssuer("/api/auth")
                .withExpiresAt(new Date(System.currentTimeMillis() + expiresAccessToken))
                .sign(Algorithm.HMAC512(jwtSecret));
        return access_token;
    }

    public String generateRefreshToken(Users users){
        String refresh_token = JWT.create()
                .withSubject(users.getUsername())
                .withIssuer("/api/auth")
                .withExpiresAt(new Date(System.currentTimeMillis() + expiresRefreshToken))
                .sign(Algorithm.HMAC512(jwtSecret));
        return refresh_token;
    }

    public String getSubject(String token){
        return JWT.require(Algorithm.HMAC512(jwtSecret))
                .withIssuer("/api/auth")
                .build().verify(token)
                .getSubject();
    }

    public Date getExpirationData(String token){
        return JWT.require(Algorithm.HMAC512(jwtSecret))
                .withIssuer("/api/auth")
                .build().verify(token)
                .getExpiresAt();
    }

    public boolean isTokenValid(String token, Users users){
        String username = getSubject(token);
        return username.equals(users.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token){
        Date expirationDate = getExpirationData(token);
        return expirationDate.before(new Date());
    }
}
