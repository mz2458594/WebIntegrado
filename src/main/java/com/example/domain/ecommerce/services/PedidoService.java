package com.example.domain.ecommerce.services;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.ecommerce.dto.RequestDTO;
import com.example.domain.ecommerce.models.entities.Detalle_pedido;
import com.example.domain.ecommerce.models.entities.Pedido;
import com.example.domain.ecommerce.models.entities.Producto;
import com.example.domain.ecommerce.models.entities.Usuario;
import com.example.domain.ecommerce.models.enums.EstadoPedido;
import com.example.domain.ecommerce.repositories.PedidoDAO;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PedidoService {
    @Autowired
    private PedidoDAO pedidoDAO;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProductoService productosService;

    public Pedido crearPedido(RequestDTO data) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(data.getId_usuario());

        Pedido pedido = new Pedido();
        pedido.setFechaPedido(Timestamp.from(Instant.now()));
        pedido.setUser(usuario);

        List<Detalle_pedido> lista_pedidos = new ArrayList<>();

        double total = 0.00;

        for (RequestDTO.ItemsVentaDTO productos : data.getItem()) {

            Producto p = productosService.obtenerProductoPorId(productos.getProducto().getIdProducto());

            Detalle_pedido vp = new Detalle_pedido();
            vp.setCantidad(productos.getCantidad());
            vp.setProducto(p);
            vp.setPedido(pedido);

            total += vp.getSubtotal();

            lista_pedidos.add(vp);

        }

        pedido.setTotal(total);
        pedido.setDetallePedidos(lista_pedidos);

        return pedidoDAO.save(pedido);

    }

    public List<Pedido> getPedidos() {
        return (List<Pedido>) pedidoDAO.findAll();
    }

    public void deletePedido(int id) {
        Optional<Pedido> pedido = pedidoDAO.findById(Long.valueOf(id));

        if (pedido.isEmpty()) {
            throw new EntityNotFoundException("Pedido con id " + id + " no encontrado");
        }

        Pedido p = pedido.get();

        for (Detalle_pedido pe : p.getDetallePedidos()) {
            productosService.devolverStock(pe.getProducto(), pe.getCantidad());
        }

        pedidoDAO.deleteById(Long.valueOf(id));

    }

    public Pedido obtenerPedidoPorId(int id) {

        Optional<Pedido> pedido = pedidoDAO.findById(Long.valueOf(id));

        if (pedido.isEmpty()) {
            throw new EntityNotFoundException("Pedido con id " + id + " no encontrado");

        }

        return pedido.get();
    }

    public void actualizarEstado(int id, String estado) {
        Optional<Pedido> pedido = pedidoDAO.findById(Long.valueOf(id));

        if (pedido.isEmpty()) {
            throw new EntityNotFoundException("Venta con id " + id + " no encontrado");
        }

        Pedido pedido2 = pedido.get();

        if (estado != null) {

            switch (estado) {
                case "CANCELADO":
                    pedido2.setEstado(EstadoPedido.CANCELADO);
                    break;
                case "COMPLETADO":
                    pedido2.setEstado(EstadoPedido.COMPLETADO);
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

}
