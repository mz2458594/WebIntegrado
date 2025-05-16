package com.example.domain.ecommerce.controllers.web;

import com.example.domain.ecommerce.dto.RequestDTO;
import com.example.domain.ecommerce.models.entities.Cliente;
import com.example.domain.ecommerce.models.entities.Producto;
import com.example.domain.ecommerce.services.ProductoService;
import com.example.domain.ecommerce.services.VentaService;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@RequestMapping("/targus/venta")
@SessionAttributes({"carrito" })
@Controller
@Slf4j
public class VentasController {

    @Autowired
    VentaService ventasService;

    @Autowired
    private ProductoService productosService;


    @GetMapping("/registrar_venta")
    public String registrarVenta(
        
            Model model, HttpSession session, SessionStatus status) {

        RequestDTO car = (RequestDTO)session.getAttribute("carrito");
        
        Cliente user = (Cliente)session.getAttribute("user");
        car.setId_usuario(user.getUsuario().getIdUsuario());
        
        ventasService.crearVenta(car);
        session.removeAttribute("carrito");

        model.addAttribute("productos", productosService.listarProducto());
        return "redirect:/";
    }

    @PostMapping("/añadir/{id}")
    public String agregar(
            @PathVariable int id,
            @RequestParam("canti") int cantidad,
            HttpSession session,
            Model model) {

        RequestDTO carrito = (RequestDTO) session.getAttribute("carrito");
        

        if (carrito == null) {
            carrito = new RequestDTO();
            carrito.setItem(new ArrayList<>());
        }

        boolean encontrado = false;

        for (RequestDTO.ItemsVentaDTO item : carrito.getItem()) {
            if (item.getProducto().getIdProducto() == id) {
                item.setCantidad(item.getCantidad() + cantidad);
                item.setTotal(Float.parseFloat(item.getProducto().getPrecioVenta()) * item.getCantidad());
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            RequestDTO.ItemsVentaDTO nuevo_item = new RequestDTO.ItemsVentaDTO();
            nuevo_item.setCantidad(cantidad);
            Producto p = productosService.obtenerProductoPorId(id);
            nuevo_item.setProducto(p);
            nuevo_item.setTotal(cantidad * Float.parseFloat(nuevo_item.getProducto().getPrecioVenta()));
            carrito.getItem().add(nuevo_item);
        }

        session.setAttribute("carrito", carrito);
        model.addAttribute("productos", productosService.listarProducto() );

        return "redirect:/producto";
    }

    @GetMapping("/eliminar_prod/{id}")
    public String eliminarProd(
            @PathVariable Integer id,
            HttpSession session,
            Model model) {

       RequestDTO carrito = (RequestDTO) session.getAttribute("carrito");

       for (RequestDTO.ItemsVentaDTO item : carrito.getItem()) {
            if (item.getProducto().getIdProducto() == id) {
                carrito.getItem().remove(item);
                break;
            }
       }


        model.addAttribute("productos", productosService.listarProducto());

        return "redirect:/producto";

    }

}
