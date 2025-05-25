package com.example.domain.ecommerce.security;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

    
    private String secret = "springboot";


    public String generateToken(String username){
        return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
        .signWith(SignatureAlgorithm.HS256, secret)
        .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser().setSigningKey(secret)
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
    }

    public boolean validateToken(String token, String username){
        return extractUsername(token).equals(username);
    }

}
