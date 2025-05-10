package com.example.domain.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.domain.ecommerce.models.entities.Boleta;

@Repository
public interface BoletaDAO extends JpaRepository<Boleta, Long>{
    
}
