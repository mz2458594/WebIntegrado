package com.example.domain.ecommerce.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

//Clase utilitaria para el manejo de tokens JWT
//Proporciona métodos para generar, validar y extraer información de tokens JWT
@Component
public class JwtUtil {
    
    // Clave secreta para firmar los tokens JWT
    private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    // Tiempo de expiración del token de acceso (24 horas)
    private static final long EXPIRATION_TIME = 1000 * 60 * 24;
    // Tiempo de expiración del refresh token (7 días)
    private static final long REFRESH_EXPIRATION = 1000 * 60 * 24 * 7;

    //Extrae el nombre de usuario del token JWT
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    //Extrae la fecha de expiración del token JWT
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    //Método genérico para extraer cualquier claim del token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //Extrae todos los claims del token JWT
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    //Obtiene la clave de firma para los tokens
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //Verifica si el token ha expirado
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    //Genera un token JWT para un usuario
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    //Genera un token JWT con claims adicionales
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, EXPIRATION_TIME);
    }

    //Genera un refresh token
    public String generateRefreshToken(UserDetails userDetails) {
        return buildToken(new HashMap<>(), userDetails, REFRESH_EXPIRATION);
    }

    //Construye un token JWT con los parámetros especificados
    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    //Valida si un token JWT es válido para un usuario específico
    
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
