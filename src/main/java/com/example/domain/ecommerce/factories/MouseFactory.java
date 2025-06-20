package com.example.domain.ecommerce.factories;

import org.springframework.stereotype.Component;

import com.example.domain.ecommerce.dto.ProductDTO;
import com.example.domain.ecommerce.models.entities.Laptop;
import com.example.domain.ecommerce.models.entities.Mouse;
import com.example.domain.ecommerce.models.entities.Producto;
import com.example.domain.ecommerce.repositories.ProductoDAO;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class MouseFactory implements ProductoFactory {
    private final ProductoDAO productoDAO;

    @Override
    public boolean supports(String tipo) {
        return tipo.equalsIgnoreCase("Mouses");
    }

    @Override
    public Producto crearProducto(ProductDTO productDTO) {

        Mouse mouse = new Mouse();
        
        mouse.setTipo(productDTO.getTipo());
        mouse.setConectividad(productDTO.getConectividad());
        mouse.setDpi(productDTO.getDpi());
        mouse.setCantidadBotones(productDTO.getCantidadBotones());

        return mouse;
    }

    @Override
    public Producto actualizar(ProductDTO productDTO, int id) {
        Mouse mouse = (Mouse) productoDAO.findById(Long.valueOf(id)).get();

        mouse.setTipo(productDTO.getTipo());
        mouse.setConectividad(productDTO.getConectividad());
        mouse.setDpi(productDTO.getDpi());
        mouse.setCantidadBotones(productDTO.getCantidadBotones());

        return mouse;
    }

    @Override
    public Producto obtener(int id){
        Mouse mouse = (Mouse) productoDAO.findById(Long.valueOf(id)).get();
        return mouse;
    }
}
