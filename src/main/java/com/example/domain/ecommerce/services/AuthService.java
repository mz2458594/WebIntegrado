package com.example.domain.ecommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.domain.ecommerce.dto.UserDTO;
import com.example.domain.ecommerce.models.entities.Usuario;

@Service
public class AuthService {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PersonaService personaService;


    public void register(UserDTO user) {

        Usuario usuario = usuarioService.createUser(user);

        personaService.createPersona(user, usuario);

    }
}
