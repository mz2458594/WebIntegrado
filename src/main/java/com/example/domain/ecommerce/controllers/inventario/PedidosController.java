package com.example.domain.ecommerce.controllers.inventario;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.domain.ecommerce.dto.EstadoRequestDTO;
import com.example.domain.ecommerce.dto.ProductRequestDTO;
import com.example.domain.ecommerce.dto.RequestDTO;
import com.example.domain.ecommerce.dto.VentaRequestDTO;
import com.example.domain.ecommerce.models.entities.DetallePedido;
import com.example.domain.ecommerce.models.entities.Empleado;
import com.example.domain.ecommerce.models.entities.PedidoProveedor;
import com.example.domain.ecommerce.models.entities.Producto;
import com.example.domain.ecommerce.services.PedidoService;
import com.example.domain.ecommerce.services.ProductoService;
import com.example.domain.ecommerce.services.ProveedorService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/inventario/pedido")
public class PedidosController {

    @Autowired
    private ProductoService productosService;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ProveedorService proveedorService;

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
            model.addAttribute("pedidos", pedidoService.getPedidosProveedor());
            return "venta/pedidos";
        }

        model.addAttribute("productos", productosService.listarProducto());
        model.addAttribute("proveedores", proveedorService.obtenerProveedores());
        return "venta/agregarPedido";
    }

    @PostMapping("/registroPedido")
    public String registrarPedido(
            VentaRequestDTO ventaRequestDTOs,
            Model model, HttpSession session) {

        Empleado empleado = (Empleado) session.getAttribute("empleado");

        RequestDTO sale = new RequestDTO();

        float total = 0;

        sale.setItem(new ArrayList<>());

        List<ProductRequestDTO> productRequestDTOs = ventaRequestDTOs.getProductos();

        if (productRequestDTOs == null) {
            model.addAttribute("productos", productosService.listarProducto());

            return "redirect:/inventario/pedido/agregarPedido";
        }

        for (ProductRequestDTO productRequestDTO : productRequestDTOs) {
            RequestDTO.ItemsVentaDTO nuevo_item = new RequestDTO.ItemsVentaDTO();
            nuevo_item.setCantidad(productRequestDTO.getCantidad());
            Producto p = productosService.obtenerProductoPorId(productRequestDTO.getId());
            nuevo_item.setProducto(p);
            nuevo_item.setTotal(
                    productRequestDTO.getCantidad() * Float.parseFloat(nuevo_item.getProducto().getPrecioVenta()));
            total += nuevo_item.getTotal();
            sale.getItem().add(nuevo_item);
        }

        sale.setId_usuario(empleado.getId());

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
            nuevo_item.setTotal(Float.parseFloat(pedido.getProducto().getPrecioCompra())
                    * pedido.getCantidad());
            sale.getItem().add(nuevo_item);
        }

        model.addAttribute("pedidos", sale);
        model.addAttribute("productos", productosService.listarProducto());
        model.addAttribute("id", pedidos.getIdPedido());
        model.addAttribute("mostrar", true);
        return "venta/registroPedido";
    }

    @GetMapping("/editarPedido")
    public String editarVenta(Model model, HttpSession session) {

        model.addAttribute("productos", productosService.listarProducto());
        model.addAttribute("proveedores", proveedorService.obtenerProveedores());

        return "venta/agregarPedido";
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
            pedidoService.actualizarEstadoProveedor(id, estadoRequestDTO);
        } catch (EntityNotFoundException | IllegalStateException e) {
            model.addAttribute("error", e.getMessage());
        }

        model.addAttribute("pedidos", pedidoService.getPedidosProveedor());
        return "redirect:/inventario/pedido/pedidos";
    }

}
