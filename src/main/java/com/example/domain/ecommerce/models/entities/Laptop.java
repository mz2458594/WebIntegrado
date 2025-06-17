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
@Table(name = "Laptops")
public class Laptop extends Producto{

    private String procesador;
    private String tarjetaGrafica;
    private String sistemaOperativo;
    private String tamañoPantalla;
    private String memoriaRam;
    private String color;
    private String almacenamiento;
}
