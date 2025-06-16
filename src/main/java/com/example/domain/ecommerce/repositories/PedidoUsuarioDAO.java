package com.example.domain.ecommerce.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.domain.ecommerce.models.entities.PedidoUsuario;

public interface PedidoUsuarioDAO extends JpaRepository<PedidoUsuario, Long>{
    @Query("SELECT p FROM PedidoUsuario p WHERE p.user.idUsuario = :idUsuario")
    List<PedidoUsuario> obtenerPedidosPorIdUsuario(@Param("idUsuario") Long idUsuario);
}
