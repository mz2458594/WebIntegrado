package com.example.domain.ecommerce.factories;

import org.springframework.stereotype.Component;

import com.example.domain.ecommerce.dto.ProductDTO;
import com.example.domain.ecommerce.models.entities.Auricular;
import com.example.domain.ecommerce.models.entities.Laptop;
import com.example.domain.ecommerce.models.entities.Producto;
import com.example.domain.ecommerce.models.entities.Teclado;
import com.example.domain.ecommerce.repositories.ProductoDAO;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class TecladoFactory implements ProductoFactory {

    private final ProductoDAO productoDAO;

    @Override
    public boolean supports(String tipo) {
        return tipo.equalsIgnoreCase("Teclados");
    }

    @Override
    public Producto crearProducto(ProductDTO productDTO) {

        Teclado teclado = new Teclado();

        teclado.setTipo(productDTO.getTipo());
        teclado.setConectividad(productDTO.getConectividad());
        teclado.setDistribución(productDTO.getDistribución());
        teclado.setRetroiluminación(productDTO.getRetroiluminación());

        return teclado;
    }

    @Override
    public Producto actualizar(ProductDTO productDTO, int id) {
        Teclado teclado = (Teclado) productoDAO.findById(Long.valueOf(id)).get();

        teclado.setTipo(productDTO.getTipo());
        teclado.setConectividad(productDTO.getConectividad());
        teclado.setDistribución(productDTO.getDistribución());
        teclado.setRetroiluminación(productDTO.getRetroiluminación());

        return teclado;
    }

    @Override
    public Producto obtener(int id){
        Teclado teclado = (Teclado) productoDAO.findById(Long.valueOf(id)).get();
        return teclado;
    }

}
