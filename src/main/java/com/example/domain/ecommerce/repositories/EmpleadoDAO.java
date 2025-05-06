package com.example.domain.ecommerce.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.domain.ecommerce.models.entities.Empleado;
import com.example.domain.ecommerce.models.entities.Usuario;

@Repository
public interface EmpleadoDAO extends JpaRepository<Empleado, Long> {
    Optional<Empleado> findByUsuario(Usuario usuario);
}
