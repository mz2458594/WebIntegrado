package com.example.domain.ecommerce.repositories;

import com.example.domain.ecommerce.models.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaDAO extends JpaRepository<Categoria, Long> {
    Categoria findByNombre(String nombre);
}
