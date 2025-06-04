package com.example.domain.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class LaptopDTO {
    private String procesador;
    private String tarjetaGrafica;
    private String sistemaOperativo;
    private String tamañoPantalla;
    private String memoriaRam;
    private String color;
}
