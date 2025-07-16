package com.example.domain.ecommerce.controllers.web;

import com.example.domain.ecommerce.dto.DireccionDTO;
import com.example.domain.ecommerce.dto.LoginDTO;
import com.example.domain.ecommerce.dto.PedidoDTO;
import com.example.domain.ecommerce.dto.RequestDTO;
import com.example.domain.ecommerce.dto.UserDTO;
import com.example.domain.ecommerce.models.entities.Cliente;
import com.example.domain.ecommerce.models.entities.Pedido;
import com.example.domain.ecommerce.models.entities.PedidoUsuario;
import com.example.domain.ecommerce.models.entities.Persona;
import com.example.domain.ecommerce.models.entities.Usuario;
import com.example.domain.ecommerce.services.AuthService;
import com.example.domain.ecommerce.services.DireccionService;
import com.example.domain.ecommerce.services.EmailService;
import com.example.domain.ecommerce.services.PedidoService;
import com.example.domain.ecommerce.services.UsuarioService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/targus/usuario")
@SessionAttributes({ "carrito" })
@Controller
@Slf4j
@AllArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    private final DireccionService direccionService;

    private final EmailService emailService;

    private final AuthService authService;

    private final PedidoService pedidoService;

    private final int contador = 0;

    @PostMapping("/form_crear")
    public String crearCuenta(
            @ModelAttribute UserDTO userDTO,
            Model model) {

        userDTO.setRol("Cliente");
        try {
            Usuario usuario = authService.register(userDTO);
            usuarioService.enviarEmail(usuario);

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

    @GetMapping("/info")
    public String abrirInfo(Model model, HttpSession session) {

        Cliente user = (Cliente) session.getAttribute("user");
        model.addAttribute("user", user);
        return "commerce/informacion_Usu";
    }

    @GetMapping("/pedidos")
    public String mostrarPedidos(Model model, HttpSession session) throws JsonProcessingException {
        Cliente user = (Cliente) session.getAttribute("user");
        try {
            List<PedidoUsuario> pedidos = pedidoService.getPedidosUsuarioPorIdUsuario(user.getUsuario().getIdUsuario());

            List<PedidoDTO> dtoList = pedidoService.convertirPedidoDTO(pedidos);

            model.addAttribute("pedidos", dtoList);
        } catch (RuntimeException e) {
            return "commerce/seguipedidos";
        }
        return "commerce/seguipedidos";
    }

    @PostMapping("/actualizarUsu/{id}")
    public String actualizarUsuarios(
            @ModelAttribute UserDTO userDTO,
            @PathVariable int id,
            HttpSession session,
            Model model) {

        try {
            Cliente cliente = (Cliente)authService.update(userDTO, id);
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
