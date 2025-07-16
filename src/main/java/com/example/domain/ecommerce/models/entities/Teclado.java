package com.example.domain.ecommerce.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "teclados")
@Entity
public class Teclado extends Producto{
    private String tipo;

    private String conectividad;

    private String distribución;

    private String retroiluminación;

}
