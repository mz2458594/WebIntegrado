package com.example.domain.ecommerce;
import java.util.Optional;
import com.example.domain.ecommerce.dto.CategoriaDTO;
import com.example.domain.ecommerce.models.entities.Categoria;
import com.example.domain.ecommerce.repositories.CategoriaDAO;
import com.example.domain.ecommerce.services.CategoriaService;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EcommerceApplicationTests {

    @Autowired
    private CategoriaService categoriaService;

    @MockBean
    private CategoriaDAO categoriaDAO;

    @Test
    void testObtenerCategorias() {
        categoriaService.obtenerCategorias();
        verify(categoriaDAO, times(1)).findAll();
    }
    
    @Test
    void testCreateCategory() {
        
        CategoriaDTO dto = new CategoriaDTO();
        dto.setNombre("Electrónica");
        dto.setDescripcion("Productos tecnológicos");
        dto.setImagen("electro.jpg");

    
        categoriaService.createCategory(dto);

        ArgumentCaptor<Categoria> captor = ArgumentCaptor.forClass(Categoria.class);
        verify(categoriaDAO, times(1)).save(captor.capture());

        Categoria categoriaGuardada = captor.getValue();
        assertEquals("Electrónica", categoriaGuardada.getNombre());
        assertEquals("Productos tecnológicos", categoriaGuardada.getDescripcion());
        assertEquals("electro.jpg", categoriaGuardada.getImagen());
    }
    
    @Test
    void testActualizarCategoria() {
    Categoria existente = new Categoria();
    existente.setId_categoria(1);  

    CategoriaDTO dto = new CategoriaDTO();
    dto.setNombre("Nueva");
    dto.setDescripcion("Actualizada");
    dto.setImagen("nueva.jpg");

    when(categoriaDAO.findById(1L)).thenReturn(Optional.of(existente));

    categoriaService.updateCategoria(dto, 1);

    verify(categoriaDAO).save(any(Categoria.class));
}

    @Test
    void testEliminarCategoria() {
        categoriaService.eliminarCategoria(1);
        verify(categoriaDAO, times(1)).deleteById(1L);
    }
}
