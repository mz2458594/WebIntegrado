package com.example.domain.ecommerce.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomEmpleadorFailureHandler implements AuthenticationFailureHandler{
    int intentos = 1;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        if (intentos == 3) {
            response.sendRedirect("/inventario/principal/login?bloquear=true");
            intentos = 1;
        } else {
            response.sendRedirect("/inventario/principal/login?error=true");
            System.out.println(intentos);
            intentos++;
        }
    }
}
