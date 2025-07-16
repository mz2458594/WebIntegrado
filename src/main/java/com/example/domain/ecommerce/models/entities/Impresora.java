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
@Table(name = "impresoras")
public class Impresora extends Producto{
    // (Inyección de tinta, Láser)
    private String tipo;

    private String funciones;

    private String conectividad;

    private String velocidadImpresion;

    private String dobleCaraAutomatica;
}
