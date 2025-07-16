package com.example.domain.ecommerce.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.domain.ecommerce.models.entities.Proveedor;

@Repository
public interface ProveedorDAO extends JpaRepository<Proveedor, Long> {
    Optional<Proveedor> findByNombre(String nombre);
    Optional<Proveedor> findByEmail(String email);
    Optional<Proveedor> findByRuc(Long ruc);
    Optional<Proveedor> findByTelefono(int telefono);
}
