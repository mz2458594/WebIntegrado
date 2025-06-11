package com.example.domain.ecommerce.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.example.domain.ecommerce.dto.EstadoRequestDTO;
import com.example.domain.ecommerce.dto.RequestDTO;
import com.example.domain.ecommerce.factories.PedidoProveedorFactory;
import com.example.domain.ecommerce.factories.PedidoUsuarioFactory;
import com.example.domain.ecommerce.models.entities.DetallePedido;
import com.example.domain.ecommerce.models.entities.Pedido;
import com.example.domain.ecommerce.models.entities.PedidoProveedor;
import com.example.domain.ecommerce.models.entities.PedidoUsuario;
import com.example.domain.ecommerce.models.enums.EstadoPedido;
import com.example.domain.ecommerce.repositories.PedidoDAO;
import com.example.domain.ecommerce.repositories.PedidoProveedorDAO;
import com.example.domain.ecommerce.repositories.PedidoUsuarioDAO;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PedidoService {

    private final PedidoProveedorDAO pedidoProveedorDAO;

    private final PedidoUsuarioDAO pedidoUsuarioDAO;

    private final ProductoService productosService;

    private final PedidoProveedorFactory pedidoProveedorFactory;

    private final PedidoUsuarioFactory pedidoUsuarioFactory;

    private final PedidoDAO pedidoDAO;

    public List<Pedido> getPedidos(){
        return (List<Pedido>) pedidoDAO.findAll();
    }

    public List<PedidoProveedor> getPedidosProveedor() {
        return (List<PedidoProveedor>) pedidoProveedorDAO.findAll();
    }

    public List<PedidoUsuario> getPedidosUsuario() {
        return (List<PedidoUsuario>) pedidoUsuarioDAO.findAll();
    }

    public Pedido obtenerPedidoPorId(int id){
        Optional<Pedido> pedidos = pedidoDAO.findById(Long.valueOf(id));

        if (pedidos.isEmpty()) {
            throw new RuntimeException("Pedido con id " + id + " no encontrado");
        }

        return pedidos.get();
    }

    public PedidoProveedor obtenerPedidoProveedorPorId(int id) {

        Optional<PedidoProveedor> pedido = pedidoProveedorDAO.findById(Long.valueOf(id));

        if (pedido.isEmpty()) {
            throw new EntityNotFoundException("Pedido con id " + id + " no encontrado");
        }

        return pedido.get();
    }

    public PedidoUsuario obtenerPedidoUsuarioPorId(int id) {
        Optional<PedidoUsuario> pedido = pedidoUsuarioDAO.findById((Long.valueOf(id)));
        if (pedido.isEmpty()) {
            throw new EntityNotFoundException("Pedido con id " + id + " no encontrado");
        }

        return pedido.get();
    }

    public List<PedidoUsuario> getPedidosUsuarioPorId(int id) {
        if (!pedidoUsuarioDAO.existsById(Long.valueOf(id))) {
            throw new RuntimeException("No se encontro pedidos para el usuario con ID: " + id);
        }
        return pedidoUsuarioDAO.obtenerPedidosPorIdUsuario(Long.valueOf(id));
    }

    public Pedido crearPedidoProveedor(RequestDTO data) {
        return pedidoProveedorFactory.crearPedido(data);
    }

    public Pedido crearPedidoUsuario(RequestDTO data) {
        return pedidoUsuarioFactory.crearPedido(data);
    }

    public void deletePedido(int id) {
        Optional<PedidoProveedor> pedido = pedidoProveedorDAO.findById(Long.valueOf(id));

        if (pedido.isEmpty()) {
            throw new EntityNotFoundException("Pedido con id " + id + " no encontrado");
        }

        pedidoProveedorDAO.deleteById(Long.valueOf(id));

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
