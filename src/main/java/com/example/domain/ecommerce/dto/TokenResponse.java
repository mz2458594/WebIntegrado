package com.example.domain.ecommerce.dto;

//Clase DTO que encapsula la respuesta de tokens JWT, se usa para enviar tanto el token de acceso como el refresh token al cliente

public class TokenResponse {
    // Token de acceso JWT principal
    private String token;
    
    // Token de refresco para obtener nuevos tokens de acceso
    private String refreshToken;


    public TokenResponse(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }


    public String getToken() {
        return token;
    }


    public String getRefreshToken() {
        return refreshToken;
    }
} 