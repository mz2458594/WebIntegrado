package com.example.domain.ecommerce.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.domain.ecommerce.models.entities.Cliente;
import com.example.domain.ecommerce.models.entities.Persona;
import com.example.domain.ecommerce.models.entities.Usuario;
import com.example.domain.ecommerce.models.enums.Estado;

@Repository
public interface ClienteDAO extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByUsuario(Usuario usuario);
    Optional<Cliente> findByDni(String num_documento);
    Optional<Cliente> findByTelefono(String telefono);

    @Query("""
        SELECT c 
        FROM Cliente c
        WHERE (:estado IS NULL OR c.usuario.estado = :estado) 
        AND (:departamento IS NULL OR c.direccion.departamento = :departamento)
            """)
    List<Persona> findByFiltro(@Param("estado") Estado estado, @Param("departamento") String departamento);
}
