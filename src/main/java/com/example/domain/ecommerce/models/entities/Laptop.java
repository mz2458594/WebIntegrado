package com.example.domain.ecommerce.models.entities;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Laptops")
public class Laptop implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String procesador;
    private String tarjetaGrafica;
    private String sistemaOperativo;
    private String tamañoPantalla;
    private String memoriaRam;

    @OneToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;
}
