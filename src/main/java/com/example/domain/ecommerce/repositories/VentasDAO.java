package com.example.domain.ecommerce.repositories;

import com.example.domain.ecommerce.models.entities.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VentasDAO extends JpaRepository<Venta, Long> {
    Venta findByTotal(double total);
}
