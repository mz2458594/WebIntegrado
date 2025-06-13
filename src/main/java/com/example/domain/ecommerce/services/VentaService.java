package com.example.domain.ecommerce.services;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import com.example.domain.ecommerce.dto.RequestDTO;
import com.example.domain.ecommerce.models.entities.Comprobante;
import com.example.domain.ecommerce.models.entities.Detalle_venta;
import com.example.domain.ecommerce.models.entities.Producto;
import com.example.domain.ecommerce.models.entities.Usuario;
import com.example.domain.ecommerce.models.entities.Venta;
import com.example.domain.ecommerce.repositories.VentasDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class VentaService {

    private final VentasDAO ventasDAO;

    private final UsuarioService usuarioService;

    private final ProductoService productosService;

    private final ComprobanteService comprobanteService;

    private final PedidoService pedidoService;

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

        Comprobante comprobante = comprobanteService.generarComprobanteVenta(ventaGuardada, data.getTipo(), data.getRuc(),
                data.getRazon());

        ventaGuardada.setComprobante(comprobante);

        pedidoService.crearPedidoUsuario(data);

        return ventaGuardada;
    }

    public List<Venta> getVentas() {
        return (List<Venta>) ventasDAO.findAll();
    }

}
