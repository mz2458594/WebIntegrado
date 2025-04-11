package com.example.domain.ecommerce.repositories;

import com.example.domain.ecommerce.models.entities.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PersonaDAO extends JpaRepository<Persona, Long>{

}
