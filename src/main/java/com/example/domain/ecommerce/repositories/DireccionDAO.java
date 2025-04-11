package com.example.domain.ecommerce.repositories;

import com.example.domain.ecommerce.models.entities.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DireccionDAO extends JpaRepository<Direccion, Long>{
}