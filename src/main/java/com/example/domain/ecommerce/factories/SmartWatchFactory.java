package com.example.domain.ecommerce.factories;

import org.springframework.stereotype.Component;

import com.example.domain.ecommerce.dto.ProductDTO;
import com.example.domain.ecommerce.models.entities.Laptop;
import com.example.domain.ecommerce.models.entities.Mouse;
import com.example.domain.ecommerce.models.entities.Producto;
import com.example.domain.ecommerce.models.entities.Smartwatch;
import com.example.domain.ecommerce.repositories.ProductoDAO;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class SmartWatchFactory implements ProductoFactory {
    private final ProductoDAO productoDAO;

    @Override
    public boolean supports(String tipo) {
        return tipo.equalsIgnoreCase("Smartwatches");
    }

    @Override
    public Producto crearProducto(ProductDTO productDTO) {

        Smartwatch smartwatch = new Smartwatch();

        smartwatch.setCompatibilidad(productDTO.getCompatibilidad());
        smartwatch.setMonitoreoSalud(productDTO.getMonitoreoSalud());
        smartwatch.setResistenciaAgua(productDTO.getResistenciaAgua());
        smartwatch.setDuracion(productDTO.getDuracion());

        return smartwatch;
    }

    @Override
    public Producto actualizar(ProductDTO productDTO, int id) {
        Smartwatch smartwatch = (Smartwatch) productoDAO.findById(Long.valueOf(id)).get();

        smartwatch.setCompatibilidad(productDTO.getCompatibilidad());
        smartwatch.setMonitoreoSalud(productDTO.getMonitoreoSalud());
        smartwatch.setResistenciaAgua(productDTO.getResistenciaAgua());
        smartwatch.setDuracion(productDTO.getDuracion());

        return smartwatch;
    }

    @Override
    public Producto obtener(int id){
        Smartwatch smartwatch = (Smartwatch) productoDAO.findById(Long.valueOf(id)).get();
        return smartwatch;
    }
}
