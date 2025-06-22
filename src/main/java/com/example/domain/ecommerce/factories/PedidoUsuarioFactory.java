package com.example.domain.ecommerce.factories;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.domain.ecommerce.dto.RequestDTO;
import com.example.domain.ecommerce.models.entities.Comprobante;
import com.example.domain.ecommerce.models.entities.DetallePedido;
import com.example.domain.ecommerce.models.entities.Pedido;
import com.example.domain.ecommerce.models.entities.PedidoUsuario;
import com.example.domain.ecommerce.models.entities.Producto;
import com.example.domain.ecommerce.models.entities.Usuario;
import com.example.domain.ecommerce.models.enums.EstadoPedido;
import com.example.domain.ecommerce.repositories.PedidoUsuarioDAO;
import com.example.domain.ecommerce.services.ProductoService;
import com.example.domain.ecommerce.services.UsuarioService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PedidoUsuarioFactory implements PedidoFactory {

    private final UsuarioService usuarioService;

    private final ProductoService productosService;

    private final PedidoUsuarioDAO pedidoUsuarioDAO;

    @Override
    public Pedido crearPedido(RequestDTO data) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(data.getId_usuario());

        PedidoUsuario pedido = new PedidoUsuario();
        pedido.setFechaPedido(Timestamp.from(Instant.now()));
        LocalDateTime fechaEntrega = LocalDateTime.now().plusDays(7);
        pedido.setFechaEntrega(Timestamp.valueOf(fechaEntrega));
        pedido.setUser(usuario);

        List<DetallePedido> lista_pedidos = new ArrayList<>();

        double total = 0.00;

        for (RequestDTO.ItemsVentaDTO productos : data.getItem()) {

            Producto p = productosService.obtenerProductoPorId(productos.getProducto().getIdProducto());

            DetallePedido vp = new DetallePedido();
            vp.setCantidad(productos.getCantidad());
            vp.setProducto(p);
            vp.setPedido(pedido);
            double subtotal = productos.getCantidad() * Double.parseDouble(p.getPrecioVenta());
            vp.setSubtotal(subtotal);

            total += vp.getSubtotal();

            lista_pedidos.add(vp);

        }

        pedido.setEstado(EstadoPedido.PENDIENTE);
        pedido.setTotal(total);
        pedido.setDetallePedidos(lista_pedidos);

        return pedidoUsuarioDAO.save(pedido);
    }
}
