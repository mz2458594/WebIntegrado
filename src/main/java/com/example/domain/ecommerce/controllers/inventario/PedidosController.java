package com.example.domain.ecommerce.controllers.inventario;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.domain.ecommerce.dto.EstadoRequestDTO;
import com.example.domain.ecommerce.dto.RequestDTO;
import com.example.domain.ecommerce.models.entities.DetallePedido;
import com.example.domain.ecommerce.models.entities.Empleado;
import com.example.domain.ecommerce.models.entities.PedidoProveedor;
import com.example.domain.ecommerce.models.entities.Producto;
import com.example.domain.ecommerce.services.PedidoService;
import com.example.domain.ecommerce.services.ProductoService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/inventario/pedido")
public class PedidosController {

    @Autowired
    private ProductoService productosService;

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/pedidos")
    public String pedidos(Model model) {

        model.addAttribute("pedidos", pedidoService.getPedidosProveedor());

        return "venta/pedidos";
    }

    @GetMapping("/agregarPedido")
    public String agregarPedido(Model model, HttpSession session) {

        Empleado empleado = (Empleado) session.getAttribute("empleado");

        if (empleado == null) {
            model.addAttribute("error", "No hay usuario logeado en el sistema");
            return "venta/pedidos";
        }

        model.addAttribute("productos", productosService.listarProducto());
        return "venta/agregarPedido";
    }

    @PostMapping("/añadirPedido/{id}")
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
                item.setTotal(Float.parseFloat(item.getProducto().getPrecioCompra()) *
                        item.getCantidad());
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            RequestDTO.ItemsVentaDTO nuevo_item = new RequestDTO.ItemsVentaDTO();
            nuevo_item.setCantidad(cantidad);
            Producto p = productosService.obtenerProductoPorId(id);
            nuevo_item.setProducto(p);
            nuevo_item.setTotal(cantidad *
                    Float.parseFloat(nuevo_item.getProducto().getPrecioCompra()));
            sale.getItem().add(nuevo_item);
        }

        session.setAttribute("sale", sale);

        model.addAttribute("productos", productosService.listarProducto());
        model.addAttribute("pedidos", sale);

        return "venta/agregarPedido";
    }

    @GetMapping("/eliminarPedido/{id}")
    public String eliminarPedido(
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
        model.addAttribute("pedidos", sale);

        return "venta/agregarPedido";

    }

    @PostMapping("/registroPedido")
    public String registrarVenta(
            @RequestParam("total_reg") double total,
            Model model, HttpSession session) {

        RequestDTO sale = (RequestDTO) session.getAttribute("sale");
        Empleado empleado = (Empleado) session.getAttribute("empleado");

        sale.setId_usuario(empleado.getUsuario().getIdUsuario());
        session.setAttribute("sale", sale);

        model.addAttribute("pedidos", sale);
        return "venta/registroPedido";
    }

    @PostMapping("/savePedido")
    public String saveVenta(HttpSession session, Model model) {
        RequestDTO sale = (RequestDTO) session.getAttribute("sale");
        Empleado empleado = (Empleado) session.getAttribute("empleado");

        sale.setId_usuario(empleado.getUsuario().getIdUsuario());
        sale.setTipo("FACTURA");

        pedidoService.crearPedidoProveedor(sale);

        session.removeAttribute("sale");
        model.addAttribute("ventas", pedidoService.getPedidosProveedor());
        return "redirect:/inventario/pedido/pedidos";
    }

    @GetMapping("/detallePedido/{id}")
    public String ver(
            @PathVariable int id,
            HttpSession session,
            Model model) {

        PedidoProveedor pedidos = pedidoService.obtenerPedidoProveedorPorId(id);

        RequestDTO sale = new RequestDTO();
        sale.setItem(new ArrayList<>());
        sale.setId_usuario(pedidos.getUser().getIdUsuario());

        for (DetallePedido pedido : pedidos.getDetallePedidos()) {
            RequestDTO.ItemsVentaDTO nuevo_item = new RequestDTO.ItemsVentaDTO();
            nuevo_item.setCantidad(pedido.getCantidad());
            nuevo_item.setProducto(pedido.getProducto());
            nuevo_item.setTotal(Float.parseFloat(nuevo_item.getProducto().getPrecioCompra())
                    * nuevo_item.getCantidad());
            sale.getItem().add(nuevo_item);
        }

        session.setAttribute("sale", sale);

        model.addAttribute("pedidos", sale);
        model.addAttribute("productos", productosService.listarProducto());
        model.addAttribute("id", pedidos.getIdPedido());
        model.addAttribute("mostrar", true);
        return "venta/registroPedido";
    }

    @GetMapping("/editarPedido")
    public String editarVenta(Model model, HttpSession session) {

        RequestDTO sale = (RequestDTO) session.getAttribute("sale");

        model.addAttribute("pedidos", sale);
        model.addAttribute("productos", productosService.listarProducto());

        return "venta/editarPedido";
    }

    @GetMapping("/cancelar")
    public String cancelar(Model model, HttpSession session) {

        session.removeAttribute("sale");

        model.addAttribute("pedidos", pedidoService.getPedidosProveedor());
        return "venta/pedidos";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarPedido(Model model, @PathVariable int id, EstadoRequestDTO estadoRequestDTO) {

        try {
            pedidoService.actualizarEstado(id, estadoRequestDTO);
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
        }

        model.addAttribute("pedidos", pedidoService.getPedidosProveedor());
        return "venta/pedidos";
    }

}
