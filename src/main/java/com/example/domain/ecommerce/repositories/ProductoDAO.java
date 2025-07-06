package com.example.domain.ecommerce.repositories;

import com.example.domain.ecommerce.models.entities.Producto;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductoDAO extends JpaRepository<Producto, Long>{
    Producto findByNombre(String nombre);
    Optional<Producto> findByCodigoBarras(String codigo);
    @Query("SELECT p from Producto p WHERE p.stock < '5'")
    List<Producto> obtenerProductosStockBajo();

    @Query("""
            SELECT p
            FROM Producto p
            WHERE (:categoria IS NULL OR p.categoria.nombre = :categoria)
            AND (:proveedor IS NULL OR p.proveedor.nombre = :proveedor)
            """)
    List<Producto> findByFiltro();
}
