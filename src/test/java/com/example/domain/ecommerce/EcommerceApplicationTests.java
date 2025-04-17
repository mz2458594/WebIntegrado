package com.example.domain.ecommerce;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.example.domain.ecommerce.dto.CategoriaDTO;
import com.example.domain.ecommerce.dto.DireccionDTO;
import com.example.domain.ecommerce.dto.LoginDTO;
import com.example.domain.ecommerce.dto.ProductDTO;
import com.example.domain.ecommerce.dto.ProveedorDTO;
import com.example.domain.ecommerce.dto.RequestDTO;
import com.example.domain.ecommerce.dto.UserDTO;
import com.example.domain.ecommerce.models.entities.Categoria;
import com.example.domain.ecommerce.models.entities.Cliente;
import com.example.domain.ecommerce.models.entities.Direccion;
import com.example.domain.ecommerce.models.entities.Persona;
import com.example.domain.ecommerce.models.entities.Producto;
import com.example.domain.ecommerce.models.entities.Proveedor;
import com.example.domain.ecommerce.models.entities.Usuario;
import com.example.domain.ecommerce.models.entities.Venta;
import com.example.domain.ecommerce.models.entities.Venta_producto;
import com.example.domain.ecommerce.repositories.CategoriaDAO;
import com.example.domain.ecommerce.repositories.PersonaDAO;
import com.example.domain.ecommerce.repositories.DireccionDAO;
import com.example.domain.ecommerce.repositories.ProductoDAO;
import com.example.domain.ecommerce.repositories.ProveedorDAO;
import com.example.domain.ecommerce.repositories.UsuarioDAO;
import com.example.domain.ecommerce.repositories.VentasDAO;
import com.example.domain.ecommerce.services.CategoriaService;
import com.example.domain.ecommerce.services.DireccionService;
import com.example.domain.ecommerce.services.ProductoService;
import com.example.domain.ecommerce.services.ProveedorService;
import com.example.domain.ecommerce.services.UsuarioService;
import com.example.domain.ecommerce.services.VentaService;
import jakarta.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EcommerceApplicationTests {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private DireccionService direccionService;

    @Autowired
    private ProductoService productoService; 
    
    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private VentaService ventaService;

    @MockBean
    private CategoriaDAO categoriaDAO;

    @MockBean
    private DireccionDAO direccionDAO;

    @MockBean
    private UsuarioDAO usuarioDAO;

    @MockBean
    private PersonaDAO personaDAO;

    @MockBean
    private ProductoDAO productoDAO; 

    @MockBean
    private ProveedorDAO proveedorDAO;
    
    @MockBean
    private VentasDAO ventasDAO;

    
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

    @Test
    void testObtenerProveedores() {
        List<Proveedor> proveedores = new ArrayList<>();
        proveedores.add(new Proveedor());
        proveedores.add(new Proveedor());

        when(proveedorDAO.findAll()).thenReturn(proveedores);

        Iterable<Proveedor> resultado = proveedorService.obtenerProveedores();
        assertEquals(2, ((List<Proveedor>) resultado).size());
        verify(proveedorDAO, times(1)).findAll();
    }

    @Test
    void testCreateProveedor() {
        ProveedorDTO dto = new ProveedorDTO();
        dto.setRuc(12345678901L);
        dto.setNombre("Proveedor 1");
        dto.setTelefono(999999999);
        dto.setEmail("prov1@mail.com");

        proveedorService.createProv(dto);

        ArgumentCaptor<Proveedor> captor = ArgumentCaptor.forClass(Proveedor.class);
        verify(proveedorDAO).save(captor.capture());

        Proveedor proveedorGuardado = captor.getValue();
        assertEquals(dto.getRuc(), proveedorGuardado.getRuc());
        assertEquals(dto.getNombre(), proveedorGuardado.getNombre());
        assertEquals(dto.getTelefono(), proveedorGuardado.getTelefono());
        assertEquals(dto.getEmail(), proveedorGuardado.getEmail());
    }

    @Test
    void testUpdateProveedor() {
        ProveedorDTO dto = new ProveedorDTO();
        dto.setRuc(12345678901L);
        dto.setNombre("Proveedor Actualizado");
        dto.setTelefono(987654321);
        dto.setEmail("actualizado@mail.com");

        Proveedor existente = new Proveedor();
        existente.setId(1);

        when(proveedorDAO.findById(1L)).thenReturn(Optional.of(existente));

        proveedorService.updateProv(dto, 1);

        assertEquals(dto.getRuc(), existente.getRuc());
        assertEquals(dto.getNombre(), existente.getNombre());
        assertEquals(dto.getTelefono(), existente.getTelefono());
        assertEquals(dto.getEmail(), existente.getEmail());

        verify(proveedorDAO).save(existente);
    }

    @Test
    void testEliminarProveedor() {
        proveedorService.eliminarProveedor(1);
        verify(proveedorDAO).deleteById(1L);
    }

    @Test
    void testLogin() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("usuario@example.com");
        loginDTO.setPassword("password123");

        Usuario usuario = new Usuario();
        usuario.setEmail("usuario@example.com");
        usuario.setPassword(new BCryptPasswordEncoder().encode("password123"));

        when(usuarioDAO.findByEmail("usuario@example.com")).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.login(loginDTO);

        assertEquals("usuario@example.com", resultado.getEmail());
        verify(usuarioDAO, times(1)).findByEmail("usuario@example.com");
    }

    @Test
    void testLoginEmpleado() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("empleado@example.com");
        loginDTO.setPassword("password123");

        Usuario usuario = new Usuario();
        usuario.setEmail("empleado@example.com");
        usuario.setPassword(new BCryptPasswordEncoder().encode("password123"));
        usuario.setRole("Empleado");

        when(usuarioDAO.findByEmail("empleado@example.com")).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.loginEmpleado(loginDTO);

        assertEquals("empleado@example.com", resultado.getEmail());
        verify(usuarioDAO, times(1)).findByEmail("empleado@example.com");
    }


    @Test
    void testActualizarUsuario() {
        UserDTO userDTO = new UserDTO();
        userDTO.setNombre("Nuevo Nombre");
        userDTO.setApellido("Nuevo Apellido");
        userDTO.setNum_documento("12345678");
        userDTO.setCelular("987654321");
        userDTO.setCalle("Calle Falsa");
        userDTO.setCiudad("Ciudad Falsa");
        userDTO.setDistrito("Distrito Falso");
        userDTO.setProvincia("Provincia Falsa");
        userDTO.setCorreo("nuevoemail@example.com");
        userDTO.setUsername("nuevo_username");
        userDTO.setRol("Cliente");


        Persona persona = new Cliente(); 
        persona.setNombre("Nombre Anterior");
        persona.setApellido("Apellido Anterior");
        persona.setDni("12345678");
        persona.setTelefono("987654321");
        persona.setDireccion(new Direccion());

        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setIdUsuario(1);
        usuarioExistente.setEmail("usuario@example.com");
        usuarioExistente.setPersona(persona);

        when(usuarioDAO.findById(1L)).thenReturn(Optional.of(usuarioExistente));
        when(usuarioDAO.save(any(Usuario.class))).thenReturn(usuarioExistente);

        Usuario actualizado = usuarioService.actualizarUsuarios(userDTO, 1);

        assertEquals("Nuevo Nombre", actualizado.getPersona().getNombre());
        assertEquals("Nuevo Apellido", actualizado.getPersona().getApellido());
        assertEquals("nuevoemail@example.com", actualizado.getEmail());
        verify(usuarioDAO, times(1)).save(any(Usuario.class));
    }


    @Test
    void testObtenerUsuarioPorId() {
        int usuarioId = 1;
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(usuarioId);
        usuario.setEmail("usuario@example.com");

        when(usuarioDAO.findById(Long.valueOf(usuarioId))).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.obtenerUsuarioPorId(usuarioId);

        assertEquals(usuarioId, resultado.getIdUsuario());
        assertEquals("usuario@example.com", resultado.getEmail());
    }

    @Test
    void testCrearUsuario() {
        UserDTO userDTO = new UserDTO();
        userDTO.setNombre("Nuevo Usuario");
        userDTO.setApellido("Nuevo Apellido");
        userDTO.setNum_documento("12345678");
        userDTO.setCelular("987654321");
        userDTO.setCalle("Calle Falsa");
        userDTO.setCiudad("Ciudad Falsa");
        userDTO.setDistrito("Distrito Falso");
        userDTO.setProvincia("Provincia Falsa");
        userDTO.setCorreo("nuevoemail@example.com");
        userDTO.setUsername("nuevo_username");
        userDTO.setContraseña("password123");
        userDTO.setRol("Cliente");
    
        Persona persona = mock(Persona.class);
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);
        usuario.setUsername("nuevo_username");
        usuario.setPassword("password123");
        usuario.setEmail("nuevoemail@example.com");
        usuario.setRole("Cliente");
        usuario.setPersona(persona);
    
        when(personaDAO.save(any(Persona.class))).thenReturn(persona);
        when(usuarioDAO.save(any(Usuario.class))).thenReturn(usuario);
    
        Usuario usuarioCreado = usuarioService.createUser(userDTO);
    
        assertNotNull(usuarioCreado);
        assertEquals("nuevo_username", usuarioCreado.getUsername());
        verify(usuarioDAO, times(1)).save(any(Usuario.class));
    }
    

    @Test
    void testEliminarUsuario() {
        int usuarioId = 1;

        usuarioService.eliminarUsuario(usuarioId);

        verify(usuarioDAO, times(1)).deleteById(Long.valueOf(usuarioId));
    }



}