package com.example.domain.ecommerce.repositories;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.domain.ecommerce.models.entities.Pedido;
import com.example.domain.ecommerce.models.entities.PedidoProveedor;
import com.example.domain.ecommerce.models.enums.EstadoPedido;

@Repository
public interface PedidoProveedorDAO extends JpaRepository<PedidoProveedor, Long> {
    @Query("""
            SELECT p
            FROM PedidoProveedor p
            JOIN p.detallePedidos d
            JOIN d.producto prod
            JOIN prod.proveedor prov
            WHERE (:username IS NULL OR p.user.username = :username)
            AND (:fechaInicio IS NULL OR p.fechaPedido >= :fechaInicio)
            AND (:fechaFinal IS NULL OR p.fechaPedido <= :fechaFinal)
            AND (:estado IS NULL OR p.estado = :estado)
            AND (:proveedor IS NULL OR prov.nombre = :proveedor)
            """)
    List<Pedido> findbyFiltro(
            @Param("fechaInicio") Timestamp fechaInicio,
            @Param("fechaFinal") Timestamp fechaFinal,
            @Param("estado") EstadoPedido estado,
            @Param("username") String usuario,
            @Param("proveedor") String proveedor

    );
}
