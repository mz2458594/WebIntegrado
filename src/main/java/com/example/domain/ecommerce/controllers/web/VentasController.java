package com.example.domain.ecommerce.controllers.web;

import com.example.domain.ecommerce.dto.DireccionDTO;
import com.example.domain.ecommerce.dto.ProductoSeleccionadoDTO;
import com.example.domain.ecommerce.dto.RequestDTO;
import com.example.domain.ecommerce.models.entities.Cliente;
import com.example.domain.ecommerce.models.entities.Producto;
import com.example.domain.ecommerce.services.DireccionService;
import com.example.domain.ecommerce.services.ProductoService;
import com.example.domain.ecommerce.services.VentaService;

import org.springframework.ui.Model;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("/targus/venta")
@SessionAttributes({ "carrito" })
@Controller
@Slf4j
public class VentasController {

    @Autowired
    VentaService ventasService;

    @Autowired
    private ProductoService productosService;

    @Autowired
    private DireccionService direccionService;

    @PostMapping("/añadir/{id}")
    public String agregar(
            @PathVariable int id,
            @RequestParam("canti") int cantidad,
            HttpSession session,
            RedirectAttributes redirectAttributes,
            Model model) {

        RequestDTO carrito = (RequestDTO) session.getAttribute("carrito");

        if (carrito == null) {
            carrito = new RequestDTO();
            carrito.setItem(new ArrayList<>());
        }

        boolean encontrado = false;

        for (RequestDTO.ItemsVentaDTO item : carrito.getItem()) {
            if (item.getProducto().getIdProducto() == id) {

                int nuevaCantidad = item.getCantidad() + cantidad;

                if (nuevaCantidad > Integer.parseInt(item.getProducto().getStock())) {
                    redirectAttributes.addFlashAttribute("error", "Ha sobrepasado el stock del producto");
                    return "redirect:/targus/producto/detalle/" + id;
                }

                item.setCantidad(item.getCantidad() + cantidad);
                item.setTotal(Float.parseFloat(item.getProducto().getPrecioVenta()) * item.getCantidad());
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            Producto p = productosService.obtenerProductoPorId(id);
            int stockDisponible = Integer.parseInt(p.getStock());

            if (stockDisponible == 0) {
                redirectAttributes.addFlashAttribute("error", "No hay stock de este producto actualmente");
                return "redirect:/targus/producto/detalle/" + id;
            }

            if (cantidad > stockDisponible) {
                redirectAttributes.addFlashAttribute("error", "La cantidad ingresada sobrepasa el stock del producto");
                return "redirect:/targus/producto/detalle/" + id;
            }

            RequestDTO.ItemsVentaDTO nuevo_item = new RequestDTO.ItemsVentaDTO();
            nuevo_item.setCantidad(cantidad);
            nuevo_item.setProducto(p);
            nuevo_item.setTotal(cantidad * Float.parseFloat(nuevo_item.getProducto().getPrecioVenta()));
            carrito.getItem().add(nuevo_item);
        }

        session.setAttribute("carrito", carrito);
        model.addAttribute("productos", productosService.listarProducto());

        return "redirect:/targus/producto/detalle/" + id;
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

        return "redirect:/targus/producto/";

    }

    @PostMapping("/agregar/{id}")
    public String añadir(
            @PathVariable int id,
            HttpSession session,
            RedirectAttributes redirectAttributes,
            Model model) {

        RequestDTO carrito = (RequestDTO) session.getAttribute("carrito");

        if (carrito == null) {
            carrito = new RequestDTO();
            carrito.setItem(new ArrayList<>());
        }

        boolean encontrado = false;

        for (RequestDTO.ItemsVentaDTO item : carrito.getItem()) {
            if (item.getProducto().getIdProducto() == id) {

                int nuevaCantidad = item.getCantidad() + 1;

                if (nuevaCantidad > Integer.parseInt(item.getProducto().getStock())) {
                    redirectAttributes.addFlashAttribute("error", "Ha sobrepasado el stock del producto");
                    return "redirect:/targus/principal/carro";
                }

                item.setCantidad(item.getCantidad() + 1);
                item.setTotal(Float.parseFloat(item.getProducto().getPrecioVenta()) * item.getCantidad());
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            Producto p = productosService.obtenerProductoPorId(id);
            int stockDisponible = Integer.parseInt(p.getStock());

            if (stockDisponible == 0) {
                redirectAttributes.addFlashAttribute("error", "No hay stock de este producto actualmente");
                return "redirect:/targus/principal/carro";
            }
            RequestDTO.ItemsVentaDTO nuevo_item = new RequestDTO.ItemsVentaDTO();
            nuevo_item.setCantidad(1);
            nuevo_item.setProducto(p);
            nuevo_item.setTotal(1 * Float.parseFloat(nuevo_item.getProducto().getPrecioVenta()));
            carrito.getItem().add(nuevo_item);
        }

        session.setAttribute("carrito", carrito);
        return "redirect:/targus/principal/carro";
    }

    @PostMapping("/disminuir/{id}")
    public String disminuir(
            @PathVariable int id,
            HttpSession session,
            Model model) {

        RequestDTO carrito = (RequestDTO) session.getAttribute("carrito");

        for (RequestDTO.ItemsVentaDTO item : carrito.getItem()) {
            if (item.getProducto().getIdProducto() == id) {
                if (item.getCantidad() > 1) {
                    item.setCantidad(item.getCantidad() - 1);
                    item.setTotal(item.getCantidad() * Float.parseFloat(item.getProducto().getPrecioVenta()));
                    break;
                } else {
                    break;
                }
            }
        }

        session.setAttribute("carrito", carrito);
        return "redirect:/targus/principal/carro";
    }

    @PostMapping("/pagar")
    public String seleccionados(
            @RequestBody List<ProductoSeleccionadoDTO> productos,
            HttpSession session, SessionStatus status, Model model) {

        RequestDTO lista = new RequestDTO();

        lista.setItem(new ArrayList<>());

        for (ProductoSeleccionadoDTO productoSeleccionadoDTO : productos) {
            RequestDTO.ItemsVentaDTO nuevo_item = new RequestDTO.ItemsVentaDTO();
            nuevo_item.setCantidad(productoSeleccionadoDTO.getCantidad());
            Producto p = productosService.obtenerProductoPorId(productoSeleccionadoDTO.getIdProducto());
            nuevo_item.setProducto(p);
            nuevo_item.setTotal(nuevo_item.getCantidad() * Float.parseFloat(nuevo_item.getProducto().getPrecioVenta()));
            lista.getItem().add(nuevo_item);
        }

        session.setAttribute("lista", lista);

        return "redirect:/targus/usuario/form_pago";
    }

    @GetMapping("/registrar_venta")
    public String registrarVenta(
            Model model, HttpSession session, SessionStatus status) {

        RequestDTO car = (RequestDTO) session.getAttribute("lista");

        Cliente user = (Cliente) session.getAttribute("user");
        car.setId_usuario(user.getUsuario().getIdUsuario());
        car.setTipo("BOLETA");

        ventasService.crearVentaEcommerce(car);

        status.setComplete();
        session.removeAttribute("carrito");
        session.removeAttribute("lista");
        return "redirect:/targus/principal/";
    }

    @PostMapping("/updateDirection/{id}")
    public String actualizarDireccionEnvio(@ModelAttribute DireccionDTO direccionDTO,
            @PathVariable int id,
            Model model,
            HttpSession session) {
        try {
            Cliente cliente = direccionService.updateDirection(direccionDTO, id);
            session.setAttribute("user", cliente);

        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
        }

        return "redirect:/targus/usuario/form_pago";
    }

}
