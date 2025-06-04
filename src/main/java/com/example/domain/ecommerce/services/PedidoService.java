package com.example.domain.ecommerce.services;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.ecommerce.dto.EstadoRequestDTO;
import com.example.domain.ecommerce.dto.RequestDTO;
import com.example.domain.ecommerce.models.entities.Comprobante;
import com.example.domain.ecommerce.models.entities.DetallePedido;
import com.example.domain.ecommerce.models.entities.PedidoProveedor;
import com.example.domain.ecommerce.models.entities.Producto;
import com.example.domain.ecommerce.models.entities.Usuario;
import com.example.domain.ecommerce.models.enums.EstadoPedido;
import com.example.domain.ecommerce.repositories.PedidoProveedorDAO;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PedidoService {
    @Autowired
    private PedidoProveedorDAO pedidoProveedorDAO;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProductoService productosService;

    @Autowired
    private ComprobanteService comprobanteService;

    public PedidoProveedor crearPedido(RequestDTO data) {

        data.setTipo("FACTURA");
        Usuario usuario = usuarioService.obtenerUsuarioPorId(data.getId_usuario());

        PedidoProveedor pedido = new PedidoProveedor();
        pedido.setFechaPedido(Timestamp.from(Instant.now()));
        pedido.setUser(usuario);

        List<DetallePedido> lista_pedidos = new ArrayList<>();

        double total = 0.00;

        for (RequestDTO.ItemsVentaDTO productos : data.getItem()) {

            Producto p = productosService.obtenerProductoPorId(productos.getProducto().getIdProducto());

            DetallePedido vp = new DetallePedido();
            vp.setCantidad(productos.getCantidad());
            vp.setProducto(p);
            vp.setPedido(pedido);
            double subtotal = productos.getCantidad() * Double.parseDouble(p.getPrecioCompra());
            vp.setSubtotal(subtotal);

            total += vp.getSubtotal();

            lista_pedidos.add(vp);

        }

        pedido.setEstado(EstadoPedido.PROCESANDO);
        pedido.setTotal(total);
        pedido.setDetallePedidos(lista_pedidos);

        PedidoProveedor pedido2 = pedidoProveedorDAO.save(pedido);

        Comprobante comprobante = comprobanteService.generarComprobantePedido(pedido2, data.getRuc(), data.getRazon());

        pedido2.setComprobante(comprobante);

        return pedido2;

    }

    public List<PedidoProveedor> getPedidos() {
        return (List<PedidoProveedor>) pedidoProveedorDAO.findAll();
    }

    public void deletePedido(int id) {
        Optional<PedidoProveedor> pedido = pedidoProveedorDAO.findById(Long.valueOf(id));

        if (pedido.isEmpty()) {
            throw new EntityNotFoundException("Pedido con id " + id + " no encontrado");
        }

        pedidoProveedorDAO.deleteById(Long.valueOf(id));

    }

    public PedidoProveedor obtenerPedidoPorId(int id) {

        Optional<PedidoProveedor> pedido = pedidoProveedorDAO.findById(Long.valueOf(id));

        if (pedido.isEmpty()) {
            throw new EntityNotFoundException("Pedido con id " + id + " no encontrado");

        }

        return pedido.get();
    }

    public void actualizarEstado(int id, EstadoRequestDTO estadoRequestDTO) {
        Optional<PedidoProveedor> pedido = pedidoProveedorDAO.findById(Long.valueOf(id));

        if (pedido.isEmpty()) {
            throw new EntityNotFoundException("Venta con id " + id + " no encontrado");
        }

        PedidoProveedor pedido2 = pedido.get();

        if (estadoRequestDTO.getEstado() != null) {

            if (pedido2.getEstado().equals("COMPLETADO") || pedido2.getEstado().equals("CANCELADO")) {
                return;
            } else {
                switch (estadoRequestDTO.getEstado()) {
                    case "CANCELADO":
                        pedido2.setEstado(EstadoPedido.CANCELADO);
                        break;
                    case "COMPLETADO":
                        pedido2.setEstado(EstadoPedido.COMPLETADO);
                        for (DetallePedido pe : pedido2.getDetallePedidos()) {
                            productosService.aumentarStock(pe.getProducto(), pe.getCantidad());
                        }
                        break;
                    case "EN ESPERA":
                        pedido2.setEstado(EstadoPedido.EN_ESPERA);
                        break;
                    case "PROCESANDO":
                        pedido2.setEstado(EstadoPedido.PROCESANDO);
                        break;
                    default:
                        break;
                }
            }
        }

        pedidoProveedorDAO.save(pedido2);

    }

}
