package com.example.domain.ecommerce.models.entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "smartphones")
public class Smartphone extends Producto{
    
    private String tamañoPantalla;
    private String memoriaRam;
    private String almacenamientoInterno;
    private String resolucionCamara;
    private String capacidadBateria;
    private String sistemaOperativo;

}
