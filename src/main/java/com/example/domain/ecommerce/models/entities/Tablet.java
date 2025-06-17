package com.example.domain.ecommerce.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tablets")
@Entity
public class Tablet extends Producto{
    
    private String tamañoPantalla;

    private String memoriaRam;

    private String almacenamientoInterno;

    private String resolucionCamara;

    private String sistemaOperativo;

}
