package com.example.domain.ecommerce;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.example.domain.ecommerce.dto.CategoriaDTO;
import com.example.domain.ecommerce.dto.DireccionDTO;
import com.example.domain.ecommerce.dto.ProductDTO;
import com.example.domain.ecommerce.models.entities.Categoria;
import com.example.domain.ecommerce.models.entities.Direccion;
import com.example.domain.ecommerce.models.entities.Persona;
import com.example.domain.ecommerce.models.entities.Producto;
import com.example.domain.ecommerce.models.entities.Proveedor;
import com.example.domain.ecommerce.models.entities.Usuario;
import com.example.domain.ecommerce.repositories.CategoriaDAO;
import com.example.domain.ecommerce.repositories.DireccionDAO;
import com.example.domain.ecommerce.repositories.ProductoDAO;
import com.example.domain.ecommerce.repositories.ProveedorDAO;
import com.example.domain.ecommerce.repositories.UsuarioDAO;
import com.example.domain.ecommerce.services.CategoriaService;
import com.example.domain.ecommerce.services.DireccionService;
import com.example.domain.ecommerce.services.ProductoService;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
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

    @Autowired
    private DireccionService direccionService;

    @MockBean
    private CategoriaDAO categoriaDAO;

    @MockBean
    private DireccionDAO direccionDAO;

    @MockBean
    private UsuarioDAO usuarioDAO;

    @MockBean
    private ProductoDAO productoDAO; 

    @MockBean
    private ProveedorDAO proveedorDAO; 

    @Autowired
    private ProductoService productoService; 
    
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

    @Test
    void testCreateDirection() {
        DireccionDTO direccionDTO = new DireccionDTO();
        direccionDTO.setCalle("Calle Falsa 123");
        direccionDTO.setDistrito("Distrito X");
        direccionDTO.setProvincia("Provincia Y");

        Persona persona = new Persona() {};
        Usuario usuario = new Usuario();
        usuario.setPersona(persona);

        when(usuarioDAO.findById(1L)).thenReturn(Optional.of(usuario));

        direccionService.createDirection(direccionDTO, 1);

        verify(direccionDAO, times(1)).save(any(Direccion.class));
    }

    @Test
    void testUpdateDirection() {
        Direccion direccion = new Direccion();
        Persona persona = new Persona() {};
        persona.setDireccion(direccion);

        Usuario usuario = new Usuario();
        usuario.setPersona(persona);

        when(usuarioDAO.findById(1L)).thenReturn(Optional.of(usuario));

        DireccionDTO direccionDTO = new DireccionDTO();
        direccionDTO.setCalle("Nueva Calle");
        direccionDTO.setCiudad("Nueva Ciudad");
        direccionDTO.setDistrito("Nuevo Distrito");
        direccionDTO.setProvincia("Nueva Provincia");

        direccionService.updateDirection(direccionDTO, 1);

        verify(direccionDAO, times(1)).save(direccion);
        assertEquals("Nueva Calle", direccion.getCalle());
        assertEquals("Nueva Ciudad", direccion.getCiudad());
        assertEquals("Nuevo Distrito", direccion.getDistrito());
        assertEquals("Nueva Provincia", direccion.getProvincia());
    }

    @Test
    void testAgregarProducto() {
        ProductDTO dto = new ProductDTO();
        dto.setNombre("Producto 1");
        dto.setDescripcion("Desc");
        dto.setPrecio("100");
        dto.setStock("50");
        dto.setImagen1("img.jpg");
        dto.setNombre_categoria("Cat 1");
        dto.setProveedor("Prov 1");

        Categoria categoria = new Categoria();
        Proveedor proveedor = new Proveedor();

        when(categoriaDAO.findByNombre("Cat 1")).thenReturn(categoria);
        when(proveedorDAO.findByNombre("Prov 1")).thenReturn(proveedor);

        productoService.agregarProducto(dto);

        verify(productoDAO, times(1)).save(any(Producto.class));
    }

    @Test
    void testActualizarProducto() {
        ProductDTO dto = new ProductDTO();
        dto.setNombre("Producto Actualizado");
        dto.setDescripcion("Nueva Desc");
        dto.setPrecio("200");
        dto.setStock("100");
        dto.setImagen1("img2.jpg");
        dto.setNombre_categoria("Cat 1");
        dto.setProveedor("Prov 1");

        Producto productoExistente = new Producto();
        productoExistente.setIdProducto(1);

        Categoria categoria = new Categoria();
        Proveedor proveedor = new Proveedor();

        when(productoDAO.findById(1L)).thenReturn(Optional.of(productoExistente));
        when(categoriaDAO.findByNombre("Cat 1")).thenReturn(categoria);
        when(proveedorDAO.findByNombre("Prov 1")).thenReturn(proveedor);

        productoService.actualizarProducto(dto, 1);

        verify(productoDAO, times(1)).save(productoExistente);
    }

    @Test
    void testEliminarProducto() {
        productoService.eliminarProducto(1);
        verify(productoDAO, times(1)).deleteById(1L);
    }

    @Test
    void testObtenerProductoPorId() {
        Producto producto = new Producto();
        producto.setIdProducto(1);

        when(productoDAO.findById(1L)).thenReturn(Optional.of(producto));

        Producto resultado = productoService.obtenerProductoPorId(1);
        assertEquals(1, resultado.getIdProducto());
    }

    @Test
    void testListarProductos() {
        List<Producto> productos = new ArrayList<>();
        productos.add(new Producto());
        productos.add(new Producto());

        when(productoDAO.findAll()).thenReturn(productos);

        List<Producto> resultado = productoService.listarProducto();
        assertEquals(2, resultado.size());
    }
}