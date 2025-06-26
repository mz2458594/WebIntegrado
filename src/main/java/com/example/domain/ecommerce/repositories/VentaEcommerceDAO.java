package com.example.domain.ecommerce.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.domain.ecommerce.models.entities.Usuario;
import com.example.domain.ecommerce.models.entities.VentaEcommerce;

@Repository
public interface VentaEcommerceDAO extends JpaRepository<VentaEcommerce, Long>{
    Optional<VentaEcommerce> findByUsuario(Usuario usuario);
}
