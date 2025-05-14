package com.example.domain.ecommerce.controllers.inventario;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import jakarta.servlet.http.HttpSession;

@Controller
@Slf4j
public class ControladorGeneral {


    @RequestMapping("/login")
    public String loginEmpleado() {
        return "venta/login";
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
