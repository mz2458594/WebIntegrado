package com.example.domain.ecommerce.dto;

import lombok.Data;

@Data
public class LaptopDTO extends ProductDTO{
    private String procesador;
    private String tarjetaGrafica;
    private String sistemaOperativo;
    private String tamañoPantalla;
    private String memoriaRam;
    private String color;
}
