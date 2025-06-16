package com.example.domain.ecommerce.models.entities;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tablets")
public class Tablet extends Producto{
    private String tamañoPantalla;

    private String memoriaRam;

    private String almacenamientoInterno;

    private String resoluciónCamara;

    private String sistemaOperativo;

}
