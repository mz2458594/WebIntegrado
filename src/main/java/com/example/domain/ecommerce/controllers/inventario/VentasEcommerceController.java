package com.example.domain.ecommerce.controllers.inventario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.ecommerce.services.PedidoService;

@RequestMapping("/inventario/ecommerce")
@Controller
public class VentasEcommerceController {
    
    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/pedidos")
    public String nuevaVenta(Model model) {

        model.addAttribute("ventas", pedidoService.getPedidosUsuario());

        return "venta/pedidosEcommerce";
    }
}
