package com.example.domain.ecommerce.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.domain.ecommerce.models.entities.Empleado;
import com.example.domain.ecommerce.models.entities.Persona;
import com.example.domain.ecommerce.models.entities.Usuario;
import com.example.domain.ecommerce.models.enums.Estado;

@Repository
public interface EmpleadoDAO extends JpaRepository<Empleado, Long> {
    Optional<Empleado> findByUsuario(Usuario usuario);

    @Query("""
        SELECT e 
        FROM Empleado e
        WHERE (:estado IS NULL OR e.usuario.estado = :estado) 
        AND (:departamento IS NULL OR e.direccion.departamento = :departamento)
            """)
    List<Persona> findByFiltro(@Param("estado") Estado estado, @Param("departamento") String departamento);
}
