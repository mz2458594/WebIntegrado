package com.example.domain.ecommerce.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "smartwatches")
@Entity
public class Smartwatch extends Producto {
    private String compatibilidad;

    private String monitoreoSalud;

    private String resistenciaAgua;

    private String duracion;

}
