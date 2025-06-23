package com.example.domain.ecommerce.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.domain.ecommerce.models.entities.TarifaEnvio;

@Repository
public interface TarifaDAO extends JpaRepository<TarifaEnvio, Long>{
    Optional<TarifaEnvio> findByDepartamento(String departamento);
}
