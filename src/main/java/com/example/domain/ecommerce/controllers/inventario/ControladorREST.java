package com.example.domain.ecommerce.controllers.inventario;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.example.domain.ecommerce.dto.LoginDTO;
import com.example.domain.ecommerce.models.entities.Producto;
import com.example.domain.ecommerce.models.entities.Usuario;
import com.example.domain.ecommerce.services.ProductoService;
import com.example.domain.ecommerce.services.UsuarioService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@Slf4j
@SessionAttributes({ "nombre", "id", "rol" })
@RequestMapping("/venta")
public class ControladorREST {

    @Autowired
    private UsuarioService usuarioService;

    int contador = 0;

    @RequestMapping("/iniciar_crear")
    public String loginEmpleado() {
        return "venta/Iniciar_crear";
    }

    @PostMapping("/iniciar_crear")
    public String access(LoginDTO loginDTO, Model model, HttpServletRequest request) {

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
            return "venta/Iniciar_crear";
        }

    }

    @GetMapping("/cerrar")
    public String cerrar_sesion(HttpSession request, SessionStatus status) {
        status.setComplete();
        request.removeAttribute("nombre");
        request.removeAttribute("id");
        request.removeAttribute("rol");
        return "redirect:/";
    }

    @GetMapping("/index")
    public String menu(Model model) {
        
        return "index";
    }

    @PostMapping("/fecha")
    public String fecha(Model model,
            @RequestParam("fecha") String fecha) {

        
        return "index";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        return "dashboard";
    }
}
