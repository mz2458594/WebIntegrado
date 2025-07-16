package com.example.domain.ecommerce.dto;

import java.util.List;

public class VentaRequestDTO {
    private List<ProductRequestDTO> productos;

    public List<ProductRequestDTO> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductRequestDTO> productos) {
        this.productos = productos;
    }

   

    
}
