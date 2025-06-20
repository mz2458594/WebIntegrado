package com.example.domain.ecommerce.controllers.web;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.domain.ecommerce.models.entities.Cliente;
import com.example.domain.ecommerce.models.entities.Empleado;
import com.example.domain.ecommerce.models.entities.Notificacion;
import com.example.domain.ecommerce.models.entities.Producto;
import com.example.domain.ecommerce.services.ProductoService;

import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class GlobalModelAtributte {

    @Autowired
    private ProductoService productoService;

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

            List<Notificacion> notificaciones = productos.stream()
                    .filter(p -> Integer.parseInt(p.getStock()) < 5)
                    .map(p -> new Notificacion("El producto " + p.getNombre() + "tiene stock bajo (" + p.getStock() + " unidades)", LocalDateTime.now()))
                    .collect(Collectors.toList());

            model.addAttribute("notificaciones", notificaciones);
        }
    }

}
