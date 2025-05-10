package com.example.domain.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.domain.ecommerce.models.entities.Pedido;

@Repository
public interface PedidoDAO extends JpaRepository<Pedido, Long> {
    
}
