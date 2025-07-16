package com.example.domain.ecommerce.controllers.web;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.domain.ecommerce.models.entities.Cliente;
import com.example.domain.ecommerce.models.entities.Empleado;
import com.example.domain.ecommerce.models.entities.Notificacion;
import com.example.domain.ecommerce.models.entities.Pedido;
import com.example.domain.ecommerce.models.entities.Producto;
import com.example.domain.ecommerce.models.enums.EstadoPedido;
import com.example.domain.ecommerce.services.ProductoService;
import com.example.domain.ecommerce.services.PedidoService;

import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class GlobalModelAtributte {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private PedidoService pedidoService;

    @ModelAttribute
    public void addUserModel(HttpSession session, Model model) {
        Cliente user = (Cliente) session.getAttribute("user");
        Empleado empleado = (Empleado) session.getAttribute("empleado");
        if (user != null) {
            model.addAttribute("user", user);
        }

        if (empleado != null) {
            model.addAttribute("empleado", empleado);
            List<Producto> productos = productoService.listarProducto();

            List<Pedido> pedidos = pedidoService.getPedidos();

            List<Notificacion> notificacions = new ArrayList<>();

            notificacions.addAll(pedidos.stream()
                    .filter(p -> p.getEstado() == EstadoPedido.PENDIENTE)
                    .filter(p -> {
                        LocalDate fechaPedido = p.getFechaPedido().toLocalDateTime().toLocalDate();
                        long dias = ChronoUnit.DAYS.between(fechaPedido, LocalDate.now());
                        return dias == 1 || dias == 2;
                    })
                    .map(p -> new Notificacion("Estado del pedido",
                            "El pedido con ID " + p.getIdPedido()
                                    + " con estado PENDIENTE va a cumplir el límite de confirmación",
                            LocalDateTime.now()))
                    .collect(Collectors.toList()));

            notificacions.addAll(productos.stream()
                    .filter(p -> Integer.parseInt(p.getStock()) < 5)
                    .map(p -> new Notificacion("Stock bajo",
                            "El producto " + p.getNombre() + "tiene stock bajo (" + p.getStock() + " unidades)",
                            LocalDateTime.now()))
                    .collect(Collectors.toList()));

            model.addAttribute("notificaciones", notificacions);
        }
    }

}
