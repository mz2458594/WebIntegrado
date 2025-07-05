package com.example.domain.ecommerce.repositories;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.domain.ecommerce.models.entities.Usuario;
import com.example.domain.ecommerce.models.entities.Venta;
import com.example.domain.ecommerce.models.entities.VentaEcommerce;
import com.example.domain.ecommerce.models.enums.TipoComprobante;

@Repository
public interface VentaEcommerceDAO extends JpaRepository<VentaEcommerce, Long>{
    Optional<VentaEcommerce> findByUsuario(Usuario usuario);
    @Query("SELECT v from VentaEcommerce v WHERE (:username IS NULL OR v.usuario.username = :username) AND (:tipo IS NULL OR v.comprobante.tipo = :tipo) AND (:fechaInicio IS NULL OR v.fechaVenta >= :fechaInicio) AND (:fechaFinal IS NULL OR v.fechaVenta <= :fechaFinal)")
    List<Venta> findByUsuarioAndTipoComprobanteAndFechaVentaBetween(
            @Param("fechaInicio") Timestamp fechaInicio,
            @Param("fechaFinal") Timestamp fechaFinal,
            @Param("username") String username,
            @Param("tipo") TipoComprobante tipo);
}
