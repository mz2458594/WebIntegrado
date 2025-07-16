package com.example.domain.ecommerce.services;

import org.springframework.stereotype.Service;
import com.example.domain.ecommerce.dto.UserDTO;
import com.example.domain.ecommerce.models.entities.Persona;
import com.example.domain.ecommerce.models.entities.Usuario;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {

   
    private final PersonaService personaService;

    private final UsuarioService usuarioService;

    public Usuario register(UserDTO user) {

        Usuario usuario = usuarioService.createUser(user);

        personaService.createPersona(user, usuario);

        return usuario;

    }

    public Persona update(UserDTO userDTO, int id){
        Usuario usuario = usuarioService.actualizarUsuarios(userDTO, id);

        return personaService.actualizarPersona(userDTO, usuario);
    }
}
