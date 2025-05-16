package com.example.domain.ecommerce.controllers.web;

import com.example.domain.ecommerce.dto.DireccionDTO;
import com.example.domain.ecommerce.dto.LoginDTO;
import com.example.domain.ecommerce.dto.UserDTO;
import com.example.domain.ecommerce.models.entities.Cliente;
import com.example.domain.ecommerce.models.entities.Persona;
import com.example.domain.ecommerce.models.entities.Usuario;
import com.example.domain.ecommerce.services.DireccionService;
import com.example.domain.ecommerce.services.EmailService;
import com.example.domain.ecommerce.services.UsuarioService;
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
    UsuarioService usuarioService;
    @Autowired
    DireccionService direccionService;
    @Autowired
    EmailService emailService;

    int contador = 0;

    @PostMapping("/form_crear")
    public String crearCuenta(
            @ModelAttribute UserDTO userDTO,
            Model model) {

        userDTO.setRol("Cliente");
        usuarioService.createUser(userDTO);

        return "commerce/iniciosesion";

    }


    @GetMapping("/pagar")
    public String abrirForm_pago(Model model, HttpSession session) {
        Usuario user = (Usuario) session.getAttribute("user");

        model.addAttribute("usuario", user);
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

        Persona cliente = usuarioService.actualizarUsuarios(userDTO, id);

        session.setAttribute("user", cliente);

        return "redirect:/info";

    }

    @PostMapping("/actualizar_direccion/{id}")
    public String actualizarDir(
            @ModelAttribute DireccionDTO direccionDTO,
            @PathVariable int id,
            Model model, HttpSession session) {

        Cliente cliente = direccionService.updateDirection(direccionDTO, id);

        session.setAttribute("user", cliente);

        return "redirect:/info";
    }

    @GetMapping("/actualizar_contrasena/{id}")
    public String recuperarContraseña(Model model,
            @PathVariable Integer id) {

        return "form_contraseña";
    }
}
