package com.example.domain.ecommerce.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.domain.ecommerce.models.entities.Empleado;
import com.example.domain.ecommerce.models.entities.Usuario;
import com.example.domain.ecommerce.models.enums.Estado;
import com.example.domain.ecommerce.repositories.EmpleadoDAO;
import com.example.domain.ecommerce.repositories.UsuarioDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class EmpleadoSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private EmpleadoDAO empleadoDAO;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        String email = authentication.getName();

        Usuario usuario = usuarioDAO.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        ;

        HttpSession session = request.getSession();

        if (empleadoDAO.findByUsuario(usuario).isPresent()) {
            Empleado empleado = empleadoDAO.findByUsuario(usuario).get();
            session.setAttribute("empleado", empleado);
            response.sendRedirect("/inventario/principal/index");

            // if (empleado.getUsuario().getEstado() == Estado.ACTIVO) {
            //     session.setAttribute("empleado", empleado);
            //     response.sendRedirect("/inventario/principal/index");
            // } else if (empleado.getUsuario().getEstado() == Estado.INACTIVO) {
            //     response.sendRedirect("/inventario/principal/login?error=true");

            // }

        } else {
            response.sendRedirect("/inventario/principal/login?error=true");
        }

    }
}
