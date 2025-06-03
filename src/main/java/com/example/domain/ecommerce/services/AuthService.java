package com.example.domain.ecommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.ecommerce.dto.UserDTO;
import com.example.domain.ecommerce.models.entities.Persona;
import com.example.domain.ecommerce.models.entities.Usuario;

@Service
public class AuthService {

    @Autowired
    private PersonaService personaService;

    @Autowired
    private UsuarioService usuarioService;

    public void register(UserDTO user) {

        Usuario usuario = usuarioService.createUser(user);

        personaService.createPersona(user, usuario);

    }

    public Persona update(UserDTO userDTO, int id){
        Usuario usuario = usuarioService.actualizarUsuarios(userDTO, id);

        return personaService.actualizarPersona(userDTO, usuario);
    }
}
