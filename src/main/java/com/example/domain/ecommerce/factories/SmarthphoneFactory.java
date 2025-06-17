package com.example.domain.ecommerce.factories;

import org.springframework.stereotype.Component;

import com.example.domain.ecommerce.dto.ProductDTO;
import com.example.domain.ecommerce.models.entities.Producto;
import com.example.domain.ecommerce.models.entities.Smartphone;
import com.example.domain.ecommerce.repositories.ProductoDAO;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class SmarthphoneFactory implements ProductoFactory {

    private final ProductoDAO productoDAO;

    @Override
    public boolean supports(String tipo) {
        return tipo.equalsIgnoreCase("Smartphones");
    }

    @Override
    public Producto crearProducto(ProductDTO productDTO) {

        Smartphone smartphone = new Smartphone();

        smartphone.setTamañoPantalla(productDTO.getTamañoPantalla());
        smartphone.setMemoriaRam(productDTO.getMemoriaRam());
        smartphone.setAlmacenamientoInterno(productDTO.getAlmacenamientoInterno());
        smartphone.setResolucionCamara(productDTO.getResolucionCamara());
        smartphone.setCapacidadBateria(productDTO.getCapacidadBateria());
        smartphone.setSistemaOperativo(productDTO.getSistemaOperativo());

        return smartphone;
    }

    @Override
    public Producto actualizar(ProductDTO productDTO, int id) {
        Smartphone smartphone = (Smartphone) productoDAO.findById(Long.valueOf(id)).get();

        smartphone.setTamañoPantalla(productDTO.getTamañoPantalla());
        smartphone.setMemoriaRam(productDTO.getMemoriaRam());
        smartphone.setAlmacenamientoInterno(productDTO.getAlmacenamientoInterno());
        smartphone.setResolucionCamara(productDTO.getResolucionCamara());
        smartphone.setCapacidadBateria(productDTO.getCapacidadBateria());
        smartphone.setSistemaOperativo(productDTO.getSistemaOperativo());

        return smartphone;
    }

}
