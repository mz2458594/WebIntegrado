package com.example.domain.ecommerce.controllers.inventario;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.domain.ecommerce.dto.ProductRequestDTO;
import com.example.domain.ecommerce.dto.RequestDTO;
import com.example.domain.ecommerce.dto.VentaRequestDTO;
import com.example.domain.ecommerce.models.entities.Empleado;
import com.example.domain.ecommerce.models.entities.Producto;
import com.example.domain.ecommerce.models.entities.Venta;
import com.example.domain.ecommerce.services.ProductoService;
import com.example.domain.ecommerce.services.VentaService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/inventario/ventas")
public class VentasInventarioController {

    @Autowired
    private VentaService ventasService;

    @Autowired
    private ProductoService productosService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/ventas")
    public String nuevaVenta(Model model) {

        model.addAttribute("ventas", ventasService.getVentas());

        return "venta/ventas";
    }

    @GetMapping("/agregarVenta")
    public String agregarVenta(Model model, HttpSession session) {

        Empleado empleado = (Empleado) session.getAttribute("empleado");

        if (empleado == null) {
            model.addAttribute("error", "No hay usuario logeado en el sistema");
            return "venta/ventas";
        }

        model.addAttribute("productos", productosService.listarProducto());
        return "venta/agregarVenta";
    }

    @PostMapping("/pago")
    public String pago(Model model,
            @ModelAttribute("productos") VentaRequestDTO ventaRequestDTOs,
            @RequestParam("numero") String ruc,
            @RequestParam("tipo") String tipo,
            HttpSession session) {
        RequestDTO sale = new RequestDTO();

        float total = 0;

        sale.setItem(new ArrayList<>());

        if ("ruc".equalsIgnoreCase(tipo)) {
            if (ruc == null || ruc.trim().isEmpty() || ruc.length() != 11) {
                model.addAttribute("error", "Debe ingresar un numero de RUC válido");
                model.addAttribute("productos", productosService.listarProducto());

                return "venta/agregarVenta";
            }
            sale.setRuc(ruc);
            sale.setTipo("FACTURA");
        } else if ("boleta".equalsIgnoreCase(tipo)) {
            sale.setTipo("BOLETA");
        } else {
            model.addAttribute("error", "Debe seleccionar un tipo de comprobante válido");
            model.addAttribute("productos", productosService.listarProducto());
            return "venta/agregarVenta";
        }

        List<ProductRequestDTO> productRequestDTOs = ventaRequestDTOs.getProductos();

        if (productRequestDTOs == null) {
            model.addAttribute("error", "No se han agregado productos");
            model.addAttribute("productos", productosService.listarProducto());

            return "venta/agregarVenta";
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

        session.setAttribute("sale", sale);

        model.addAttribute("venta", sale);
        return "venta/agregarMetodoPago";
    }

    @GetMapping("/vuelto")
    public String vuelto(Model model, HttpSession session, @RequestParam("efectivo") float efectivo) {

        RequestDTO sale = (RequestDTO) session.getAttribute("sale");
        Empleado empleado = (Empleado) session.getAttribute("empleado");

        if (empleado == null) {
        model.addAttribute("venta", sale);
        model.addAttribute("error", "No hay usuario logeado en el sistema");
        return "venta/agregarMetodoPago";
        }

        double total = 0.00;

        for (RequestDTO.ItemsVentaDTO item : sale.getItem()) {
            total += item.getTotal();
        }

        if (efectivo < total) {
            model.addAttribute("error", "El efectivo ingresado es menor al total de la venta");
            model.addAttribute("venta", sale);
            return "venta/agregarMetodoPago";

        }

        sale.setId_usuario(empleado.getUsuario().getIdUsuario());
        Venta venta = ventasService.crearVenta(sale);

        model.addAttribute("efectivo", efectivo);
        model.addAttribute("vuelto", (efectivo - total));
        model.addAttribute("id", venta.getComprobante().getId());
        session.removeAttribute("sale");
        return "venta/MostrarVuelto";
    }

    @PostMapping("/anular")
    public String anular(@RequestParam("email") String email,
            @RequestParam("password") String password, Model model, HttpSession session) {

        Empleado empleado = (Empleado) session.getAttribute("empleado");

        if (empleado == null) {
            model.addAttribute("productos", productosService.listarProducto());
            model.addAttribute("error", "No hay usuario logeado en el sistema");
            return "venta/agregarVenta";
        } else {
            if (empleado.getUsuario().getEmail().equals(email)
                    && passwordEncoder.matches(password, empleado.getUsuario().getPassword())) {
                model.addAttribute("ventas", ventasService.getVentas());
                return "venta/ventas";
            } else {
                model.addAttribute("error", "Credenciales incorrectas");
                model.addAttribute("productos", productosService.listarProducto());
                return "venta/agregarVenta";
            }
        }

    }

}
