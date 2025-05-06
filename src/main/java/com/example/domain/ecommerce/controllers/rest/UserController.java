package com.example.domain.ecommerce.controllers.rest;

import com.example.domain.ecommerce.dto.DireccionDTO;
import com.example.domain.ecommerce.dto.LoginDTO;
import com.example.domain.ecommerce.dto.UserDTO;
import com.example.domain.ecommerce.services.DireccionService;
import com.example.domain.ecommerce.services.EmailService;
import com.example.domain.ecommerce.services.UsuarioService;
import com.example.domain.ecommerce.models.entities.Cliente;
import com.example.domain.ecommerce.models.entities.Empleado;
import com.example.domain.ecommerce.models.entities.Usuario;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

   @PostMapping("/login")
    public Cliente login(@RequestBody LoginDTO loginDTO){
       return usuarioService.login(loginDTO);
   }

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
   public List<Usuario> obteneUsuarios() {
       List<Usuario> listaUsuarios = usuarioService.listarUsuario();
       return listaUsuarios;
   }

   @PostMapping("/createUser")
   public ResponseEntity<Usuario> createUser(@RequestBody UserDTO user) {
       Usuario usuario = usuarioService.createUser(user);

       return new ResponseEntity<>(usuario, HttpStatus.OK);
   }

   @DeleteMapping("/deleteUser/{id}")
   public ResponseEntity<String> deleteUser(@PathVariable int id) {
       usuarioService.eliminarUsuario(id);
       return new ResponseEntity<>("Usuario eliminado con éxito", HttpStatus.OK);
   }

   @PutMapping("/updateUser/{id}")
   public ResponseEntity<?> updateUser(@RequestBody UserDTO user, @PathVariable("id") int id) {

       if (usuarioService.verificar(user.getCorreo(), user.getNum_documento(), user.getCelular())) {
           return ResponseEntity.status(HttpStatus.CONFLICT).body("Ya existe un usuario con esos datos");
       }

       usuarioService.actualizarUsuarios(user, id);

       return ResponseEntity.ok("Usuario actualizado correctamente");

   }

   @PutMapping("/updatePassword/{id}")
   public ResponseEntity<?> updatePassword(
           @PathVariable("id") int id,
           @RequestBody Map<String, String> body) {

       String password = body.get("password");
       usuarioService.actualizarContraseña(password, id);

       return ResponseEntity.ok("Contraseña actualizada");
   }

}
