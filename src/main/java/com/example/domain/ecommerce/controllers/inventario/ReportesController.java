package com.example.domain.ecommerce.controllers.inventario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.ecommerce.services.VentaService;

@Controller
@RequestMapping("/inventario/reportes")
public class ReportesController {

    @Autowired
    private VentaService ventaService;


    @GetMapping("/")
    public String verReportes(Model model){
        model.addAttribute("ventas", ventaService.getVentas());
        return "venta/reportes";
    }


}
