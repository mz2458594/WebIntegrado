package com.example.domain.ecommerce.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "auriculares")
public class Auricular extends Producto {

    // Tipo (Inalámbrico / Cableado)
    private String tipo;
    private String duracion;
    private String cancelacionRuido;
    private String conector;

}
