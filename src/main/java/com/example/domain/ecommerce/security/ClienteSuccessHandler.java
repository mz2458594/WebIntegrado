package com.example.domain.ecommerce.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.domain.ecommerce.models.entities.Cliente;
import com.example.domain.ecommerce.models.entities.Usuario;
import com.example.domain.ecommerce.models.enums.Estado;
import com.example.domain.ecommerce.repositories.ClienteDAO;
import com.example.domain.ecommerce.repositories.UsuarioDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class ClienteSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private ClienteDAO clienteDAO;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        String email = authentication.getName();

        Usuario usuario = usuarioDAO.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        ;

        HttpSession session = request.getSession();

        if (clienteDAO.findByUsuario(usuario).isPresent()) {
            Cliente cliente = clienteDAO.findByUsuario(usuario).get();
            if (cliente.getUsuario().getEstado() == Estado.ACTIVO) {
                session.setAttribute("user", cliente);
                response.sendRedirect("/targus/principal/");
            } else if (cliente.getUsuario().getEstado() == Estado.INACTIVO) {
                response.sendRedirect("/targus/principal/iniciar_crear?error=true");
            }

        } else {
            response.sendRedirect("/targus/principal/iniciar_crear?error=true");
        }

    }
}
