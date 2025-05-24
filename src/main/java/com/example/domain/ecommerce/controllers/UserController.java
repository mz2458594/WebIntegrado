package com.example.domain.ecommerce.controllers;

import com.example.domain.ecommerce.dto.DireccionDTO;
import com.example.domain.ecommerce.dto.UpdatePasswordRequest;
import com.example.domain.ecommerce.dto.UserDTO;
import com.example.domain.ecommerce.dto.UsuarioPersonaDTO;
import com.example.domain.ecommerce.services.DireccionService;
import com.example.domain.ecommerce.services.EmailService;
import com.example.domain.ecommerce.services.UsuarioService;
import com.example.domain.ecommerce.models.entities.Persona;
import com.example.domain.ecommerce.models.entities.Usuario;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {
    @Autowired
    UsuarioService usuarioService;

    @Autowired
    DireccionService direccionService;

    @Autowired
    EmailService emailService;

    @PostMapping("/createDirection/{id}")
    public ResponseEntity<?> createDirection(
            @RequestBody DireccionDTO direccion,
            @PathVariable("id") int id_usuario) {

        direccionService.createDirection(direccion, id_usuario);

        return ResponseEntity.ok("Direccion agregada al usuario con id: " + id_usuario);

    }

    @PutMapping("/updateDirection/{id}")
    public ResponseEntity<?> updateDirection(
            @PathVariable("id") int id_usuario,
            @RequestBody DireccionDTO direccion) {

        direccionService.updateDirection(direccion, id_usuario);

        return ResponseEntity.ok("Dirección actualizada con éxito");

    }

    @GetMapping("/")
    public ResponseEntity<List<UsuarioPersonaDTO>> obteneUsuarios() {
       return ResponseEntity.ok(usuarioService.listarClientesYEmpleados());
    }

    @PostMapping("/createUser")
    public ResponseEntity<Usuario> createUser(@RequestBody UserDTO user) {
        Usuario usuario = usuarioService.createUser(user);

        return ResponseEntity.status(201).body(usuario);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<Persona> updateUser(@RequestBody UserDTO user, @PathVariable("id") int id) {

        Persona persona = usuarioService.actualizarUsuarios(user, id);

        return ResponseEntity.ok(persona);

    }

    @PutMapping("/updatePassword/{id}")
    public ResponseEntity<?> updatePassword(
            @PathVariable("id") int id,
            @RequestBody UpdatePasswordRequest request) {
       
        Usuario usuario = usuarioService.actualizarContraseña(request.getPassword(), id);

        return ResponseEntity.ok(usuario);
    }

    //METODOS QUE FALTAN

    // enviarEmailRegistrar
    // activar
    // emailContraseña
    // actualizarContraseña


}
