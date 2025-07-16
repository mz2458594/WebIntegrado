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
@Table(name = "camaras")
public class Camara extends Producto{
    // (DSLR, Compacta, Deportiva)
    private String tipo;

    private String resolución;

    private String zoomOptico;

    private String estabilizacionImagen;

}
