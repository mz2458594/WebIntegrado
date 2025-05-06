package com.example.domain.ecommerce.controllers.inventario;

import lombok.extern.slf4j.Slf4j;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import com.example.domain.ecommerce.dto.LoginDTO;
import com.example.domain.ecommerce.models.entities.Empleado;
import com.example.domain.ecommerce.models.entities.Usuario;
import com.example.domain.ecommerce.services.UsuarioService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@Slf4j
public class ControladorGeneral {

    @Autowired
    private UsuarioService usuarioService;

    int contador = 0;

    @RequestMapping("/login")
    public String loginEmpleado() {
        return "venta/login";
    }

    @PostMapping("/login")
    public String access(LoginDTO loginDTO, Model model, HttpServletRequest request) {

        try {
            Empleado empleado = usuarioService.loginEmpleado(loginDTO);

            HttpSession session = request.getSession();

            session.setAttribute("empleado", empleado);

            contador = 0;

            return "redirect:/index";

        } catch (EntityNotFoundException e) {

            contador++;
            System.out.println("CONTADOR" + contador);
            if (contador >= 3) {
                model.addAttribute("bloquear", contador>=3);
            }

            model.addAttribute("error", true);
            return "venta/login";
        }

    }

    @GetMapping("/cerrarEmpleado")
    public String cerrar_sesion(HttpSession request, SessionStatus status) {
        status.setComplete();
        request.removeAttribute("empleado");
        return "redirect:/login";
    }

    @GetMapping("/index")
    public String menu(Model model) {
        
        return "venta/index";
    }

    @PostMapping("/fecha")
    public String fecha(Model model,
            @RequestParam("fecha") String fecha) {

        
        return "venta/index";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        return "venta/dashboard";
    }
}
