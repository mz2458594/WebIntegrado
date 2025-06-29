package com.example.domain.ecommerce.repositories;
import com.example.domain.ecommerce.models.entities.Venta;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface VentasDAO extends JpaRepository<Venta, Long> {
    Venta findByTotal(double total);
    @Query("SELECT v from Venta v WHERE v.fechaVenta BETWEEN :fechaInicio AND :fechaFinal")
    List<Venta> obtenerVentasPorRangoFecha(@Param("fechaInicio") Timestamp fechaInicio, @Param("fechaInicio") Timestamp fechaFinal);
}
