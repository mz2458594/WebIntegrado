package com.example.domain.ecommerce.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.domain.ecommerce.models.entities.Rol;

@Repository
public interface RolDAO extends JpaRepository<Rol, Long>{
    Optional<Rol> findByNombre(String nombre);
}
