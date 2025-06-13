package com.example.domain.ecommerce.factories;

import org.springframework.stereotype.Component;
import com.example.domain.ecommerce.dto.LaptopDTO;
import com.example.domain.ecommerce.dto.ProductDTO;
import com.example.domain.ecommerce.models.entities.Laptop;
import com.example.domain.ecommerce.models.entities.Producto;
import com.example.domain.ecommerce.repositories.ProductoDAO;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class LaptopFactory implements ProductoFactory {

    private final ProductoDAO productoDAO;

    @Override
    public boolean supports(String tipo) {
        return tipo.equalsIgnoreCase("Laptops");
    }

    @Override
    public Producto crearProducto(ProductDTO productDTO) {
    
        Laptop laptop = new Laptop();

        laptop.setProcesador(productDTO.getProcesador());
        laptop.setTarjetaGrafica(productDTO.getTarjetaGrafica());
        laptop.setSistemaOperativo(productDTO.getSistemaOperativo());
        laptop.setTamañoPantalla(productDTO.getTamañoPantalla());
        laptop.setMemoriaRam(productDTO.getMemoriaRam());
        laptop.setColor(productDTO.getColor());
        return laptop;
    }

    @Override
    public Producto actualizar(ProductDTO productDTO, int id) {
        Laptop laptop = (Laptop) productoDAO.findById(Long.valueOf(id)).get();

        laptop.setProcesador(productDTO.getProcesador());
        laptop.setTarjetaGrafica(productDTO.getTarjetaGrafica());
        laptop.setSistemaOperativo(productDTO.getSistemaOperativo());
        laptop.setTamañoPantalla(productDTO.getTamañoPantalla());
        laptop.setMemoriaRam(productDTO.getMemoriaRam());
        laptop.setColor(productDTO.getColor());

        return laptop;
    }
}
