package com.example.domain.ecommerce.controllers.inventario;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/inventario/reportes")
public class ReportesController {

    
    @GetMapping("/")
    public String verReportes(Model model){
        return "venta/reportes";
    }
}
