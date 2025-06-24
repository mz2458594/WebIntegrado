package com.example.domain.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.domain.ecommerce.models.entities.VentaInventario;

@Repository
public interface VentasInventarioDAO extends JpaRepository<VentaInventario, Long> {
    
}
