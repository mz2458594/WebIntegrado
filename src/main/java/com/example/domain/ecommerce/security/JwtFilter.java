package com.example.domain.ecommerce.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.domain.ecommerce.services.CustomUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

// Filtro que intercepta todas las peticiones HTTP para validar el token JWT
//Se ejecuta una vez por cada petición en la cadena de filtros

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        
        // Obtiene el header de autorización
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // Si no hay token o no es del tipo Bearer, continúa con la cadena de filtros
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extrae el token JWT del header
        jwt = authHeader.substring(7);
        userEmail = jwtUtil.extractUsername(jwt);

        // Si hay un email y no hay autenticación actual
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Carga los detalles del usuario desde la base de datos
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            
            // Valida el token
            if (jwtUtil.validateToken(jwt, userDetails)) {
                // Crea un token de autenticación
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
                );
                
                // Agrega los detalles de la petición al token
                authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
                );
                
                // Establece la autenticación en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        
        // Continúa con la cadena de filtros
        filterChain.doFilter(request, response);
    }
}
