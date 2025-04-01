package com.example.cards.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class JwtService {
    private final static String key = "X7k9p2mQz8vL4nJtY6rW3sB5uA1eF9gD12442434234";
    public String generateToken(String cardNumber) {
        return Jwts.builder()
                .setSubject(cardNumber)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 6_048_000))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }
}
