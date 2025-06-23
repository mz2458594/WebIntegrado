package com.example.domain.ecommerce.factories;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.domain.ecommerce.dto.RequestDTO;
import com.example.domain.ecommerce.models.entities.DetallePedido;
import com.example.domain.ecommerce.models.entities.Pedido;
import com.example.domain.ecommerce.models.entities.PedidoUsuario;
import com.example.domain.ecommerce.models.entities.Producto;
import com.example.domain.ecommerce.models.entities.TarifaEnvio;
import com.example.domain.ecommerce.models.entities.Usuario;
import com.example.domain.ecommerce.models.enums.EstadoPedido;
import com.example.domain.ecommerce.repositories.ClienteDAO;
import com.example.domain.ecommerce.repositories.PedidoUsuarioDAO;
import com.example.domain.ecommerce.repositories.TarifaDAO;
import com.example.domain.ecommerce.services.ProductoService;
import com.example.domain.ecommerce.services.UsuarioService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PedidoUsuarioFactory implements PedidoFactory {

    private final UsuarioService usuarioService;

    private final ProductoService productosService;

    private final PedidoUsuarioDAO pedidoUsuarioDAO;

    private final ClienteDAO clienteDAO;

    private final TarifaDAO tarifaDAO;

    @Override
    public Pedido crearPedido(RequestDTO data) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(data.getId_usuario());

        PedidoUsuario pedido = new PedidoUsuario();
        pedido.setFechaPedido(Timestamp.from(Instant.now()));
        LocalDateTime fechaEntrega = LocalDateTime.now().plusDays(7);
        pedido.setFechaEntrega(Timestamp.valueOf(fechaEntrega));
        pedido.setUser(usuario);

        List<DetallePedido> lista_pedidos = new ArrayList<>();

        BigDecimal total = BigDecimal.ZERO;
        BigDecimal pesoTotal = BigDecimal.ZERO;

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

            BigDecimal peso = BigDecimal.valueOf(p.getPeso());
            pesoTotal = pesoTotal.add(peso.multiply(cantidad));

            lista_pedidos.add(vp);

        }
        BigDecimal costoEnvio = BigDecimal.ZERO;

        String departamento = clienteDAO.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado para el usuario"))
                .getDireccion()
                .getDepartamento();

        TarifaEnvio tarifaEnvio = tarifaDAO.findByDepartamento(departamento)
                .orElseThrow(() -> new RuntimeException(
                        "No se encontro ninguna tarifa para el departamento " + departamento));

        BigDecimal precioDepartamento = tarifaEnvio.getPrecio_envio();

        costoEnvio = pesoTotal.multiply(BigDecimal.valueOf(2.6)).add(precioDepartamento).setScale(2,
                    RoundingMode.UP);

        BigDecimal totalFinal = total.add(costoEnvio).setScale(2,RoundingMode.UP);

        pedido.setEstado(EstadoPedido.PENDIENTE);
        pedido.setTotal(totalFinal);
        pedido.setDetallePedidos(lista_pedidos);

        return pedidoUsuarioDAO.save(pedido);
    }
}
