package com.example.domain.ecommerce.repositories;

import com.example.domain.ecommerce.models.entities.Persona;
import com.example.domain.ecommerce.models.enums.Estado;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaDAO extends JpaRepository<Persona, Long> {
    @Query("""
            SELECT p
            FROM Persona p 
            LEFT JOIN p.direccion d
            LEFT JOIN Cliente c ON TREAT (p AS Cliente) = c
            LEFT JOIN Empleado e on TREAT (p AS Empleado) = e
            WHERE (:estado IS NULL OR c.usuario.estado = :estado OR e.usuario.estado = :estado)
            AND (:departamento IS NULL OR d.departamento = :departamento)
                """)
    List<Persona> findByFiltro(@Param("estado") Estado estado, @Param("departamento") String departamento);
    // Filtros: obtener cuantas personas son de tal departamento, si estan en estado activo o inactivo, y si son Clientes o empleado
}
