package com.example.domain.ecommerce.controllers.inventario;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.example.domain.ecommerce.services.ProductoService;
import com.example.domain.ecommerce.services.VentaService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@SessionAttributes({ "compra"})
public class VentasController {

    // @Autowired
    // private VentaService ventasService;
   

    // @Autowired
    // private ProductoService productosService;

    // List<DetalleVenta> detalles = new ArrayList<DetalleVenta>();

    // List<DetalleVenta> lista_temporal = new ArrayList<DetalleVenta>();

    // Ventas venta = new Ventas();

    // int id_vent;

    // @PostMapping("/registroVenta")
    // public String registrarVenta(
    //         @RequestParam("total_reg") double total,
    //         Model model, HttpSession session, SessionStatus status) {

    //     java.sql.Date date = new java.sql.Date(new java.util.Date().getTime());

    //     int id = (int) session.getAttribute("id");
    //     Ventas re = new Ventas();
    //     re.setIdUsuario(id);
    //     re.setTotal(total);
    //     re.setFechaVenta(date);

    //     Ventas nueva = ventasDAO.save(re);

    //     for (DetalleVenta detalle_ventas : detalles) {
    //         detalle_ventas.setIdVentas(nueva.getIdVventa());
    //         detalleService.agregarDetalle(detalle_ventas);
    //     }

    //     id_vent = nueva.getIdVventa();

    //     model.addAttribute("detalles", detalles);
    //     model.addAttribute("productos", ventasService.listarProductos());
    //     model.addAttribute("id", id);
    //     model.addAttribute("id_venta", nueva.getIdVventa());
    //     return "registroVenta";
    // }

    // @PostMapping("/añadir")
    // public String agregar(
    //         @RequestParam("productInput") String nombre,
    //         @RequestParam("canti") int cantidad,
    //         Model model) {

    //     DetalleVenta detalle_ventas = new DetalleVenta();
    //     Producto producto = new Producto();
    //     int contador = 0;
    //     Producto optional = productosService.obtenerProductoPorNombre(nombre);

    //     producto = optional;

    //     if (producto.getStock() > 0) {

    //         if (cantidad > producto.getStock()) {
    //             model.addAttribute("error", "No hay existencias");
    //         } else {
    //             detalle_ventas.setIdProducto(producto.getIdProducto());
    //             detalle_ventas.setCantidadProductos(cantidad);

    //             for (DetalleVenta detalle_v : detalles) {
    //                 if (detalle_v.getIdProducto() == detalle_ventas.getIdProducto()) {
    //                     if ((detalle_v.getCantidadProductos() + cantidad) > producto.getStock()) {
    //                         model.addAttribute("error", "No hay existencias");
    //                         contador = 1;
    //                     } else {
    //                         detalle_v.setCantidadProductos(detalle_v.getCantidadProductos() + cantidad);
    //                         contador = 1;
    //                     }
    //                 }
    //             }

    //             if (contador == 0) {
    //                 detalles.add(detalle_ventas);
    //             }
    //         }

    //     } else {
    //         model.addAttribute("error", "No hay existencias");
    //     }

    //     model.addAttribute("productos", ventasService.listarProductos());
    //     model.addAttribute("detalles", detalles);

    //     return "agregarVenta";
    // }

    // @GetMapping("/eliminar_prod/{id}")
    // public String eliminarProd(
    //         @PathVariable Integer id,
    //         Model model) {

    //     List<DetalleVenta> detalle_ventas = new ArrayList<DetalleVenta>();
    //     for (DetalleVenta detalle_ventas2 : detalles) {
    //         if (detalle_ventas2.getIdProducto() != id) {
    //             detalle_ventas.add(detalle_ventas2);
    //         }
    //     }

    //     detalles = detalle_ventas;

    //     model.addAttribute("productos", ventasService.listarProductos());
    //     model.addAttribute("detalles", detalles);

    //     return "agregarVenta";

    // }

    // @GetMapping("/nuevaVenta")
    // public String nuevaVenta(Model model) {

    //     detalles.clear();
    //     id_vent = 0;

    //     model.addAttribute("ventas", ventasService.listarVentas());
    //     model.addAttribute("usuarios", ventasService.listarUsuarios());

    //     return "nuevaVenta";
    // }

    // @GetMapping("/agregarVenta")
    // public String agregarVenta(Model model) {
    //     model.addAttribute("productos", productosService.listarProducto());
    //     return "agregarVenta";
    // }

    // @GetMapping("/editarVenta")
    // public String editarVenta(Model model, HttpSession session, SessionStatus status) {

    //     int id = (int) session.getAttribute("id");

    //     model.addAttribute("detalles", detalles);
    //     model.addAttribute("productos", productosService.listarProducto());
    //     model.addAttribute("id", id);
    //     return "editarVenta";
    // }

    // @PostMapping("/eliminarVenta")
    // public String eliminarVenta(
    //         @RequestParam("id_eliminar") int id_venta,
    //         Model model) {

    //     List<DetalleVenta> eliminar_detalles = detalleService.obtenerVentasPorIdVenta(id_venta);

    //     for (DetalleVenta detalleVenta : eliminar_detalles) {
    //         Optional<Producto> optional = productosService.obtenerProductoPorId(detalleVenta.getIdProducto());
    //         if (optional.isPresent()) {
    //             Producto producto = optional.get();
    //             producto.setStock(producto.getStock() + detalleVenta.getCantidadProductos());
    //             productosService.guardarProducto(producto);
    //         }
    //     }

    //     ventasService.eliminarVenta(id_venta);

    //     model.addAttribute("ventas", ventasService.listarVentas());
    //     return "redirect:/nuevaVenta";

    // }

    // @PostMapping("/ver")
    // public String ver(
    //         @RequestParam("id_detalle") int id_venta,
    //         Model model) {

    //     id_vent = id_venta;
    //     Optional<Ventas> v = ventasService.obtenerVentasPorId(id_venta);

    //     Ventas venta = v.get();
    //     detalles = detalleService.obtenerVentasPorIdVenta(venta.getIdVventa());

    //     for (DetalleVenta detalle_venta : detalles) {
    //         DetalleVenta copia = new DetalleVenta();
    //         copia.setCantidadProductos(detalle_venta.getCantidadProductos());
    //         copia.setIdDetalleVenta(detalle_venta.getIdDetalleVenta());
    //         copia.setIdVentas(detalle_venta.getIdVentas());
    //         copia.setIdProducto(detalle_venta.getIdProducto());
    //         lista_temporal.add(copia);
    //     }

    //     model.addAttribute("detalles", detalles);
    //     model.addAttribute("productos", ventasService.listarProductos());
    //     model.addAttribute("id", venta.getIdUsuario());
    //     model.addAttribute("id_venta", venta.getIdVventa());
    //     return "registroVenta";
    // }

    // @GetMapping("/eliminar/{id}")
    // public String eliminar(
    //         @PathVariable Integer id,
    //         HttpSession session, SessionStatus status,
    //         Model model) {

    //     int id_usuario = (int) session.getAttribute("id");

    //     List<DetalleVenta> detalle_ventas = new ArrayList<DetalleVenta>();
    //     for (DetalleVenta detalle_ventas2 : detalles) {
    //         if (detalle_ventas2.getIdProducto() != id) {
    //             detalle_ventas.add(detalle_ventas2);
    //         } else {
    //             Optional<Producto> optional = productosService.obtenerProductoPorId(id);
    //             if (optional.isPresent()) {
    //                 Producto producto = optional.get();
    //                 producto.setStock(producto.getStock() + detalle_ventas2.getCantidadProductos());
    //                 productosService.guardarProducto(producto);
    //             }
    //         }
    //     }

    //     detalles = detalle_ventas;

    //     model.addAttribute("productos", ventasService.listarProductos());
    //     model.addAttribute("detalles", detalles);
    //     model.addAttribute("id", id_usuario);

    //     return "editarVenta";

    // }

    // @PostMapping("/añadirV")
    // public String agregarV(
    //         @RequestParam("productInput") String nombre,
    //         @RequestParam("canti") int cantidad,
    //         Model model) {

    //     DetalleVenta detalle_ventas = new DetalleVenta();
    //     Producto producto = new Producto();
    //     int contador = 0;
    //     Producto optional = productosService.obtenerProductoPorNombre(nombre);

    //     producto = optional;

    //     if (producto.getStock() > 0) {

    //         if (cantidad > producto.getStock()) {
    //             model.addAttribute("error", "La cantidad excede el stock");
    //         } else {
    //             detalle_ventas.setIdProducto(producto.getIdProducto());
    //             detalle_ventas.setCantidadProductos(cantidad);

    //             for (DetalleVenta detalle_v : detalles) {
    //                 if (detalle_v.getIdProducto() == detalle_ventas.getIdProducto()) {
    //                     producto.setStock(producto.getStock() + detalle_v.getCantidadProductos());
    //                     productosService.guardarProducto(producto);
    //                     if ((detalle_v.getCantidadProductos() + cantidad) > (producto.getStock())) {
    //                         model.addAttribute("error", "No hay existencias");
    //                         contador = 1;
    //                     } else {
    //                         detalle_v.setCantidadProductos(detalle_v.getCantidadProductos() + cantidad);
    //                         contador = 1;
    //                         producto.setStock(producto.getStock() - detalle_v.getCantidadProductos());
    //                     }
    //                 }
    //             }

    //             if (contador == 0) {
    //                 detalles.add(detalle_ventas);
    //             }
    //         }

    //     } else {
    //         model.addAttribute("error", "No hay existencias");
    //     }

    //     productosService.guardarProducto(producto);

    //     model.addAttribute("productos", ventasService.listarProductos());
    //     model.addAttribute("detalles", detalles);

    //     return "editarVenta";
    // }

    // @PostMapping("/actualizarV")
    // public String actualizar(
    //         @RequestParam("total_reg") double total,
    //         Model model, HttpSession session, SessionStatus status) {

    //     int idVenta = id_vent;
    //     int id = (int) session.getAttribute("id");
    //     Optional<Ventas> venta = ventasDAO.findById(Long.valueOf(idVenta));
    //     Ventas re = venta.get();
    //     re.setTotal(total);

    //     ventasDAO.save(re);

    //     detalleService.eliminarDetalle(idVenta);

    //     for (DetalleVenta detalle_ventas : detalles) {
    //         detalle_ventas.setIdVentas(re.getIdVventa());
    //         Optional<Producto> optional = productosService.obtenerProductoPorId(detalle_ventas.getIdProducto());
    //         Producto producto = optional.get();
    //         producto.setStock(producto.getStock() + detalle_ventas.getCantidadProductos());
    //         producto.setStock(producto.getStock() - detalle_ventas.getCantidadProductos());
    //         productosService.guardarProducto(producto);
    //         detalleService.actualizarDetalle(detalle_ventas);
    //     }

    //     model.addAttribute("detalles", detalles);
    //     model.addAttribute("productos", ventasService.listarProductos());
    //     model.addAttribute("id", id);
    //     model.addAttribute("id_venta", re.getIdVventa());
    //     return "registroVenta";
    // }

    // @GetMapping("/cancelar_edit")
    // public String cancelar(Model model) {

    //     for (DetalleVenta detalleVentaActual : detalles) {
    //         boolean existeEnVentaOriginal = lista_temporal.stream()
    //                 .anyMatch(detalleOriginal -> detalleOriginal.getIdProducto() == detalleVentaActual.getIdProducto());

    //         Optional<Producto> optional = productosService.obtenerProductoPorId(detalleVentaActual.getIdProducto());
    //         if (optional.isPresent()) {
    //             Producto producto = optional.get();

    //             if (existeEnVentaOriginal) {
    //                 DetalleVenta detalleOriginal = lista_temporal.stream()
    //                         .filter(detalle -> detalle.getIdProducto() == detalleVentaActual.getIdProducto())
    //                         .findFirst()
    //                         .orElse(null);

    //                 if (detalleOriginal != null) {
    //                     int diferencia = detalleVentaActual.getCantidadProductos() - detalleOriginal.getCantidadProductos();
    //                     producto.setStock(producto.getStock() + diferencia);
    //                 }
    //             } else {
    //                 producto.setStock(producto.getStock() - detalleVentaActual.getCantidadProductos());
    //             }

    //             productosService.guardarProducto(producto);
    //         }
    //     }

    //     lista_temporal.clear();
    //     detalles.clear();
    //     id_vent = 0;

    //     model.addAttribute("ventas", ventasService.listarVentas());
    //     model.addAttribute("usuarios", ventasService.listarUsuarios());

    //     return "nuevaVenta";
    // }

}
