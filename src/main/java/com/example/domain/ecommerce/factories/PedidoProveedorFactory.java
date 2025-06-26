package com.example.domain.ecommerce.factories;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.domain.ecommerce.dto.EstadoRequestDTO;
import com.example.domain.ecommerce.dto.RequestDTO;
import com.example.domain.ecommerce.models.entities.Comprobante;
import com.example.domain.ecommerce.models.entities.DetallePedido;
import com.example.domain.ecommerce.models.entities.Pedido;
import com.example.domain.ecommerce.models.entities.PedidoProveedor;
import com.example.domain.ecommerce.models.entities.Producto;
import com.example.domain.ecommerce.models.entities.Usuario;
import com.example.domain.ecommerce.models.enums.EstadoPedido;
import com.example.domain.ecommerce.repositories.PedidoProveedorDAO;
import com.example.domain.ecommerce.services.ComprobanteService;
import com.example.domain.ecommerce.services.ProductoService;
import com.example.domain.ecommerce.services.UsuarioService;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PedidoProveedorFactory implements PedidoFactory {

    private final UsuarioService usuarioService;

    private final ProductoService productosService;

    private final PedidoProveedorDAO pedidoProveedorDAO;

    private final ComprobanteService comprobanteService;

    @Override
    public Pedido crearPedido(RequestDTO data) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(data.getId_usuario());

        PedidoProveedor pedido = new PedidoProveedor();
        pedido.setFechaPedido(Timestamp.from(Instant.now()));
        LocalDateTime fechaEntrega = LocalDateTime.now().plusDays(7);
        pedido.setFechaEntrega(Timestamp.valueOf(fechaEntrega));
        pedido.setUser(usuario);

        List<DetallePedido> lista_pedidos = new ArrayList<>();

        BigDecimal total = BigDecimal.ZERO;

        for (RequestDTO.ItemsVentaDTO productos : data.getItem()) {

            Producto p = productosService.obtenerProductoPorId(productos.getProducto().getIdProducto());

            BigDecimal cantidad = BigDecimal.valueOf(productos.getCantidad());
            BigDecimal precio = new BigDecimal(p.getPrecioVenta());

            DetallePedido vp = new DetallePedido();
            vp.setCantidad(productos.getCantidad());
            vp.setProducto(p);
            vp.setPedido(pedido);

            BigDecimal subtotal = cantidad.multiply(precio);
            vp.setSubtotal(subtotal);

            total = total.add(subtotal);

            lista_pedidos.add(vp);

        }

        pedido.setEstado(EstadoPedido.PENDIENTE);
        pedido.setTotal(total);
        pedido.setDetallePedidos(lista_pedidos);
        PedidoProveedor pedidoSave = pedidoProveedorDAO.save(pedido);

        Comprobante comprobante = comprobanteService.generarComprobantePedido(pedidoSave, data.getTipo(), data.getRuc(),
                data.getRazon());

        pedidoSave.setComprobante(comprobante);

        return pedidoProveedorDAO.save(pedidoSave);
    }

    @Override
    public void actualizarEstado(int id, EstadoRequestDTO estadoRequestDTO) {
        Optional<PedidoProveedor> pedido = pedidoProveedorDAO.findById(Long.valueOf(id));

        if (pedido.isEmpty()) {
            throw new EntityNotFoundException("Venta con id " + id + " no encontrado");
        }

        PedidoProveedor pedido2 = pedido.get();

        if (estadoRequestDTO.getEstado() != null) {

           if (pedido2.getEstado().equals(EstadoPedido.ENTREGADO) || pedido2.getEstado().equals(EstadoPedido.CANCELADO)) {
                throw new IllegalStateException("No se puede modificar un pedido " + pedido2.getEstado());
            } else {
                switch (estadoRequestDTO.getEstado()) {
                    case "CANCELADO":
                        pedido2.setEstado(EstadoPedido.CANCELADO);
                        pedido2.setComentario(estadoRequestDTO.getMotivoCancelado());
                        break;
                    case "CONFIRMADO":
                        pedido2.setEstado(EstadoPedido.CONFIRMADO);
                        for (DetallePedido pe : pedido2.getDetallePedidos()) {
                            productosService.aumentarStock(pe.getProducto(), pe.getCantidad());
                        }
                        break;
                    case "EN_CAMINO":
                        pedido2.setEstado(EstadoPedido.EN_CAMINO);
                        break;
                    case "PENDIENTE":
                        pedido2.setEstado(EstadoPedido.PENDIENTE);
                        break;
                    case "ENTREGADO":
                        pedido2.setEstado(EstadoPedido.ENTREGADO);
                        break;
                    default:
                        break;
                }
            }
        }

        pedidoProveedorDAO.save(pedido2);

    }
}
