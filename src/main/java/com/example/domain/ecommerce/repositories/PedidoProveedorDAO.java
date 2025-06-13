package com.example.domain.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.domain.ecommerce.models.entities.PedidoProveedor;

@Repository
public interface PedidoProveedorDAO extends JpaRepository<PedidoProveedor, Long> {
    
}
