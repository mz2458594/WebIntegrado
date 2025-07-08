package com.example.domain.ecommerce.repositories;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.domain.ecommerce.models.entities.Pedido;
import com.example.domain.ecommerce.models.entities.PedidoUsuario;
import com.example.domain.ecommerce.models.enums.EstadoPedido;

public interface PedidoUsuarioDAO extends JpaRepository<PedidoUsuario, Long>{
    @Query("SELECT p FROM PedidoUsuario p WHERE p.user.idUsuario = :idUsuario")
    List<PedidoUsuario> obtenerPedidosPorIdUsuario(@Param("idUsuario") Long idUsuario);

    @Query("""
            SELECT p
            FROM PedidoUsuario p
            WHERE (:username IS NULL OR p.user.username = :username)
            AND (:fechaInicio IS NULL OR p.fechaPedido >= :fechaInicio)
            AND (:fechaFinal IS NULL OR p.fechaPedido <= :fechaFinal)
            AND (:estado IS NULL OR p.estado = :estado)
            """)
    List<Pedido> findbyFiltro(
        @Param("fechaInicio") Timestamp fechaInicio, 
        @Param("fechaFinal") Timestamp fechaFinal,
        @Param("estado") EstadoPedido estado,
        @Param("username") String usuario
    );
}
