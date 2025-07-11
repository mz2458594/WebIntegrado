package com.example.domain.ecommerce.repositories;

import java.util.Optional;

import com.example.domain.ecommerce.models.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UsuarioDAO extends JpaRepository<Usuario, Long>{
    Optional<Usuario> findByEmail(String correo);
    Optional<Usuario> findByUsername(String username);
}
