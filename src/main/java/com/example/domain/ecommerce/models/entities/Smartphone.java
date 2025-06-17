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
@Table(name = "smartphones")
public class Smartphone extends Producto{
    
    private String tamañoPantalla;
    private String memoriaRam;
    private String almacenamientoInterno;
    private String resolucionCamara;
    private String capacidadBateria;
    private String sistemaOperativo;

}
