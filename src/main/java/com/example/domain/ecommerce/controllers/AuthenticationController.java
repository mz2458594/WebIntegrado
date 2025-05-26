package com.example.domain.ecommerce.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.ecommerce.dto.LoginDTO;
import com.example.domain.ecommerce.dto.TokenResponse;
import com.example.domain.ecommerce.dto.UserDTO;
import com.example.domain.ecommerce.security.JwtUtil;
import com.example.domain.ecommerce.services.CustomUserDetailsService;

// Controlador que maneja todas las operaciones relacionadas con la autenticación

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    public AuthenticationController(AuthenticationManager authenticationManager, 
                                  JwtUtil jwtUtil,
                                  CustomUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }


    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@RequestBody UserDTO userDTO) {
        // Carga los detalles del usuario recién creado
        UserDetails userDetails = userDetailsService.loadUserByUsername(userDTO.getCorreo());
        
        // Genera los tokens JWT
        String token = jwtUtil.generateToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);
        
        // Retorna los tokens en la respuesta
        return ResponseEntity.ok(new TokenResponse(token, refreshToken));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginDTO loginDTO) {
        // Intenta autenticar al usuario con sus credenciales
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginDTO.getEmail(), 
                loginDTO.getPassword()));
        
        // Si la autenticación es exitosa, obtiene los detalles del usuario
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        
        // Genera los tokens JWT
        String token = jwtUtil.generateToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);
        
        // Retorna los tokens en la respuesta
        return ResponseEntity.ok(new TokenResponse(token, refreshToken));
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@RequestHeader("Authorization") String authHeader) {
        // Verifica si el header de autorización existe y tiene el formato correcto
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String refreshToken = authHeader.substring(7);
            String username = jwtUtil.extractUsername(refreshToken);
            
            if (username != null) {
                // Carga los detalles del usuario
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                
                // Valida el refresh token
                if (jwtUtil.validateToken(refreshToken, userDetails)) {
                    // Genera un nuevo token de acceso
                    String newToken = jwtUtil.generateToken(userDetails);
                    return ResponseEntity.ok(new TokenResponse(newToken, refreshToken));
                }
            }
        }
        return ResponseEntity.badRequest().build();
    }
}