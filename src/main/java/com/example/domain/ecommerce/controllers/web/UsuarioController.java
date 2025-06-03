package com.example.domain.ecommerce.controllers.web;

import com.example.domain.ecommerce.dto.DireccionDTO;
import com.example.domain.ecommerce.dto.LoginDTO;
import com.example.domain.ecommerce.dto.RequestDTO;
import com.example.domain.ecommerce.dto.UserDTO;
import com.example.domain.ecommerce.models.entities.Cliente;
import com.example.domain.ecommerce.models.entities.Persona;
import com.example.domain.ecommerce.models.entities.Usuario;
import com.example.domain.ecommerce.services.AuthService;
import com.example.domain.ecommerce.services.DireccionService;
import com.example.domain.ecommerce.services.EmailService;
import com.example.domain.ecommerce.services.UsuarioService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/targus/usuario")
@SessionAttributes({ "carrito" })
@Controller
@Slf4j
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private DireccionService direccionService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private AuthService authService;

    int contador = 0;

    @PostMapping("/form_crear")
    public String crearCuenta(
            @ModelAttribute UserDTO userDTO,
            Model model) {

        userDTO.setRol("Cliente");
        try {
            authService.register(userDTO);
            // usuarioService.enviarEmail(usuario);

            return "commerce/iniciosesion";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "commerce/crear_cuenta";
        }

    }

    @GetMapping("/form_pago")
    public String abrirForm_pago(Model model, HttpSession session) {
        RequestDTO lista = (RequestDTO) session.getAttribute("lista");

        if (lista != null) {
            model.addAttribute("lista", lista);
        }
        return "commerce/form_pago";
    }

    @RequestMapping("/info")
    public String abrirInfo(Model model, HttpSession session) {

        Cliente user = (Cliente) session.getAttribute("user");
        model.addAttribute("user", user);
        return "commerce/informacion_Usu";
    }

    @PostMapping("/actualizarUsu/{id}")
    public String actualizarUsuarios(
            @ModelAttribute UserDTO userDTO,
            @PathVariable int id,
            HttpSession session,
            Model model) {

        try {
            Persona cliente = authService.update(userDTO, id);
            session.setAttribute("user", cliente);
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
        }

        return "redirect:/targus/usuario/info";

    }

    @PostMapping("/actualizar_direccion/{id}")
    public String actualizarDir(
            @ModelAttribute DireccionDTO direccionDTO,
            @PathVariable int id,
            Model model, HttpSession session) {

        try {
            Cliente cliente = direccionService.updateDirection(direccionDTO, id);
            session.setAttribute("user", cliente);

        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
        }

        return "redirect:/targus/usuario/info";
    }

    @GetMapping("/registrar/{id}")
    public String activar(@PathVariable Integer id) {
        usuarioService.activar(id);
        return "redirect:/targus/principal/iniciar_crear";
    }

    @GetMapping("/recuperar")
    public String enviar_email(Model model) {
        return "commerce/recuperar_contraseña";
    }

    @PostMapping("/recuperar")
    public String enviar_correo(
            @RequestParam("email") String email,
            Model model) {

        usuarioService.emailContraseña(email);

        return "redirect:/targus/principal/iniciar_crear";

    }

    @GetMapping("/actualizar_contrasena/{id}")
    public String recuperarContraseña(Model model,
            @PathVariable Integer id) {

        model.addAttribute("id", id);

        return "commerce/form_contraseña";
    }

    @PostMapping("/actualizar_contrasena/{id}")
    public String actualizar_con(
            @PathVariable Integer id,
            @RequestParam("repassword") String password,
            Model model, HttpSession session) {

        usuarioService.actualizarContraseña(password, id);

        return "redirect:/targus/principal/iniciar_crear";
    }

}
