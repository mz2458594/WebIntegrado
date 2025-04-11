package com.example.domain.ecommerce.controllers.rest;

import com.example.domain.ecommerce.dto.DireccionDTO;
import com.example.domain.ecommerce.dto.EmailDTO;
import com.example.domain.ecommerce.dto.LoginDTO;
import com.example.domain.ecommerce.dto.UserDTO;
import com.example.domain.ecommerce.services.DireccionService;
import com.example.domain.ecommerce.services.EmailService;
import com.example.domain.ecommerce.services.UsuarioService;
import com.example.domain.ecommerce.models.entities.Email;
import com.example.domain.ecommerce.models.entities.Usuario;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

// @RestController
// @RequestMapping("/api/users")
// @Slf4j
// public class UserController {
//    @Autowired
//    UsuarioService usuarioService;

//    @Autowired
//    DireccionService direccionService;

//    @Autowired
//    EmailService emailService;

//    @PostMapping("/login")
//     public Usuario login(@RequestBody LoginDTO loginDTO){
//        return usuarioService.login(loginDTO);
//    }

//    @PostMapping("/agregar_direccion/{id}")
//    public ResponseEntity<?> agregarDir(
//            @RequestBody DireccionDTO direccion,
//            @PathVariable int id) {

//        direccionService.createDirection(direccion, id);

//        return ResponseEntity.ok("Direccion agregada al usuario con id: " + id);

//    }

//    @PutMapping("/actualizar_direccion/{id}")
//    public ResponseEntity<?> actualizarDir(
//            @PathVariable int id,
//            @RequestBody DireccionDTO direccion) {

//        direccionService.updateDirection(direccion, id);

//        return ResponseEntity.ok("Dirección actualizada con éxito");

//    }

//    @PostMapping("/recuperar")
//    public ResponseEntity<String> enviar_correo(
//            @RequestBody EmailDTO email,
//            Model model) {

//        Usuario usuario = usuarioService.obtenerUsuarioPorCorreo(email);
//        Email correo = new Email("mz2458594@gmail.com", usuario.getEmail(), "Recuperar Contraseña",
//                usuario.getUsername(),
//                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");

//        emailService.sendEmailTemplate(correo, usuario.getIdUsuario());

//        return ResponseEntity.ok("Correo de recuperacion enviado con éxito");

//    }

//    @GetMapping("/users")
//    public List<Usuario> obteneUsuarios() {
//        List<Usuario> listaUsuarios = usuarioService.listarUsuario();
//        return listaUsuarios;
//    }

//    @PostMapping("/createUser")
//    public ResponseEntity<Usuario> createUser(UserDTO user) {
//        Usuario usuario = usuarioService.createUser(user);

//        return new ResponseEntity<>(usuario, HttpStatus.OK);
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteUser(@PathVariable int id) {
//        usuarioService.eliminarUsuario(id);
//        return new ResponseEntity<>("Usuario eliminado con éxito", HttpStatus.OK);
//    }

//    @PutMapping("/actualizar_usu/{id}")
//    public ResponseEntity<?> actualizarUsuarios(@RequestBody UserDTO user, @PathVariable int id) {

//        if (usuarioService.verificar(user.getCorreo(), user.getNum_documento(), user.getCelular())) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ya existe un usuario con esos datos");

//        }

//        usuarioService.actualizarUsuarios(user, id);

//        return ResponseEntity.ok("Usuario actualizado correctamente");

//    }

//    @PutMapping("/updatePassword/{id}")
//    public ResponseEntity<?> actualizar_con(
//            @PathVariable Integer id,
//            @RequestBody Map<String, String> body) {

//        String password = body.get("password");
//        usuarioService.actualizarContraseña(password, id);

//        return ResponseEntity.ok("Contraseña actualizada");
//    }

// }
