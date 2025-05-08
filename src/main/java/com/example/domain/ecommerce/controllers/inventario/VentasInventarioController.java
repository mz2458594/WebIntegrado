package com.example.domain.ecommerce.controllers.inventario;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.example.domain.ecommerce.dto.RequestDTO;
import com.example.domain.ecommerce.models.entities.Empleado;
import com.example.domain.ecommerce.models.entities.Producto;
import com.example.domain.ecommerce.models.entities.Venta;
import com.example.domain.ecommerce.models.entities.Venta_producto;
import com.example.domain.ecommerce.services.ProductoService;
import com.example.domain.ecommerce.services.VentaService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@SessionAttributes({ "compra" })
public class VentasInventarioController {

    @Autowired
    private VentaService ventasService;

    @Autowired
    private ProductoService productosService;

    @GetMapping("/nuevaVenta")
    public String nuevaVenta(Model model) {

        model.addAttribute("ventas", ventasService.getVentas());

        return "venta/nuevaVenta";
    }

    @PostMapping("/eliminarVenta/{id}")
    public String eliminarVenta(
            @PathVariable int id,
            Model model) {

        ventasService.deleteVenta(id);

        model.addAttribute("ventas", ventasService.getVentas());
        return "redirect:/nuevaVenta";

    }

    @GetMapping("/agregarVenta")
    public String agregarVenta(Model model) {
        model.addAttribute("productos", productosService.listarProducto());
        return "venta/agregarVenta";
    }

    @PostMapping("/añadirProd/{id}")
    public String agregar(
            @PathVariable int id,
            @RequestParam("canti") int cantidad,
            HttpSession session,
            Model model) {

        RequestDTO sale = (RequestDTO) session.getAttribute("sale");

        if (sale == null) {
            sale = new RequestDTO();
            sale.setItem(new ArrayList<>());
        }

        boolean encontrado = false;

        for (RequestDTO.ItemsVentaDTO item : sale.getItem()) {
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
            sale.getItem().add(nuevo_item);
        }

        session.setAttribute("sale", sale);
        
        model.addAttribute("productos", productosService.listarProducto());
        model.addAttribute("venta", sale);

        return "venta/agregarVenta";
    }

    @GetMapping("/eliminarProd/{id}")
    public String eliminarProd(
            @PathVariable Integer id,
            HttpSession session,
            Model model) {

        RequestDTO sale = (RequestDTO) session.getAttribute("sale");

        for (RequestDTO.ItemsVentaDTO item : sale.getItem()) {
            if (item.getProducto().getIdProducto() == id) {
                sale.getItem().remove(item);
                break;
            }
        }

        session.setAttribute("sale", sale);

        model.addAttribute("productos", productosService.listarProducto());
        model.addAttribute("venta", sale);

        return "venta/agregarVenta";

    }

    @PostMapping("/registroVenta")
    public String registrarVenta(
            @RequestParam("total_reg") double total,
            Model model, HttpSession session, SessionStatus status) {

        RequestDTO sale = (RequestDTO) session.getAttribute("sale");
        Empleado empleado = (Empleado) session.getAttribute("empleado");

        sale.setId_usuario(empleado.getUsuario().getIdUsuario());
        session.setAttribute("sale", sale);

        model.addAttribute("venta", sale);
        return "venta/registroVenta";
    }

    @PostMapping("/saveVenta")
    public String saveVenta(HttpSession session, Model model) {
        RequestDTO sale = (RequestDTO) session.getAttribute("sale");
        Empleado empleado = (Empleado) session.getAttribute("empleado");

        sale.setId_usuario(empleado.getUsuario().getIdUsuario());
        
        ventasService.crearVenta(sale);

        session.removeAttribute("sale");
        model.addAttribute("ventas", ventasService.getVentas());
        return "redirect:/nuevaVenta";
    }

    @GetMapping("/detalleVenta/{id}")
    public String ver(
            @PathVariable int id,
            HttpSession session,
            Model model) {

        Venta ventas = ventasService.obtenerVentasPorId(id);

        RequestDTO sale = new RequestDTO();
        sale.setItem(new ArrayList<>());
        sale.setId_usuario(ventas.getUsuario().getIdUsuario());

        for (Venta_producto venta : ventas.getVentaProductos()) {
            RequestDTO.ItemsVentaDTO nuevo_item = new RequestDTO.ItemsVentaDTO();
            nuevo_item.setCantidad(venta.getCantidad());
            nuevo_item.setProducto(venta.getProducto());
            nuevo_item.setTotal(Float.parseFloat(nuevo_item.getProducto().getPrecioVenta()) * nuevo_item.getCantidad());
            sale.getItem().add(nuevo_item);
        }        

        session.setAttribute("sale", sale);


        model.addAttribute("venta", sale);
        model.addAttribute("productos", productosService.listarProducto());
        model.addAttribute("ocultar", true);
        return "venta/registroVenta";
    }

    @GetMapping("/editarVenta")
    public String editarVenta(Model model, HttpSession session, SessionStatus status) {

        RequestDTO sale = (RequestDTO) session.getAttribute("sale");

        model.addAttribute("venta", sale);
        model.addAttribute("productos", productosService.listarProducto());

        return "venta/editarVenta";
    }

    @GetMapping("/cancelar")
    public String cancelar(Model model, HttpSession session) {

        session.removeAttribute("sale");

        model.addAttribute("ventas", ventasService.getVentas());
        return "venta/nuevaVenta";
    }

}
