package com.example.domain.ecommerce.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.domain.ecommerce.models.entities.ControlComprobante;
import com.example.domain.ecommerce.models.enums.TipoComprobante;

@Repository
public interface ControlComprobanteDAO extends JpaRepository<ControlComprobante, Long>{
    Optional<ControlComprobante> findByTipo(TipoComprobante tipo);
}
