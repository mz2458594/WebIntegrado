package com.example.domain.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.domain.ecommerce.models.entities.Comprobante;

@Repository
public interface ComprobanteDAO extends JpaRepository<Comprobante, Long>{
    
}
