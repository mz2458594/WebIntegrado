package com.example.domain.ecommerce.services;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.example.domain.ecommerce.dto.RequestDTO;
import com.example.domain.ecommerce.models.entities.Detalle_venta;
import com.example.domain.ecommerce.models.entities.Producto;
import com.example.domain.ecommerce.models.entities.Usuario;
import com.example.domain.ecommerce.models.entities.Venta;
import com.example.domain.ecommerce.repositories.VentasDAO;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VentaService {

    @Autowired
    private VentasDAO ventasDAO;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProductoService productosService;

    @Autowired
    private ComprobanteService comprobanteService;

    public List<Venta> obtenerVentas() {
        return (List<Venta>) ventasDAO.findAll();
    }

    @Transactional
    public Venta crearVenta(RequestDTO data) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(data.getId_usuario());

        Venta venta = new Venta();
        venta.setFechaVenta(Timestamp.from(Instant.now()));
        venta.setUsuario(usuario);

        List<Detalle_venta> listasProductos = new ArrayList<>();

        double total = 0.00;

        for (RequestDTO.ItemsVentaDTO productos : data.getItem()) {

            Producto p = productosService.obtenerProductoPorId(productos.getProducto().getIdProducto());

            Detalle_venta vp = new Detalle_venta();
            vp.setCantidad(productos.getCantidad());
            vp.setProducto(p);
            vp.setVenta(venta);

            // Para disminuir la cantidad de productos luego de registrar una venta
            productosService.actualizarStockProducto(p, productos.getCantidad());
            double subtotal = productos.getCantidad() * Double.parseDouble(p.getPrecioVenta());
            vp.setSubtotal(subtotal);

            total += vp.getSubtotal();

            listasProductos.add(vp);

        }

        venta.setTotal(total);
        venta.setVentaProductos(listasProductos);

        Venta ventaGuardada = ventasDAO.save(venta);

        comprobanteService.generarComprobante(ventaGuardada, data.getTipo(), data.getRuc(), data.getRazon());

        return ventaGuardada;

    }

    public void deleteVenta(int id) {
        Optional<Venta> venta = ventasDAO.findById(Long.valueOf(id));

        if (venta.isEmpty()) {
            throw new EntityNotFoundException("Venta con id " + id + " no encontrado");
        }

        Venta v = venta.get();

        for (Detalle_venta ve : v.getVentaProductos()) {
            productosService.devolverStock(ve.getProducto(), ve.getCantidad());
        }

        ventasDAO.deleteById(Long.valueOf(id));

    }
}
