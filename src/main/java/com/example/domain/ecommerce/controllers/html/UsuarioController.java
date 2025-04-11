package com.example.domain.ecommerce.controllers.html;

import com.example.domain.ecommerce.dto.DireccionDTO;
import com.example.domain.ecommerce.dto.LoginDTO;
import com.example.domain.ecommerce.dto.UserDTO;
import com.example.domain.ecommerce.models.entities.Usuario;
import com.example.domain.ecommerce.services.DireccionService;
import com.example.domain.ecommerce.services.EmailService;
import com.example.domain.ecommerce.services.UsuarioService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/adminUsuarios")
    public String usuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.listarUsuario());
        return "/adminUsuarios";
    }

    @PostMapping("/form_crear")
    public String crearCuenta(
            @ModelAttribute UserDTO userDTO,
            Model model) {

        userDTO.setRol("Cliente");
        usuarioService.createUser(userDTO);

        return "commerce/iniciosesion";

    }

    @PostMapping("/iniciar_crear")
    public String iniciar(LoginDTO loginDTO, Model model, HttpServletRequest request) {

        try {
            Usuario user = usuarioService.login(loginDTO);

            HttpSession session = request.getSession();

            session.setAttribute("user", user);

            System.out.println(session.getAttribute("user"));
            contador = 0;

            return "redirect:/";

        } catch (EntityNotFoundException e) {

            contador++;
            System.out.println("CONTADOR" + contador);
            if (contador >= 3) {
                model.addAttribute("bloquear", contador>=3);
            }

            model.addAttribute("error", true);
            return "commerce/iniciosesion";
        }

    }


    @PostMapping("/eliminar_usu/{id}")
    public String eliminarUsuario(
            @PathVariable int id,
            Model model) {

        usuarioService.eliminarUsuario(id);
        model.addAttribute("mensaje", "Datos eliminados con éxito");

        model.addAttribute("usuarios", usuarioService.listarUsuario());
        return "redirect:/adminUsuarios";
    }

    @GetMapping("/pagar")
    public String abrirForm_pago(Model model, HttpSession session) {
        Usuario user = (Usuario) session.getAttribute("user");

        model.addAttribute("usuario", user);
        return "commerce/form_pago";
    }

    @RequestMapping("/info")
    public String abrirInfo(Model model, HttpSession session) {

        Usuario user = (Usuario) session.getAttribute("user");
        model.addAttribute("user", user);
        return "commerce/informacion_Usu";
    }

    @PostMapping("/actualizar_usu/{id}")
    public String actualizarUsuarios(
            @ModelAttribute UserDTO userDTO,
            @PathVariable int id,
            HttpSession session,
            Model model) {

        Usuario user = usuarioService.actualizarUsuarios(userDTO, id);

        session.setAttribute("user", user);

        return "redirect:/info";

    }

    @PostMapping("/actualizar_direccion/{id}")
    public String actualizarDir(
            @ModelAttribute DireccionDTO direccionDTO,
            @PathVariable int id,
            Model model, HttpSession session) {

    
        direccionService.updateDirection(direccionDTO, id);

        Usuario u = usuarioService.obtenerUsuarioPorId(id);

        session.setAttribute("user", u);

        return "redirect:/info";
    }

    @GetMapping("/recuperar")
    public String enviar_email(Model model) {
        return "recuperar_contraseña";
    }

    @GetMapping("/actualizar_contrasena/{id}")
    public String recuperarContraseña(Model model,
            @PathVariable Integer id) {

        model.addAttribute("id", id);
        return "form_contraseña";
    }
}
