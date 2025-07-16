package com.example.domain.ecommerce.repositories;

import com.example.domain.ecommerce.models.entities.Venta;
import com.example.domain.ecommerce.models.enums.TipoComprobante;

import java.sql.Timestamp;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VentasDAO extends JpaRepository<Venta, Long> {
    Venta findByTotal(double total);
    @Query("""
        SELECT v 
        from Venta v
        WHERE (:username IS NULL OR v.usuario.username = :username) 
        AND (:tipo IS NULL OR v.comprobante.tipo = :tipo) 
        AND (:fechaInicio IS NULL OR v.fechaVenta >= :fechaInicio) 
        AND (:fechaFinal IS NULL OR v.fechaVenta <= :fechaFinal)
            """)
    List<Venta> findByUsuarioAndTipoComprobanteAndFechaVentaBetween(
            @Param("fechaInicio") Timestamp fechaInicio,
            @Param("fechaFinal") Timestamp fechaFinal,
            @Param("username") String username,
            @Param("tipo") TipoComprobante tipo);
}
