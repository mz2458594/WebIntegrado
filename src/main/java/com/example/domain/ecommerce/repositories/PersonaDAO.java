package com.example.domain.ecommerce.repositories;

import com.example.domain.ecommerce.models.entities.Persona;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaDAO extends JpaRepository<Persona, Long> {
    // @Query("""
    //         SELECT p
    //         FROM Persona p
    //         JOIN Cliente c
    //         JOIN Empleado p
    //         WHERE (:estado IS NULL OR c.usuario.estado = :estado)
    //         AND (:departamento IS NULL OR c.direccion.departamento = :departamento)
    //             """)
    // List<Persona> findByFiltro();

}
