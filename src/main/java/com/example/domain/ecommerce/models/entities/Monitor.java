package com.example.domain.ecommerce.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "monitores")
@Entity
public class Monitor extends Producto{
    
    private String tamañoPantalla;
    private String resolución;
    private String tipoPanel;
    private String frecuenciaActualizacion;
    private String puertosEntrada;
}
