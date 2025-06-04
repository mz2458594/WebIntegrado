package com.example.domain.ecommerce.dto;

import lombok.Data;

@Data
public class ProductDTO {


    private String nombre;
    private String descripcion;
    private String nombre_categoria;
    private String precio;
    private String stock;
    private String imagen1;
    private String proveedor;
    private String marca;
    private String precioCompra;
    private String codigoBarras;
    private LaptopDTO laptopDTO;
    
}
