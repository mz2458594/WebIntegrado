package com.example.domain.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.domain.ecommerce.models.entities.Pedido;

public interface PedidoDAO extends JpaRepository<Pedido, Long>{
    
}
