package com.example.domain.ecommerce.controllers.inventario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.ecommerce.dto.EstadoRequestDTO;
import com.example.domain.ecommerce.services.PedidoService;

import jakarta.persistence.EntityNotFoundException;

@RequestMapping("/inventario/ecommerce")
@Controller
public class VentasEcommerceController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/pedidos")
    public String nuevaVenta(Model model) {

        model.addAttribute("pedidos", pedidoService.getPedidosUsuario());

        return "venta/pedidosEcommerce";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarPedido(Model model, @PathVariable int id, EstadoRequestDTO estadoRequestDTO) {
        try {
            pedidoService.actualizarEstadoUsuario(id, estadoRequestDTO);
        } catch (EntityNotFoundException | IllegalStateException e) {
            System.out.println("Error interno: " + e.getMessage());
        }

        model.addAttribute("pedidos", pedidoService.getPedidosUsuario());
        return "redirect:/inventario/ecommerce/pedidos";
    }
}
