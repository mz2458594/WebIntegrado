package com.example.domain.ecommerce.models.entities;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "teclados")
public class Teclado extends Producto{
    private String tipo;

    private String conectividad;

    private String distribución;

    private String retroiluminación;

}
