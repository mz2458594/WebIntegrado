package com.example.domain.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.domain.ecommerce.models.entities.VentaEcommerce;

@Repository
public interface VentaEcommerceDAO extends JpaRepository<VentaEcommerce, Long>{
    
}
